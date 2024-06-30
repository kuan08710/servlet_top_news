package com.louis.top_news.service.impl;

import com.louis.top_news.dao.NewsUserDao;
import com.louis.top_news.dao.impl.NewsUserDaoImpl;
import com.louis.top_news.pojo.NewsUser;
import com.louis.top_news.service.NewsUserService;
import com.louis.top_news.util.MD5Util;

public class NewsUserServiceImpl implements NewsUserService {

    private NewsUserDao newsUserDao = new NewsUserDaoImpl();

    @Override
    public NewsUser findByUserName (String username) {
        return newsUserDao.findByUserName(username);
    }

    @Override
    public NewsUser findByUid (Integer uid) {
        return newsUserDao.findByUid(uid);
    }

    @Override
    public Integer registUser (NewsUser newsUser) {
        newsUser.setUserPwd(MD5Util.encrypt(newsUser.getUserPwd()));
        return newsUserDao.insertUser(newsUser);
    }
}
