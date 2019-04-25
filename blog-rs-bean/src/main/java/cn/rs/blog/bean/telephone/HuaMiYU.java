package cn.rs.blog.bean.telephone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuaMiYU {
    private String id;

    private Integer typeId;

    private String miyuTitle;

    private String miyuAnsker;

    private String miyuTime;

    private Integer miyuOredr;

    private Integer miyuIsshow;

    private Integer miyuClicknum;

    private String miyuTieshi;

    private String miyuPhoto;

}