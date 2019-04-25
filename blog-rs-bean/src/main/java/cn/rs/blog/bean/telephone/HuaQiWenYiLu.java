package cn.rs.blog.bean.telephone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuaQiWenYiLu {
    private String id;

    private Integer typeId;

    private String qiwenyiluTitle;

    private String qiwenyiluTime;

    private Integer qiwenyiluOrder;

    private Integer qiwenyiluIsshow;

    private Integer qiwenyiluClicknum;

    private String qiwenyiluDetail;

    private String qiwenyiluDetails;

    private String qiwenyiluPhoto;

    public String getQiwenyiluDetails() {
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(qiwenyiluDetails);
        qiwenyiluDetails = m_html.replaceAll(""); //过滤html标签


        return qiwenyiluDetails;
    }

    public void setQiwenyiluDetails(String qiwenyiluDetails) {
        this.qiwenyiluDetails = qiwenyiluDetails;
    }

    public String getQiwenyiluTitle() {
        if (qiwenyiluTitle != null && qiwenyiluTitle.indexOf("<b>") != -1) {
            qiwenyiluTitle = qiwenyiluTitle.replaceAll("<b>", "").replaceAll("</b>", "");
        }

        return qiwenyiluTitle;
    }

    public void setQiwenyiluTitle(String qiwenyiluTitle) {
        this.qiwenyiluTitle = qiwenyiluTitle;
    }
}