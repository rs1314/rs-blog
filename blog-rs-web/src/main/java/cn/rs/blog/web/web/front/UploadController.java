package cn.rs.blog.web.web.front;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.Picture;
import cn.rs.blog.bean.picture.PictureAlbum;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.commoms.utils.sina.GeneralUtils;
import cn.rs.blog.commoms.utils.sina.SinaPicBedUtil;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.picture.IPictureAlbumService;
import cn.rs.blog.service.picture.IPictureService;
import cn.rs.blog.web.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传
 * Created by rs
 */
@Controller
@RequestMapping("/")
public class UploadController extends BaseController {
    //新浪账户名
    @Value("${sina.username}")
    private String sinaUserName;

    //密码
    @Value("${sina.password}")
    private String sinaPassWord;

    @Autowired
    private IMemberService memberService;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private IPictureAlbumService pictureAlbumService;

    @RequestMapping("${rsblog.managePath}/uploadImage")
    @ResponseBody
    public Object manageUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        return uploadImage(file, 0);
    }


    /**
     * 微博图片上传
     *
     * @param file
     * @return
     */
    @RequestMapping("/weiboUploadImage")
    @ResponseBody
    public Object weiboUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        return uploadImage(file, 2);
    }

    /**
     * 普通图片上传
     *
     * @param file
     * @return
     */
    @RequestMapping("/uploadImage")
    @ResponseBody
    public Object indexUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        return uploadImage(file, 0);
    }

    /**
     * 缩略图上传
     *
     * @param file
     * @return
     */
    @RequestMapping("/thumbnailUploadImage")
    @ResponseBody
    public Object thumbnailUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        return uploadImage(file, 11);
    }

    /**
     * 保存图片
     *
     * @param file
     * @param type 0是普通图片，1是文章图片，2是微博图片，3是群组帖子图片，11是缩略图
     * @return
     */
    private Object uploadImage(MultipartFile file, int type){
        //关联微博表和图片表
        String md5 = "";
        Member loginMember = MemberUtil.getLoginMember(request);
        if (loginMember == null) {
            return new ResultModel(-1, "没有登录" );
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (suffix == null || (!".png".equals(suffix.toLowerCase()) && !".jpg".equals(suffix.toLowerCase()) && !".gif".equals(suffix.toLowerCase()) && !".jpeg".equals(suffix.toLowerCase()) && !".bmp".equals(suffix.toLowerCase()))) {
            return new ResultModel(-1, "格式不支持");
        }
        MultipartFile[] multipartFiles = {file};
        //保存
        if (GeneralUtils.isEmpty(multipartFiles)) {
            return new ResultModel(-1, "图片不能为空" );
        } else if (multipartFiles.length > 1) {
            return new ResultModel(-1, "一次最多只能上传1张图片");

        }
        List<String> urls = new ArrayList<String>();
        String url = "";
        try {
            String base64name = GeneralUtils.base64Encode(sinaUserName);
            String cookies = SinaPicBedUtil.getSinaCookie(base64name, sinaPassWord);// 持久化起来 不用每次都登录 一般失效7天左右
            /**
             * * "0">large
             "1">mw1024
             "2">mw690
             "3">bmiddle
             "4">small
             "5">thumb180
             "6">thumbnail
             "7">square
             */
            //上传回来 的图片地址
            urls = SinaPicBedUtil.uploadFile(multipartFiles, cookies, 0);
            //上传成功的图片URL，如果是文章、群组帖子、微博图片，则返回图片ID
            if (urls != null && urls.size() > 0) {
                if (type == 2) {
                    try {
                        PictureAlbum pictureAlbum = pictureAlbumService.findWeiboAlbum(loginMember.getId());
                        if (pictureAlbum == null) {
                            pictureAlbum = new PictureAlbum();
                            pictureAlbum.setType(2);
                            pictureAlbum.setName("微博配图");
                            pictureAlbum.setMemberId(loginMember.getId());
                            pictureAlbum.setJuri(0);
                            pictureAlbum.setCover(urls.get(0));
                            pictureAlbumService.save(pictureAlbum);
                        }
                        BufferedImage sourceImg = ImageIO.read(file.getInputStream());
                        Picture picture = new Picture();
                        picture.setWidth(sourceImg.getWidth());
                        picture.setHeight(sourceImg.getHeight());
                        md5 =UUID.randomUUID().toString();
                        picture.setMd5(md5);
                        //生成缩略图和小图片
                     //   new ImageUtil().dealImage(targetFile);
                        picture.setPath(urls.get(0));
                        //上传小图片
                        List<String> smailPricceUrls = SinaPicBedUtil.uploadFile(multipartFiles, cookies, 6);
                        if(smailPricceUrls!=null && smailPricceUrls.size()>0){
                            picture.setThumbnailPath(smailPricceUrls.get(0));
                            picture.setSmallPath(smailPricceUrls.get(0));
                        }else{
                            picture.setThumbnailPath(urls.get(0));
                            picture.setSmallPath(urls.get(0));
                        }

                        picture.setType(type);
                        picture.setMemberId(loginMember.getId());
                        picture.setAlbumId(pictureAlbum.getId());
                        pictureService.save(picture);
                        url = urls.get(0);
                        if (StringUtils.isEmpty(pictureAlbum.getCover()) || Const.DEFAULT_PICTURE_COVER.equals(pictureAlbum.getCover())) {
                            pictureAlbum.setCover(picture.getSmallPath());
                            pictureAlbumService.update(pictureAlbum);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new ResultModel(-1, "上传失败" );
                    }
                } else if (type == 11) {
                    List<String> smailPricceUrls = SinaPicBedUtil.uploadFile(multipartFiles, cookies, 6);
                    url = smailPricceUrls.get(0);
                    md5 =UUID.randomUUID().toString();;
                } else {
                    url = urls.get(0);
                    md5 =UUID.randomUUID().toString();;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel(-1, "系统出错" );
        }
        if("".equalsIgnoreCase(md5)){
            return new ResultModel(888, "上传失败", url,md5);
        }
        return new ResultModel(0, "上传成功", url,md5);
    }

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @RequestMapping("member/uploadAvatar")
    @ResponseBody
    public Object uploadAvatar(@RequestParam(value = "__avatar1", required = false) MultipartFile file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String fileName = UUID.randomUUID() + ".jpg";
        String ymd = simpleDateFormat.format(new Date());
        String filePath = Const.UPLOAD_PATH + "/avatar/" + ymd + "/";
        String savePath = request.getServletContext().getRealPath(filePath);
        File baseFile = new File(savePath);
        File targetFile = new File(savePath, fileName);

        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        Member findMember = memberService.findById(loginMember.getId());
        Map result = new HashMap();
        if (findMember != null) {
            String oldAvatar = findMember.getAvatar();
            findMember.setAvatar(filePath + fileName);
            ResultModel resultModel = memberService.updateAvatar(findMember, oldAvatar, request);
            if (resultModel.getCode() == 0) {
                MemberUtil.setLoginMember(request, findMember);
            }
            result.put("success", resultModel.getCode() == 0);
            result.put("msg", resultModel.getMessage());
        } else {
            result.put("success", true);
            result.put("msg", "会员不存在!");
        }
        return result;
    }
}
