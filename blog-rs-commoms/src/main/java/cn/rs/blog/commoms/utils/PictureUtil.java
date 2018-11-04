package cn.rs.blog.commoms.utils;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.rs.blog.bean.picture.Picture;
import cn.rs.blog.core.utils.StringUtils;

/**
 * Created by rs
 */
public class PictureUtil {
    public static void delete(HttpServletRequest request, List<Picture> pictures){
        if (pictures != null){
            for (Picture picture : pictures){
                delete(request, picture);
            }
        }

    }

    public static void delete(HttpServletRequest request, Picture picture){
        String path = picture.getPath();
        String thumbnailPath = picture.getThumbnailPath();
        String smallPath = picture.getSmallPath();
        if(StringUtils.isNotEmpty(path)){
            File file = new File(request.getServletContext().getRealPath(path));
            if ((file.exists())){
                file.delete();
            }
        }
        if(StringUtils.isNotEmpty(thumbnailPath)){
            File file = new File(request.getServletContext().getRealPath(thumbnailPath));
            if ((file.exists())){
                file.delete();
            }
        }
        if(StringUtils.isNotEmpty(smallPath)){
            File file = new File(request.getServletContext().getRealPath(smallPath));
            if ((file.exists())){
                file.delete();
            }
        }
    }
}
