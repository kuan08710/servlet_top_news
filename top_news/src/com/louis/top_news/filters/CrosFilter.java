package com.louis.top_news.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CrosFilter implements Filter {
    @Override
    public void doFilter (
            ServletRequest servletRequest , ServletResponse servletResponse , FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        response.setHeader("Access-Control-Allow-Origin" , "*");
        response.setHeader("Access-Control-Allow-Methods" , "POST, GET, OPTIONS, DELETE, HEAD");
        response.setHeader("Access-Control-Max-Age" , "3600");
        response.setHeader("Access-Control-Allow-Headers" ,
                           "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");

        // 非預先檢查請求，直接放行
        // 預先檢查，則到此結束，不須放行
        if (!request.getMethod()
                    .equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(servletRequest , servletResponse);
        }
    }
}