package com.louis.top_news.service.impl;

import com.louis.top_news.dao.NewsTypeDao;
import com.louis.top_news.dao.impl.NewsTypeDaoImpl;
import com.louis.top_news.pojo.NewsType;
import com.louis.top_news.service.NewsTypeService;

import java.util.List;

public class NewsTypeServiceImpl implements NewsTypeService {

    private NewsTypeDao newsTypeDao = new NewsTypeDaoImpl();
    @Override
    public List<NewsType> findAll() {
        return newsTypeDao.findAll();
    }
}
