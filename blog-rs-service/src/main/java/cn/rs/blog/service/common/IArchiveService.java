package cn.rs.blog.service.common;

import cn.rs.blog.bean.common.Archive;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.dto.ResultModel;


/**
 * Created by rs
 */
public interface IArchiveService {

    Archive findByArchiveId(int id);

    boolean save(Member member, Archive archive);

    boolean update(Member member, Archive archive);

    boolean delete(int id);

    void updateViewCount(int id);

    ResultModel favor(Member loginMember, int archiveId);
}
