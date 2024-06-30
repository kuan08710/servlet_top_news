package com.louis.top_news.controller;

import com.louis.top_news.common.Result;
import com.louis.top_news.common.ResultCodeEnum;
import com.louis.top_news.pojo.NewsUser;
import com.louis.top_news.service.NewsUserService;
import com.louis.top_news.service.impl.NewsUserServiceImpl;
import com.louis.top_news.util.JwtHelper;
import com.louis.top_news.util.MD5Util;
import com.louis.top_news.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class NewsUserController extends BaseController {
    private NewsUserService newsUserService = new NewsUserServiceImpl();

    /**
     * 登入
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login (HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException {

        NewsUser newsUser = WebUtil.readJson(req , NewsUser.class);
        Result result = null;

        NewsUser loginNewsUser = newsUserService.findByUserName(newsUser.getUsername());

        if (null != loginNewsUser) {
            if (loginNewsUser.getUserPwd()
                             .equals(MD5Util.encrypt(newsUser.getUserPwd()))) {

                Map<String, Object> data = new HashMap<>();
                String token = JwtHelper.createToken(loginNewsUser.getUid()
                                                                  .longValue());
                data.put("token" , token);
                result = Result.ok(data);
            }
            else {
                result = Result.build(null , ResultCodeEnum.PASSWORD_ERROR);
            }
        }
        else {
            result = Result.build(null , ResultCodeEnum.USERNAME_ERROR);
        }

        WebUtil.writeJson(resp , result);
    }


    protected void getUserInfo (HttpServletRequest req , HttpServletResponse resp)
            throws ServletException, IOException {

        String token = req.getHeader("token");  // 從 header 中拿
        Result result = Result.build(null , ResultCodeEnum.NOTLOGIN);

        if (null != token) {
            if (!JwtHelper.isExpiration(token)) {
                Integer uid = JwtHelper.getUserId(token)
                                       .intValue();
                NewsUser newsUser = newsUserService.findByUid(uid);
                newsUser.setUserPwd("");

                Map<String, Object> data = new HashMap<>();
                data.put("loginUser" , newsUser);
                result = Result.ok(data);
            }
        }
        WebUtil.writeJson(resp , result);
    }

    protected void checkUserName (HttpServletRequest req , HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        NewsUser newsUser = newsUserService.findByUserName(username);
        Result result = null;
        if (null == newsUser) {
            result = Result.ok(null);
        }
        else {
            result = Result.build(null , ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp , result);
    }

    protected void regist (HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException {
        NewsUser newsUser = WebUtil.readJson(req , NewsUser.class);
        NewsUser usedUser = newsUserService.findByUserName(newsUser.getUsername());

        Result result = null;

        if (null == usedUser) {
            newsUserService.registUser(newsUser);
            result = Result.ok(null);
        }
        else {
            result = Result.build(null , ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp , result);
    }
}
