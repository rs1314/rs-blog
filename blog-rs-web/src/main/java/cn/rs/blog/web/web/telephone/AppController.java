package cn.rs.blog.web.web.telephone;


import cn.rs.blog.bean.telephone.AppVO;
import cn.rs.blog.bean.telephone.HuaType;

import cn.rs.blog.commoms.utils.ResultUtils;
import cn.rs.blog.service.telephone.AppService;
import cn.rs.blog.web.utils.AppThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app/rs")
public class AppController {
    @Autowired
    AppService appService;

    /**
     * 获取功能类别功能
     * @return
     */
    @PostMapping("app/midtype")
    public Object midTypeData(@RequestParam("key")String key) {
        List<HuaType> huaType = appService.midTypeData();
        return ResultUtils.getDefaultResult(huaType);
    }


    /**
     * 类别功能的数据获取
     * @return
     */
    @PostMapping("app/listtype")
    public Map<String,Object> midTypeDataList() {
        AppVO appVo = AppThreadLocal.getAppVo();
        Map<String, Object> stringObjectMap = appService.midTypeDataList(appVo);
        return stringObjectMap;
    }

    /**
     * 通过type和uuid获取单个详情数据
     * @return
     */
    @PostMapping("app/listtype/detail")
    public Map<String,Object> getTypeDataDetail(){
        AppVO appVo = AppThreadLocal.getAppVo();
        Map<String, Object> stringObjectMap = appService.getTypeDataDetail(appVo);
        return stringObjectMap;
    }

}
