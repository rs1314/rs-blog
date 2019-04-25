package cn.rs.blog.dao.telephone;

import cn.rs.blog.bean.telephone.HuaMiYU;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuaMiYUMapper {
    int deleteByPrimaryKey(String id);

    int insert(HuaMiYU record);

    HuaMiYU selectByPrimaryKey(String id);

    List<HuaMiYU> selectAll();

    int updateByPrimaryKey(HuaMiYU record);

    List<HuaMiYU> selectByPrimaryKeyByPageVo(@Param("type") String type, @Param("page") int page);

    HuaMiYU getTypeDataDetail(@Param("type") String type, @Param("typeuuid") String typeuuid);
}