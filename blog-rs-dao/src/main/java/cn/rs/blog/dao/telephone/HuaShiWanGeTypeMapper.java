package cn.rs.blog.dao.telephone;

import cn.rs.blog.bean.telephone.HuaShiWanGeType;
import cn.rs.blog.bean.telephone.HuaType;

import java.util.List;

public interface HuaShiWanGeTypeMapper {
    int deleteByPrimaryKey(Integer whiwangeTypeId);

    int insert(HuaShiWanGeType record);

    HuaShiWanGeType selectByPrimaryKey(Integer whiwangeTypeId);

    List<HuaShiWanGeType> selectAll();

    int updateByPrimaryKey(HuaShiWanGeType record);

    List<HuaType> selectAllHuaType();
}