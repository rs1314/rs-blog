package cn.rs.blog.bean.telephone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppMiYUVo {
    /**
     * 查询的数据
     */
    private Object data;
    /**
     * 总页数
     */
    private Integer pages;
    /**
     * 当前页
     */
    private String page;
    /**
     * 类别
     */
    private String type;
}
