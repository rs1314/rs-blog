package cn.rs.blog.dao.telephone;

import cn.rs.blog.bean.telephone.HuaQiWenYiLu;
import cn.rs.blog.bean.telephone.HuaShiWanGe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuaQiWenYiLuMapper {
    int deleteByPrimaryKey(String id);

    int insert(HuaQiWenYiLu record);

    HuaQiWenYiLu selectByPrimaryKey(String id);

    List<HuaQiWenYiLu> selectAll();

    int updateByPrimaryKey(HuaQiWenYiLu record);

    List<HuaQiWenYiLu> selectByPrimaryKeyByPageVo(@Param("type") String type,@Param("page") int page);

    HuaQiWenYiLu getTypeDataDetail(@Param("type")String type, @Param("typeuuid")String typeuuid);
}