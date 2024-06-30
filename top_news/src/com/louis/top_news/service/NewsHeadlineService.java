package com.louis.top_news.service;

import com.louis.top_news.pojo.NewsHeadline;
import com.louis.top_news.pojo.vo.HeadlineDetailVo;
import com.louis.top_news.pojo.vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    Map<String, Object> findPage (HeadlineQueryVo headLineQueryVo);

    HeadlineDetailVo findHeadlineDetail (Integer hid);

    int addNewsHeadline (NewsHeadline newsHeadline);
}
