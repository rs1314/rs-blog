package cn.rs.blog.dao.common;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.common.ArchiveFavor;

/**
 * 文章点赞DAO接口
 * Created by rs
 */
public interface IArchiveFavorDao extends IBaseDao<ArchiveFavor> {

    ArchiveFavor find(@Param("archiveId") Integer archiveId, @Param("memberId") Integer memberId);

    Integer save(@Param("archiveId") Integer archiveId, @Param("memberId") Integer memberId);

    Integer delete(@Param("archiveId") Integer archiveId, @Param("memberId") Integer memberId);
}