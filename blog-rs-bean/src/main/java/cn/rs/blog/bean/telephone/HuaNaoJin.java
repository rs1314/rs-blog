package cn.rs.blog.bean.telephone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuaNaoJin {
    private String id;

    private Integer typeId;

    private String naojinTitle;

    private String naojinAnsker;

    private String naojinTime;

    private Integer naojinOredr;

    private Integer naojinIsshow;

    private Integer naojinClicknum;

    private String naojinPhoto;

}