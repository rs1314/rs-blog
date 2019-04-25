package cn.rs.blog.service.telephone;

import cn.rs.blog.bean.telephone.AppVO;
import cn.rs.blog.bean.telephone.HuaType;

import java.util.List;
import java.util.Map;

public interface AppService {
    /**
     * 获取中部的类目功能
     */
    List<HuaType> midTypeData();

    Map<String,Object> midTypeDataList(AppVO appVo);

    Map<String, Object> getTypeDataDetail(AppVO appVo);
}
