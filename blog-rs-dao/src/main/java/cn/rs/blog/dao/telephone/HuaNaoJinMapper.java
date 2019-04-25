package cn.rs.blog.dao.telephone;

import cn.rs.blog.bean.telephone.HuaNaoJin;
import cn.rs.blog.bean.telephone.HuaShiWanGe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuaNaoJinMapper {
    int deleteByPrimaryKey(String id);

    int insert(HuaNaoJin record);

    HuaNaoJin selectByPrimaryKey(String id);

    List<HuaNaoJin> selectAll();

    int updateByPrimaryKey(HuaNaoJin record);

    List<HuaNaoJin> selectByPrimaryKeyByPageVo(@Param("type") String type,@Param("page") int page);

    HuaNaoJin getTypeDataDetail(@Param("type")String type, @Param("typeuuid")String typeuuid);
}