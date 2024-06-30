package com.louis.top_news.service.impl;

import com.louis.top_news.dao.NewsHeadlineDao;
import com.louis.top_news.dao.impl.NewsHeadlineDaoImpl;
import com.louis.top_news.pojo.NewsHeadline;
import com.louis.top_news.pojo.vo.HeadlineDetailVo;
import com.louis.top_news.pojo.vo.HeadlinePageVo;
import com.louis.top_news.pojo.vo.HeadlineQueryVo;
import com.louis.top_news.service.NewsHeadlineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {

    private NewsHeadlineDao newsHeadlineDao = new NewsHeadlineDaoImpl();

    /**
     * pageData :
     * {
     * hid :           新聞id
     * , title :       新聞標題
     * , type :        新聞所屬類別編號
     * , pageViews :   新聞瀏覽量
     * , pastHours :   發佈時間已過小時數
     * , publisher :   發佈用戶ID
     * }
     * , pageNum :      頁碼數
     * , pageSize :     頁大小
     * , totalPage :    總頁數
     * , totalSize :    總紀錄數
     */
    @Override
    public Map<String, Object> findPage (HeadlineQueryVo headLineQueryVo) {

        Map<String, Object> pageInfo = new HashMap<>();

        List<HeadlinePageVo> pageData = newsHeadlineDao.findPageList(headLineQueryVo);

        int totalSize = newsHeadlineDao.findPageCount(headLineQueryVo);
        int pageSize = headLineQueryVo.getPageSize();
        int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
        int pageNum = headLineQueryVo.getPageNum();

        pageInfo.put("pageData" , pageData);
        pageInfo.put("pageNum" , pageNum);
        pageInfo.put("pageSize" , pageSize);
        pageInfo.put("totalPage" , totalPage);
        pageInfo.put("totalSize" , totalSize);

        return pageInfo;
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail (Integer hid) {

        newsHeadlineDao.increasePageViews(hid);
        return newsHeadlineDao.findHeadlineDetail(hid);
    }

    @Override
    public int addNewsHeadline (NewsHeadline newsHeadline) {
        return newsHeadlineDao.addNewsHeadline(newsHeadline);
    }

    @Override
    public NewsHeadline findHeadlineByHid (Integer hid) {
        return newsHeadlineDao.findHeadlineByHid(hid);
    }

    @Override
    public int updateNewsHeadline (NewsHeadline newsHeadline) {
        return newsHeadlineDao.updateNewsHeadline(newsHeadline);
    }

    @Override
    public int removeByHid (Integer hid) {
        return newsHeadlineDao.removeByHid(hid);
    }
}
