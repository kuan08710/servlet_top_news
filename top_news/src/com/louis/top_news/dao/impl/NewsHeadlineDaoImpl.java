package com.louis.top_news.dao.impl;

import com.louis.top_news.dao.BaseDao;
import com.louis.top_news.dao.NewsHeadlineDao;
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
                        hid, title, type, page_views pageViews
                        , TIMESTAMPDIFF(HOUR,create_time,NOW()) pastHours
                        , publisher 
                    FROM news_headline 
                    WHERE is_deleted = 0 
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
                    SELECT COUNT(1)
                    FROM news_headline
                    WHERE is_deleted = 0 
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
}
