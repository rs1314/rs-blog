package cn.rs.blog.service.weibo.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.weibo.Weibo;
import cn.rs.blog.commoms.utils.ActionLogType;
import cn.rs.blog.commoms.utils.ActionUtil;
import cn.rs.blog.commoms.utils.ConfigUtil;
import cn.rs.blog.commoms.utils.ScoreRuleConsts;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.consts.AppTag;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.MessageType;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.core.exception.ParamException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.dao.weibo.IWeiboDao;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.member.IMessageService;
import cn.rs.blog.service.member.IScoreDetailService;
import cn.rs.blog.service.picture.IPictureService;
import cn.rs.blog.service.system.IActionLogService;
import cn.rs.blog.service.weibo.IWeiboFavorService;
import cn.rs.blog.service.weibo.IWeiboService;

/**
 * Created by rs
 */
@Service("weiboService")
public class WeiboServiceImpl implements IWeiboService {
    @Autowired
    private IWeiboDao weiboDao;
    @Autowired
    private IWeiboFavorService weiboFavorService;
    @Autowired
    private IActionLogService actionLogService;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private IScoreDetailService scoreDetailService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMemberService memberService;

    @Override
    public Weibo findById(int id, int memberId) {
        Weibo weibo = weiboDao.findById(id, memberId);
        return weibo;
    }

    @Override
    @Transactional
    public boolean save(HttpServletRequest request, Member loginMember, String content, String pictures) {
        return save(request, loginMember, content, pictures, null);
    }

    @Override
    public boolean save(HttpServletRequest request, Member loginMember, String content, String pictures, String md5) {
        if ("0".equals(request.getServletContext().getAttribute(ConfigUtil.WEIBO_POST.toUpperCase()))) {
            throw new OpeErrorException("微博已关闭");
        }
        ValidUtill.checkIsNull(content, Messages.CONTENT_NOT_EMPTY);
        if (content.length() > Integer.parseInt((String) request.getServletContext().getAttribute(ConfigUtil.WEIBO_POST_MAXCONTENT.toUpperCase()))) {
            throw new ParamException("内容不能超过" + request.getServletContext().getAttribute(ConfigUtil.WEIBO_POST_MAXCONTENT.toUpperCase()) + "字");
        }
        Weibo weibo = new Weibo();
        weibo.setMemberId(loginMember.getId());
        weibo.setContent(content);
        weibo.setStatus(1);
        if (StringUtils.isEmpty(pictures)) {
            //普通文本
            weibo.setType(0);
        } else {
            //图片
            weibo.setType(1);
        }
        weibo.setMd5(md5 == null ? "" : md5);
        int result = weiboDao.save(weibo);
        if (result == 1) {
            //@会员处理并发送系统消息
            messageService.atDeal(loginMember.getId(), content, AppTag.WEIBO, MessageType.WEIBO_REFER, weibo.getId());
            pictureService.update(weibo.getId(), pictures, content);
            actionLogService.save(loginMember.getCurrLoginIp(), loginMember.getId(), ActionUtil.POST_WEIBO, "", ActionLogType.WEIBO.getValue(), weibo.getId());
            //发布微博奖励
            scoreDetailService.scoreBonus(loginMember.getId(), ScoreRuleConsts.RELEASE_WEIBO, weibo.getId());
        }
        return result == 1;
    }

    @Override
    public ResultModel<Weibo> listByPage(Page page, int memberId, int loginMemberId, String key) {
        if (StringUtils.isNotBlank(key)) {
            key = "%" + key.trim() + "%";
        }
        List<Weibo> list = weiboDao.listByPage(page, memberId, loginMemberId, key);
        list = this.atFormat(list);
        ResultModel model = new ResultModel(0, page);
        model.setData(list);
        return model;
    }

    @Transactional
    @Override
    public boolean delete(HttpServletRequest request, Member loginMember, int id) {
        Weibo weibo = this.findById(id, loginMember.getId());
        ValidUtill.checkIsNull(weibo, Messages.WEIBO_NOT_EXISTS);
        weiboDao.delete(id);
        //扣除积分
        scoreDetailService.scoreCancelBonus(loginMember.getId(), ScoreRuleConsts.RELEASE_WEIBO, id);
        pictureService.deleteByForeignId(request, id);
        actionLogService.save(loginMember.getCurrLoginIp(), loginMember.getId(), ActionUtil.DELETE_WEIBO, "ID：" + weibo.getId() + "，内容：" + weibo.getContent());
        return true;
    }

    @Transactional
    @Override
    public boolean userDelete(HttpServletRequest request, Member loginMember, int id) {
        Weibo weibo = this.findById(id, loginMember.getId());
        ValidUtill.checkIsNull(weibo, Messages.WEIBO_NOT_EXISTS);
        if (loginMember.getIsAdmin() == 0 && (loginMember.getId().intValue() != weibo.getMember().getId().intValue())) {
            throw new OpeErrorException("没有权限");
        }
        return this.delete(request, loginMember, id);
    }

    @Override
    public List<Weibo> hotList(int loginMemberId) {
        List<Weibo> hotList = weiboDao.hotList(loginMemberId);
        return hotList;
    }

    @Transactional
    @Override
    public ResultModel favor(Member loginMember, int weiboId) {
        ValidUtill.checkParam(weiboId != 0);
        Weibo weibo = this.findById(weiboId, loginMember.getId());
        ResultModel resultModel = new ResultModel(0);
        if (weiboFavorService.find(weiboId, loginMember.getId()) == null) {
            //增加
            weiboDao.favor(weiboId, 1);
            weibo.setFavor(weibo.getFavor() + 1);
            weiboFavorService.save(weiboId, loginMember.getId());
            //发布微博奖励
            scoreDetailService.scoreBonus(loginMember.getId(), ScoreRuleConsts.WEIBO_RECEIVED_THUMBUP, weiboId);
            //点赞之后发送系统信息
            messageService.diggDeal(loginMember.getId(), weibo.getMemberId(), AppTag.WEIBO, MessageType.WEIBO_ZAN, weibo.getId());
        } else {
            //减少
            weiboDao.favor(weiboId, -1);
            weibo.setFavor(weibo.getFavor() - 1);
            weiboFavorService.delete(weiboId, loginMember.getId());
            //扣除积分
            scoreDetailService.scoreCancelBonus(loginMember.getId(), ScoreRuleConsts.WEIBO_RECEIVED_THUMBUP, weiboId);
            resultModel.setCode(1);
        }
        resultModel.setData(weibo.getFavor());
        return resultModel;
    }

    @Override
    public List<Weibo> listByCustom(int loginMemberId, String sort, int num, int day) {
        return weiboDao.listByCustom(loginMemberId, sort, num, day);
    }

    public Weibo atFormat(Weibo weibo) {
        weibo.setContent(memberService.atFormat(weibo.getContent()));
        return weibo;
    }

    public List<Weibo> atFormat(List<Weibo> weiboList) {
        for (Weibo weibo : weiboList) {
            atFormat(weibo);
        }
        return weiboList;
    }
}
