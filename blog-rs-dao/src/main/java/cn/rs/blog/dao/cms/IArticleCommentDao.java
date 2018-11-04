package cn.rs.blog.dao.cms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.cms.ArticleComment;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * 文章评论DAO接口
 * Created by rs
 */
public interface IArticleCommentDao extends IBaseDao<ArticleComment> {

    List<ArticleComment> listByPage(@Param("page") Page page, @Param("articleId") Integer articleId, @Param("key") String key);

    int deleteByArticle(@Param("articleId") Integer articleId);
}