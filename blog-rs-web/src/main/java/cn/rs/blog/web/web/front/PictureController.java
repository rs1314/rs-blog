package cn.rs.blog.web.web.front;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.Picture;
import cn.rs.blog.bean.picture.PictureAlbum;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.exception.NotLoginException;
import cn.rs.blog.core.exception.ParamException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.ErrorUtil;
import cn.rs.blog.core.utils.ImageUtil;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.picture.IPictureAlbumService;
import cn.rs.blog.service.picture.IPictureCommentService;
import cn.rs.blog.service.picture.IPictureService;
import cn.rs.blog.web.interceptor.UserLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;


/**
 *
 * @author rs
 */
@Controller("frontPictureController")
@RequestMapping("/")
public class PictureController extends BaseController {
    private static final String MEMBER_FTL_PATH = "/member/";
    @Autowired
    private IPictureAlbumService pictureAlbumService;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private IPictureCommentService pictureCommentService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private RsBlogConfig rsBlogConfig;

    @RequestMapping(value = "/picture/album/{memberId}",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String album(Model model,@PathVariable("memberId") Integer memberId){
        Member findMember = memberService.findById(memberId);
        ResultModel resultModel = pictureAlbumService.listByMember(memberId);
        model.addAttribute("model", resultModel);
        model.addAttribute("member",findMember);
        return rsBlogConfig.getFrontTemplate() + "/picture/albums";
    }

    @RequestMapping(value = "/member/picture/album",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String album(Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        ResultModel resultModel = pictureAlbumService.listByMember(loginMember.getId());
        model.addAttribute("model", resultModel);
        return MEMBER_FTL_PATH + "/picture/albums";
    }

    @RequestMapping(value = "/member/picture/addAlbum",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String addAlbum(Model model){
        return MEMBER_FTL_PATH + "/picture/addAlbums";
    }

    @RequestMapping(value = "/member/picture/saveAlbum",method = RequestMethod.POST)
    @Before(UserLoginInterceptor.class)
    @ResponseBody
    public ResultModel saveAlbum(PictureAlbum pictureAlbum){
        if (StringUtils.isEmpty(pictureAlbum.getName())){
            return new ResultModel(-1,"相册名称不能为空");
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        pictureAlbum.setMemberId(loginMember.getId());
        return new ResultModel(pictureAlbumService.save(pictureAlbum));
    }

    @RequestMapping(value = "/picture/list/{memberId}-{albumId}",method = RequestMethod.GET)
    public String indexList(Model model,@PathVariable("memberId") Integer memberId,@PathVariable("albumId") Integer albumId){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        PictureAlbum pictureAlbum = pictureAlbumService.findById(albumId);
        if (pictureAlbum == null || memberId.intValue() != pictureAlbum.getMemberId().intValue()){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1010, Const.INDEX_ERROR_FTL_PATH);
        }
        if (pictureAlbum.getJuri() != 0){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1012, Const.INDEX_ERROR_FTL_PATH);
        }
        ResultModel resultModel = pictureService.listByAlbum(page,albumId,loginMemberId);
        model.addAttribute("model", resultModel);
        model.addAttribute("pictureAlbum",pictureAlbum);
        return rsBlogConfig.getFrontTemplate() + "/picture/lists";
    }

    @RequestMapping(value = "/member/picture/list/{memberId}-{albumId}",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String list(Model model,@PathVariable("memberId") Integer memberId,@PathVariable("albumId") Integer albumId){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        if (loginMemberId != memberId){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1001, Const.INDEX_ERROR_FTL_PATH);
        }
        PictureAlbum pictureAlbum = pictureAlbumService.findById(albumId);
        if (pictureAlbum == null || memberId.intValue() != pictureAlbum.getMemberId().intValue()){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1010, Const.INDEX_ERROR_FTL_PATH);
        }
        ResultModel resultModel = pictureService.listByAlbum(page,albumId,loginMemberId);
        model.addAttribute("model", resultModel);
        model.addAttribute("pictureAlbum",pictureAlbum);
        return MEMBER_FTL_PATH + "/picture/lists";
    }

    @RequestMapping(value = {"/picture","/picture/"},method = RequestMethod.GET)
    public String index(Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        Page page = new Page(request);
        ResultModel resultModel = pictureService.listByPage(page,loginMemberId);
        model.addAttribute("model", resultModel);
        return rsBlogConfig.getFrontTemplate() + "/picture/indexs";
    }

    @RequestMapping(value = "/picture/indexData",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel indexData(){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        ResultModel resultModel = pictureService.listByPage(page,loginMemberId);
        return resultModel;
    }

    @RequestMapping(value = "/picture/detail/{pictureId}",method = RequestMethod.GET)
    public String detail(Model model,@PathVariable("pictureId") Integer pictureId) throws NotLoginException {
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        Picture picture = pictureService.findById(pictureId,loginMemberId);
        if (picture == null){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1011, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("picture",picture);
        return rsBlogConfig.getFrontTemplate() + "/picture/details";
    }


    @RequestMapping(value="/picture/comment/{pictureId}",method = RequestMethod.POST)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel comment(@PathVariable("pictureId") Integer pictureId, String content) throws NotLoginException {
        Member loginMember = MemberUtil.getLoginMember(request);
        if(StringUtils.isEmpty(content)){
            return new ResultModel(-1,"内容不能为空");
        }
        if(content.length() > 500){
            return new ResultModel(-1,"评论内容不能超过500长度");
        }
        return new ResultModel(pictureCommentService.save(loginMember,content,pictureId));
    }

    @RequestMapping(value="/picture/commentList/{pictureId}.json",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel commentList(@PathVariable("pictureId") Integer pictureId){
        Page page = new Page(request);
        if(pictureId == null){
            pictureId = 0;
        }
        return pictureCommentService.listByPicture(page,pictureId);
    }

    @RequestMapping(value="/picture/favor/{pictureId}",method = RequestMethod.GET)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel favor(@PathVariable("pictureId") Integer pictureId) throws NotLoginException, ParamException {
        Member loginMember = MemberUtil.getLoginMember(request);
        if(pictureId == null) {
            throw new ParamException();
        }
        return pictureService.favor(loginMember,pictureId);
    }

    @RequestMapping(value="/member/picture/uploadPic")
    @Before(UserLoginInterceptor.class)
    public String uploadPic(Model model,Integer albumId) {
        PictureAlbum pictureAlbum = pictureAlbumService.findById(albumId);
        Member loginMember = MemberUtil.getLoginMember(request);
        if (pictureAlbum == null){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1010, Const.INDEX_ERROR_FTL_PATH);
        }
        if (pictureAlbum.getMemberId().intValue() != loginMember.getId().intValue()){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1001, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("albumId",albumId);
        return MEMBER_FTL_PATH + "/picture/uploadPics";
    }

    @RequestMapping(value="/member/picture/uploadPic/{albumId}")
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel uploadPic(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable("albumId") Integer albumId) {
        Member loginMember = MemberUtil.getLoginMember(request);
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        if(suffix == null || (!".png".equals(suffix.toLowerCase()) && !".jpg".equals(suffix.toLowerCase()) && !".gif".equals(suffix.toLowerCase()) && !".jpeg".equals(suffix.toLowerCase()) && !".bmp".equals(suffix.toLowerCase()))) {
            return new ResultModel(-1,"格式不支持");
        }
        String newFileName = UUID.randomUUID() + suffix;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        String path = Const.UPLOAD_PATH + "/images/"+ymd+"/";
        String savePath = request.getServletContext().getRealPath(path);
        File baseFile = new File(savePath);
        File targetFile = new File(baseFile, newFileName);

        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PictureAlbum pictureAlbum = pictureAlbumService.findById(albumId);
            if (pictureAlbum == null){
                return new ResultModel(-1,"相册不存在");
            }

            BufferedImage sourceImg = ImageIO.read(new FileInputStream(targetFile));
            Picture picture = new Picture();
            picture.setWidth(sourceImg.getWidth());
            picture.setHeight(sourceImg.getHeight());
            picture.setMd5(DigestUtils.md5Hex(new FileInputStream(targetFile)));
            //生成缩略图和小图片
            new ImageUtil().dealImage(targetFile);
            picture.setPath(path + newFileName);
            picture.setThumbnailPath(path + ImageUtil.THUMB_DEFAULT_PREVFIX + newFileName);
            picture.setSmallPath(path + ImageUtil.SMALL_DEFAULT_PREVFIX + newFileName);
            picture.setType(0);
            picture.setMemberId(loginMember.getId());
            picture.setAlbumId(pictureAlbum.getId());
            pictureService.save(picture);
            if (Const.DEFAULT_PICTURE_COVER.equals(pictureAlbum.getCover())){
                pictureAlbum.setCover(picture.getSmallPath());
                pictureAlbumService.update(pictureAlbum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultModel(0,"上传成功");
    }
}
