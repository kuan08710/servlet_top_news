package com.louis.top_news.service;

import com.louis.top_news.pojo.NewsUser;

public interface NewsUserService {
    NewsUser findByUserName (String username);

    NewsUser findByUid (Integer uid);

    Integer registUser (NewsUser newsUser);
}
