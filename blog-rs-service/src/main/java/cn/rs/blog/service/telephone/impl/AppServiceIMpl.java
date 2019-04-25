package cn.rs.blog.service.telephone.impl;


import cn.rs.blog.bean.telephone.*;
import cn.rs.blog.commoms.utils.PageSizeUtils;
import cn.rs.blog.dao.telephone.*;
import cn.rs.blog.service.telephone.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppServiceIMpl implements AppService {
    @Autowired
    HuaShiWanGeTypeMapper shiWanGeTypeMapper;
    @Autowired
    HuaShiWanGeMapper huaShiWanGeMapper;
    @Autowired
    HuaNaoJinMapper huaNaoJinMapper;
    @Autowired
    HuaQiWenYiLuMapper huaQiWenYiLuMapper;
    @Autowired
    HuaMiYUMapper huaMiYUMapper;

    @Override
    public List<HuaType> midTypeData() {
        return shiWanGeTypeMapper.selectAllHuaType();
    }

    @Override
    public Map<String, Object> midTypeDataList(AppVO appVo) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("code", "200");
        AppMiYUVo appMiYUVo = new AppMiYUVo();
        /**
         * 1  十万个为什么
         * 2  脑筋急转弯
         * 3  猜灯谜
         * 4  ufo专题
         *
         */
        String type = appVo.getType();
        appMiYUVo.setType(type);
        /**
         * 查询类目总条数
         */

        int shiwangeWeishenmeCounts = huaShiWanGeMapper.midTypeDataListCount(type);
        //获取有多少页
        int counts = PageSizeUtils.pageSize(shiwangeWeishenmeCounts, 10);
        appMiYUVo.setPages(counts);
        //当前页
        String pageTemp = appVo.getPage();
        appMiYUVo.setPage(pageTemp);
        int page = PageSizeUtils.getPage(pageTemp, 10, 1);
        //查询数据
        switch (type) {
            case "1"://十万个为什么
                List<HuaShiWanGe> huaShiWanGeList = huaShiWanGeMapper.selectByPrimaryKeyByPageVo(type, page);
                appMiYUVo.setData(huaShiWanGeList);

                break;
            case "2"://脑筋急转弯
                List<HuaNaoJin> huaNaoJinList = huaNaoJinMapper.selectByPrimaryKeyByPageVo(type, page);
                appMiYUVo.setData(huaNaoJinList);
                break;
            case "3"://猜灯谜
                List<HuaMiYU> huaMiYUList =huaMiYUMapper.selectByPrimaryKeyByPageVo(type,page);
                appMiYUVo.setData(huaMiYUList);
                break;
            case "4"://ufo专题
                List<HuaQiWenYiLu> huaQiWenYiLuList = huaQiWenYiLuMapper.selectByPrimaryKeyByPageVo(type, page);
                appMiYUVo.setData(huaQiWenYiLuList);
                break;
        }
        maps.put("result", appMiYUVo);

        return maps;
    }

    @Override
    public Map<String, Object> getTypeDataDetail(AppVO appVo) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("code", "200");
        String type = appVo.getType();
        String typeuuid = appVo.getTypeuuid();
        switch (type) {
            case "1"://十万个为什么
                HuaShiWanGe huaShiWanGe = huaShiWanGeMapper.getTypeDataDetail(type, typeuuid);

                maps.put("result", huaShiWanGe);
                break;
            case "2"://脑筋急转弯
                HuaNaoJin huaNaoJin = huaNaoJinMapper.getTypeDataDetail(type, typeuuid);
                maps.put("result", huaNaoJin);
                break;
            case "3"://猜灯谜
                HuaMiYU huaMiYU =huaMiYUMapper.getTypeDataDetail(type,typeuuid);
                maps.put("result", huaMiYU);
                break;
            case "4"://ufo专题
                HuaQiWenYiLu huaQiWenYiLu = huaQiWenYiLuMapper.getTypeDataDetail(type, typeuuid);
                maps.put("result", huaQiWenYiLu);
                break;
        }

        huaShiWanGeMapper.updateByPrimaryKeyClickNum(type,typeuuid);
        return maps;
    }
}
