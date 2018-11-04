package cn.rs.blog.service.cms.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rs.blog.bean.cms.Article;
import cn.rs.blog.bean.cms.ArticleComment;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.ActionUtil;
import cn.rs.blog.commoms.utils.ScoreRuleConsts;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.consts.AppTag;
import cn.rs.blog.core.enums.MessageType;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.dao.cms.IArticleCommentDao;
import cn.rs.blog.service.cms.IArticleCommentService;
import cn.rs.blog.service.cms.IArticleService;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.member.IMessageService;
import cn.rs.blog.service.member.IScoreDetailService;
import cn.rs.blog.service.system.IActionLogService;

/**
 * Created by rs
 */
@Service("articleCommentService")
public class ArticleCommentServiceImpl implements IArticleCommentService {
    @Autowired
    private IArticleCommentDao articleCommentDao;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IActionLogService actionLogService;
    @Autowired
    private IScoreDetailService scoreDetailService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMemberService memberService;

    @Override
    public ArticleComment findById(int id) {
        return this.atFormat(articleCommentDao.findById(id));
    }

    @Override
    public boolean save(Member loginMember, String content, Integer articleId) {
        Article article = articleService.findById(articleId);
        ValidUtill.checkIsNull(article, Messages.ARTICLE_NOT_EXISTS);
        ValidUtill.checkIsBlank(content, Messages.CONTENT_NOT_EMPTY);
        ArticleComment articleComment = new ArticleComment();
        articleComment.setMemberId(loginMember.getId());
        articleComment.setArticleId(articleId);
        articleComment.setContent(content);
        int result = articleCommentDao.save(articleComment);
        if(result == 0){
            throw new OpeErrorException();
        }
        //@会员处理并发送系统消息
        messageService.atDeal(loginMember.getId(),content, AppTag.CMS, MessageType.CMS_ARTICLE_COMMENT_REFER,articleComment.getId());
        messageService.diggDeal(loginMember.getId(),article.getMemberId(),content,AppTag.CMS,MessageType.CMS_ARTICLR_REPLY,article.getId());
        //文章评论奖励
        scoreDetailService.scoreBonus(loginMember.getId(), ScoreRuleConsts.ARTICLE_REVIEWS,articleComment.getId());
        return true;
    }

    @Override
    public List listByPage(Page page, int articleId, String key) {
        if (StringUtils.isNotBlank(key)){
            key = "%"+key+"%";
        }
        List<ArticleComment> list = articleCommentDao.listByPage(page, articleId, key);
        this.atFormat(list);
        return list;
    }

    @Override
    public void deleteByArticle(Integer articleId) {
        articleCommentDao.deleteByArticle(articleId);
    }

    @Override
    @Transactional
    public boolean delete(Member loginMember, int id) {
        int result = articleCommentDao.delete(id);
        if(result == 0){
            throw new OpeErrorException();
        }
        //扣除积分
        scoreDetailService.scoreCancelBonus(loginMember.getId(), ScoreRuleConsts.ARTICLE_REVIEWS,id);
        actionLogService.save(loginMember.getCurrLoginIp(),loginMember.getId(), ActionUtil.DELETE_ARTICLE_COMMENT,"ID："+id);
        return true;
    }

    public ArticleComment atFormat(ArticleComment articleComment){
        articleComment.setContent(memberService.atFormat(articleComment.getContent()));
        return articleComment;
    }

    public List<ArticleComment> atFormat(List<ArticleComment> articleCommentList){
        for (ArticleComment articleComment : articleCommentList){
            atFormat(articleComment);
        }
        return articleCommentList;
    }
}
