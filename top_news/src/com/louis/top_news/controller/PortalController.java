package com.louis.top_news.controller;

import com.louis.top_news.common.Result;
import com.louis.top_news.pojo.NewsType;
import com.louis.top_news.service.NewsTypeService;
import com.louis.top_news.service.impl.NewsTypeServiceImpl;
import com.louis.top_news.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/portal/*")
public class PortalController extends BaseController {

    private NewsTypeService newsTypeService = new NewsTypeServiceImpl();

    // 查詢所有頭條類型
    protected void findAllTypes (HttpServletRequest req , HttpServletResponse resp)
            throws ServletException, IOException {
        List<NewsType> newsTypeList = newsTypeService.findAll();

        WebUtil.writeJson(resp, Result.ok(newsTypeList));
    }
}
