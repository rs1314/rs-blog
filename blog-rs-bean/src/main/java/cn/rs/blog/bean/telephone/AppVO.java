package cn.rs.blog.bean.telephone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppVO {
    private String key;
    private String accecy;
    private String userId;
    /**
     * 页码
     */
    private String page;
    /**
     * 选择那个类别数据
     */
    private String type;

    private String typeuuid;
}
