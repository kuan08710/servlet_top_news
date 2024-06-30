package com.louis.top_news.filters;

import com.louis.top_news.common.Result;
import com.louis.top_news.common.ResultCodeEnum;
import com.louis.top_news.util.JwtHelper;
import com.louis.top_news.util.WebUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/headLine/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter (
            ServletRequest servletRequest , ServletResponse servletResponse , FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String token = request.getHeader("token");
        boolean flag = false;
        // 有 token 且 沒過期
        if (null != token) {
            boolean expiration = JwtHelper.isExpiration(token);
            if (!expiration) {
                flag = true;
            }
        }
        if (flag) {
            filterChain.doFilter(servletRequest , servletResponse);
        }
        else {
            WebUtil.writeJson((HttpServletResponse)servletResponse , Result.build(null , ResultCodeEnum.NOTLOGIN));
        }
    }
}
