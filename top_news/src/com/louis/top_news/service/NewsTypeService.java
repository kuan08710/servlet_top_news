package com.louis.top_news.service;

import com.louis.top_news.pojo.NewsType;

import java.util.List;

public interface NewsTypeService {

    // 查詢所有頭條類型
    List<NewsType> findAll ();
}
