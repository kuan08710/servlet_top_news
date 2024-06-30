package com.louis.top_news.dao;

import com.louis.top_news.pojo.NewsUser;

public interface NewsUserDao {
    NewsUser findByUserName (String username);

    NewsUser findByUid (Integer uid);

    Integer insertUser (NewsUser newsUser);
}
