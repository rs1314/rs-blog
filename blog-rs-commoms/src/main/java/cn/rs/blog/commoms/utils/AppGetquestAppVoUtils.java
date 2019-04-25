package cn.rs.blog.commoms.utils;

import cn.rs.blog.bean.telephone.AppVO;
import cn.rs.blog.commoms.utils.sign.SignConfig;
import cn.rs.blog.commoms.utils.sign.SignDecodeUtils;

import javax.servlet.http.HttpServletRequest;

public class AppGetquestAppVoUtils {

    public static AppVO getAppVo(HttpServletRequest request) {
        AppVO appVO = new AppVO();

        String key = request.getParameter("key");
        String accecy = request.getParameter("accecy");
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        page = page ==null?"1":page;
        String type = request.getParameter("type");
        String typeuuid = request.getParameter("typeuuid");
        type = type ==null?"3":type;

      //  key = SignDecodeUtils.decodeprivatekey(key, SignConfig.PRIVATEKEY);
     //   accecy = SignDecodeUtils.decodeprivatekey(accecy, SignConfig.PRIVATEKEY);
     //   userId = SignDecodeUtils.decodeprivatekey(userId, SignConfig.PRIVATEKEY);

        appVO.setKey(key);
        appVO.setAccecy(accecy);
        appVO.setUserId(userId);
        appVO.setType(type);
        appVO.setPage(page);
        appVO.setTypeuuid(typeuuid);
        return appVO;
    }

}
