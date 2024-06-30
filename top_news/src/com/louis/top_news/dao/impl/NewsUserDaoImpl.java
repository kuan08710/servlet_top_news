package com.louis.top_news.dao.impl;

import com.louis.top_news.dao.BaseDao;
import com.louis.top_news.dao.NewsUserDao;
import com.louis.top_news.pojo.NewsUser;

import java.util.List;

public class NewsUserDaoImpl extends BaseDao implements NewsUserDao {
    @Override
    public NewsUser findByUserName (String username) {
        String sql = """
                SELECT 
                    uid, 
                    username, 
                    user_pwd userPwd, 
                    nick_name nickName
                FROM 
                    news_user
                WHERE
                    username = ?
                """;

        List<NewsUser> newsUserList = baseQuery(NewsUser.class , sql , username);

        return newsUserList != null && newsUserList.size() > 0 ? newsUserList.get(0) : null;
    }

    @Override
    public NewsUser findByUid (Integer uid) {
        String sql = """
                SELECT 
                    uid, 
                    username, 
                    user_pwd userPwd,
                    nick_name nickName 
                FROM 
                    news_user 
                WHERE 
                    uid = ?
                """;
        List<NewsUser> newsUserList = baseQuery(NewsUser.class , sql , uid);
        if (null != newsUserList && newsUserList.size() > 0) {
            return newsUserList.get(0);
        }
        return null;
    }

    @Override
    public Integer insertUser (NewsUser newsUser) {
        String sql = """
                INSERT INTO news_user
                VALUES (DEFAULT, ?, ?, ?);
                """;
        return baseUpdate(sql , newsUser.getUsername() , newsUser.getUserPwd() , newsUser.getNickName());
    }
}
