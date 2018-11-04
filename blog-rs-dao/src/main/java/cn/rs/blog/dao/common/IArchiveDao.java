package cn.rs.blog.dao.common;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.common.Archive;

/**
 * 文章DAO接口
 * Created by rs.
 */
public interface IArchiveDao extends IBaseDao<Archive> {

    Archive findByArchiveId(@Param("archiveId") Integer archiveId);

    /**
     * 更新阅读数
     * @param archiveId
     * @return
     */
    int updateViewCount(@Param("archiveId") int archiveId);

    int favor(@Param("archiveId") int archiveId, @Param("num") int num);
    
}