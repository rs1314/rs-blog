package cn.rs.blog.service.cms;

import java.util.List;

import cn.rs.blog.bean.cms.Article;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IArticleService {

    Article findById(int id);

    Article findById(int id,Member loginMember);

    boolean save(Member member, Article article);

    boolean update(Member member, Article article);

    boolean delete(Member member, int id);

    ResultModel listByPage(Page page, String key, int cateid, int status, int memberId);

    void updateViewCount(int id);

    boolean audit(int id);

    ResultModel favor(Member loginMember, int articleId);

    List<Article> listByCustom(int cid,String sort,int num,int day,int thumbnail);
}
