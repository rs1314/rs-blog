package cn.rs.blog.dao.telephone;

import cn.rs.blog.bean.telephone.HuaQiWenYiLu;
import cn.rs.blog.bean.telephone.HuaShiWanGe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuaShiWanGeMapper {
    int deleteByPrimaryKey(String shiwangeId);

    int insert(HuaShiWanGe record);

    HuaShiWanGe selectByPrimaryKey(String shiwangeId);

    List<HuaShiWanGe> selectAll();

    int updateByPrimaryKey(HuaShiWanGe record);

    int midTypeDataListCount(@Param("type")String type);

    List<HuaShiWanGe> selectByPrimaryKeyByPageVo(@Param("type") String type,@Param("page")int page);

    HuaShiWanGe getTypeDataDetail(@Param("type")String type, @Param("typeuuid")String typeuuid);

    void updateByPrimaryKeyClickNum(@Param("type")String type, @Param("typeuuid")String typeuuid);
}