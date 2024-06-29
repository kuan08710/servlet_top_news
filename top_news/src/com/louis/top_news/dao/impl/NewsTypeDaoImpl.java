package com.louis.top_news.dao.impl;

import com.louis.top_news.dao.BaseDao;
import com.louis.top_news.dao.NewsTypeDao;
import com.louis.top_news.pojo.NewsType;

import java.util.List;

public class NewsTypeDaoImpl extends BaseDao implements NewsTypeDao {
    @Override
    public List<NewsType> findAll () {

        String sql = """
                select tid, tname from news_type;
                """;
        return baseQuery(NewsType.class, sql);
    }
}
