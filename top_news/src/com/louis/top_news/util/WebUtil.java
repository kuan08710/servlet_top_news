package com.louis.top_news.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louis.top_news.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class WebUtil {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    // 從請求中獲取 JSON 並轉換為 Object
    public static <T> T readJson (HttpServletRequest request , Class<T> clazz) {
        T t = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            t = objectMapper.readValue(buffer.toString() , clazz);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    // 將 Result 轉成 JSON，並放入 Response
    public static void writeJson (HttpServletResponse response , Result result) {
        response.setContentType("application/􀁌son;charset=UTF-8");
        try {
            String json = objectMapper.writeValueAsString(result);
            response.getWriter()
                    .write(json);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}