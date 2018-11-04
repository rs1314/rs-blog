package cn.rs.blog.service.cms;

import java.util.List;

import cn.rs.blog.bean.cms.ArticleComment;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IArticleCommentService {

    ArticleComment findById(int id);

    boolean save(Member loginMember, String content, Integer articleId);

    boolean delete(Member loginMember, int id);

    List<ArticleComment> listByPage(Page page, int articleId, String key);

    void deleteByArticle(Integer articleId);
}
