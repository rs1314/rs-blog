package cn.rs.blog.service.common;


import cn.rs.blog.bean.common.ArchiveFavor;

/**
 * 文章点赞Service接口
 * Created by rs
 */
public interface IArchiveFavorService {

    ArchiveFavor find(Integer archiveId, Integer memberId);

    void save(Integer archiveId, Integer memberId);

    void delete(Integer archiveId, Integer memberId);
}