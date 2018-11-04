package cn.rs.blog.bean.picture;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureAlbumFavor {
    private Integer id;

    private Date createTime;

    private Integer pictureAlbumId;

    private Integer memberId;

  
}