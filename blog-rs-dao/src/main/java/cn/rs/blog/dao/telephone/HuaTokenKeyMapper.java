package cn.rs.blog.dao.telephone;

import cn.rs.blog.bean.telephone.HuaTokenKey;
import java.util.List;

public interface HuaTokenKeyMapper {
    int insert(HuaTokenKey record);

    List<HuaTokenKey> selectAll();
}