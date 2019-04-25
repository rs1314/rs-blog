package cn.rs.blog.bean.telephone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuaShiWanGe {
    private String shiwangeId;

    private Integer typeId;

    private String shiwangeQuestion;

    private String shiwangeCreatetime;

    private Integer shiwangeClicknum;

    private Integer shiwangeIsshow;

    private Integer shiwangeOrder;

    private Integer shiwangeTypeId;

    private String shiwangeAnswerDetail;

    private String shiwangeAnswer;


}