package com.louis.top_news.controller;

import com.louis.top_news.common.Result;
import com.louis.top_news.pojo.NewsHeadline;
import com.louis.top_news.service.NewsHeadlineService;
import com.louis.top_news.service.impl.NewsHeadlineServiceImpl;
import com.louis.top_news.util.JwtHelper;
import com.louis.top_news.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/headline/*")
public class NewsHeadlineController extends BaseController {
    private NewsHeadlineService newsHeadlineService = new NewsHeadlineServiceImpl();

    protected void publish (HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException {

        NewsHeadline newsHeadline = WebUtil.readJson(req , NewsHeadline.class);

        String token = req.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        newsHeadline.setPublisher(userId.intValue());

        newsHeadlineService.addNewsHeadline(newsHeadline);

        WebUtil.writeJson(resp , Result.ok(null));
    }
}
