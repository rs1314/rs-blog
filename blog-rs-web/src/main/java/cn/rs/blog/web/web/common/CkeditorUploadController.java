package cn.rs.blog.web.web.common;


import cn.rs.blog.commoms.utils.sina.GeneralUtils;
import cn.rs.blog.commoms.utils.sina.SinaPicBedUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by rs
 */
@Controller
@RequestMapping("/ckeditorUpload")
public class CkeditorUploadController extends BaseController {

    //新浪账户名
    @Value("${sina.username}")
    private String sinaUserName;

    //密码
    @Value("${sina.password}")
    private String sinaPassWord;

    /**
     * 上传图片
     *
     * @param upload
     */
    @PostMapping(value = "/uploadImage")
    public void uploadImage(@RequestParam MultipartFile[] upload) {
        String callback = request.getParameter("CKEditorFuncNum");
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!ServletFileUpload.isMultipartContent(request)) {
            out.print("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'请选择文件');</script>");
            out.flush();
            out.close();
        }

        String fileName;//上传的图片文件名
        String suffix;//上传图片的文件扩展名
        for (MultipartFile file : upload) {
            if (file.getSize() > 1 * 1024 * 1024) {
                out.print("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于10M');</script>");
                out.flush();
                out.close();
            }

            fileName = file.getOriginalFilename();
            suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
            String[] imageExtensionNameArray = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
            String allImageExtensionName = "";
            boolean isContain = false;//默认不包含上传图片文件扩展名
            for (int i = 0; i < imageExtensionNameArray.length; i++) {
                if (suffix.equals(imageExtensionNameArray[i])) {
                    isContain = true;
                }
                if (i == 0) {
                    allImageExtensionName += imageExtensionNameArray[i];
                } else {
                    allImageExtensionName += " , " + imageExtensionNameArray[i];
                }

            }
            List<String> urls = new ArrayList<String>();
            if (isContain) {//包含
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
                    MultipartFile[] multipartFiles = {file};
                    urls = SinaPicBedUtil.uploadFile(multipartFiles, cookies, 2);
                } catch (IOException e) {
                    out.print("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'" + e + "');</script>");
                    out.flush();
                    out.close();
                }
                if (urls != null && urls.size() > 0) {
                    String imageUrl = urls.get(0);
                    out.print("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageUrl + "'," + "'');</script>");

                } else {
                    out.print("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'上传失败');</script>");
                }
                out.flush();
                out.close();

            } else {
                out.print("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为" + allImageExtensionName + "文件）');</script>");
                out.flush();
                out.close();
            }
        }
    }
}