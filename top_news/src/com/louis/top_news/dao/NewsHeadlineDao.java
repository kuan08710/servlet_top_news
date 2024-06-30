package com.louis.top_news.dao;

import com.louis.top_news.pojo.NewsHeadline;
import com.louis.top_news.pojo.vo.HeadlineDetailVo;
import com.louis.top_news.pojo.vo.HeadlinePageVo;
import com.louis.top_news.pojo.vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadlineDao {
    List<HeadlinePageVo> findPageList (HeadlineQueryVo headLineQueryVo);

    int findPageCount (HeadlineQueryVo headLineQueryVo);

    int increasePageViews (Integer hid);

    HeadlineDetailVo findHeadlineDetail (Integer hid);

    int addNewsHeadline (NewsHeadline newsHeadline);

    NewsHeadline findHeadlineByHid (Integer hid);

    int updateNewsHeadline (NewsHeadline newsHeadline);

    int removeByHid (Integer hid);
}
