package com.louis.top_news.controller;

import com.louis.top_news.common.Result;
import com.louis.top_news.pojo.NewsType;
import com.louis.top_news.pojo.vo.HeadlineDetailVo;
import com.louis.top_news.pojo.vo.HeadlineQueryVo;
import com.louis.top_news.service.NewsHeadlineService;
import com.louis.top_news.service.NewsTypeService;
import com.louis.top_news.service.impl.NewsHeadlineServiceImpl;
import com.louis.top_news.service.impl.NewsTypeServiceImpl;
import com.louis.top_news.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/portal/*")
public class PortalController extends BaseController {

    private NewsTypeService newsTypeService = new NewsTypeServiceImpl();
    private NewsHeadlineService newsHeadlineService = new NewsHeadlineServiceImpl();

    // 查詢所有頭條類型
    protected void findAllTypes (HttpServletRequest req , HttpServletResponse resp)
            throws ServletException, IOException {
        List<NewsType> newsTypeList = newsTypeService.findAll();

        WebUtil.writeJson(resp , Result.ok(newsTypeList));
    }

    protected void findNewsPage (HttpServletRequest req , HttpServletResponse resp)
            throws ServletException, IOException {
        HeadlineQueryVo headLineQueryVo = WebUtil.readJson(req , HeadlineQueryVo.class);

        Map<String, Object> pageInfo = newsHeadlineService.findPage(headLineQueryVo);
        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageInfo" , pageInfo);

        WebUtil.writeJson(resp , Result.ok(pageInfoMap));
    }

    protected void showHeadlineDetail (HttpServletRequest req , HttpServletResponse resp)
            throws ServletException, IOException {

        Integer hid = Integer.parseInt(req.getParameter("hid"));
        HeadlineDetailVo headlineDetailVo = newsHeadlineService.findHeadlineDetail(hid);

        Map < String , Object > data = new HashMap<>();
        data.put("headline" , headlineDetailVo);

        WebUtil.writeJson(resp ,Result.ok(data));
    }
}
