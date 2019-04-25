package cn.rs.blog.bean.telephone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuaType {
    private Integer typeId;

    private String typeName;

    private Integer typeOrder;

    private Integer typeIsshow;

    private String typePhoto;


}