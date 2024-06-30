package com.louis.top_news.dao.impl;

import com.louis.top_news.dao.BaseDao;
import com.louis.top_news.dao.NewsHeadlineDao;
import com.louis.top_news.pojo.NewsHeadline;
import com.louis.top_news.pojo.vo.HeadlineDetailVo;
import com.louis.top_news.pojo.vo.HeadlinePageVo;
import com.louis.top_news.pojo.vo.HeadlineQueryVo;

import java.util.LinkedList;
import java.util.List;

public class NewsHeadlineDaoImpl extends BaseDao implements NewsHeadlineDao {

    @Override
    public List<HeadlinePageVo> findPageList (HeadlineQueryVo headLineQueryVo) {
        List<Object> args = new LinkedList<>();
        String sql = """
                    SELECT
                        hid
                        , title
                        , type
                        , page_views pageViews
                        , TIMESTAMPDIFF(HOUR,create_time,NOW()) pastHours
                        , publisher
                    FROM
                        news_headline
                    WHERE
                        is_deleted = 0
                """;
        StringBuilder sqlBuffer = new StringBuilder(sql);

        String keyWords = headLineQueryVo.getKeyWords();
        if (null != keyWords && keyWords.length() > 0) {
            sqlBuffer.append(" AND title like ? ");
            args.add("%" + keyWords + "%");
        }

        Integer type = headLineQueryVo.getType();
        if (null != type && type != 0) {
            sqlBuffer.append(" AND type = ? ");
            args.add(type);
        }

        sqlBuffer.append(" ORDER BY pastHours ASC , page_views DESC ");

        sqlBuffer.append(" LIMIT ? , ? ");
        args.add((headLineQueryVo.getPageNum() - 1) * headLineQueryVo.getPageSize());
        args.add(headLineQueryVo.getPageSize());

        Object[] argsArr = args.toArray(); // 參數轉成陣列
        List<HeadlinePageVo> pageData = baseQuery(HeadlinePageVo.class , sqlBuffer.toString() , argsArr);
        return pageData;
    }

    @Override
    public int findPageCount (HeadlineQueryVo headLineQueryVo) {
        List<Object> args = new LinkedList<>();
        String sql = """
                    SELECT
                        COUNT(1)
                    FROM
                        news_headline
                    WHERE
                        is_deleted = 0
                """;
        StringBuilder sqlBuffer = new StringBuilder(sql);
        String keyWords = headLineQueryVo.getKeyWords();
        if (null != keyWords && keyWords.length() > 0) {
            sqlBuffer.append(" AND title LIKE ? ");
            args.add("%" + keyWords + "%");
        }
        Integer type = headLineQueryVo.getType();
        if (null != type && type != 0) {
            sqlBuffer.append(" AND type = ? ");
            args.add(type);
        }

        Object[] argsArr = args.toArray(); // 參數轉成陣列
        Long totalSize = baseQueryObject(Long.class , sqlBuffer.toString() , argsArr);

        return totalSize.intValue();
    }

    @Override
    public int increasePageViews (Integer hid) {
        String sql = """
                    UPDATE news_headline SET
                        page_views = page_views + 1
                    WHERE hid = ?
                """;
        return baseUpdate(sql , hid);
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail (Integer hid) {
        String sql = """
                    SELECT
                        hid
                        , title
                        , article
                        , type
                        , tname typeName
                        , page_views pageViews
                        , TIMESTAMPDIFF(HOUR, create_time ,NOW()) pastHours
                        , publisher
                        , nick_name author
                    FROM news_headline h
                        LEFT JOIN news_type t ON h.type = t.tid
                        LEFT JOIN news_user u ON h.publisher = u.uid
                    WHERE hid = ?
                """;

        List<HeadlineDetailVo> list = baseQuery(HeadlineDetailVo.class , sql , hid);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int addNewsHeadline (NewsHeadline newsHeadline) {
        String sql = """
                    INSERT INTO
                        news_headline
                    VALUES (
                        DEFAULT
                        , ?
                        , ?
                        , ?
                        , ?
                        , 0
                        , NOW()
                        , NOW()
                        , 0
                    )
                """;
        return baseUpdate(sql , newsHeadline.getTitle() , newsHeadline.getArticle() , newsHeadline.getType() ,
                          newsHeadline.getPublisher());
    }

    @Override
    public NewsHeadline findHeadlineByHid (Integer hid) {
        String sql = """
                    SELECT
                        hid
                        , title
                        , article
                        , type
                        , publisher
                        , page_views pageViews
                    FROM
                        news_headline
                    WHERE hid = ?
                """;
        List<NewsHeadline> newsHeadlineList = baseQuery(NewsHeadline.class , sql , hid);
        if (null != newsHeadlineList && newsHeadlineList.size() > 0) {
            return newsHeadlineList.get(0);
        }
        return null;
    }
}
