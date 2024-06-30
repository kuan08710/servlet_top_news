package com.louis.top_news.dao;

import com.louis.top_news.pojo.vo.HeadlinePageVo;
import com.louis.top_news.pojo.vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadlineDao {
    List<HeadlinePageVo> findPageList (HeadlineQueryVo headLineQueryVo);

    int findPageCount (HeadlineQueryVo headLineQueryVo);
}
