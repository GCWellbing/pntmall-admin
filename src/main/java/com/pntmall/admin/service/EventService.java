package com.pntmall.admin.service;

import com.pntmall.admin.domain.Event;
import com.pntmall.admin.domain.EventItem;
import com.pntmall.admin.domain.EventLog;
import com.pntmall.admin.domain.EventSearch;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    public static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Event> getList(EventSearch eventSearch) {
        return sst.selectList("Event.list", eventSearch);
    }

    public Integer getCount(EventSearch eventSearch) {
        return sst.selectOne("Event.count", eventSearch);
    }

    public List<EventItem> getItemList(Integer eventId) { return sst.selectList("Event.itemList", eventId); }

    public List<Integer> getRankScopeList(Integer eventId) {
        List<EventItem> eventItemList = sst.selectList("Event.itemList", eventId);
        List<Integer> scopeList = new ArrayList<Integer>();
        Integer totalQty = 0;

        for (int i = 0; i < eventItemList.size(); i++) {
            totalQty += eventItemList.get(i).getQty();
            scopeList.add(totalQty);
        }

        return scopeList;
    }

    @Transactional
    public void decreaseItemQty(EventItem eventItem) {
        eventItem.setQty(eventItem.getQty() - 1);
        sst.update("Event.updateItemQty", eventItem);
    }

    @Transactional
    public void createLog(Integer eventId, Integer memNo, Integer itemId) {
        EventLog eventLog = new EventLog();
        eventLog.setEventId(eventId);
        eventLog.setMemNo(memNo);
        eventLog.setItemId(itemId);
        sst.insert("Event.insertLog", eventLog);
    }

    @Transactional
    public void eventCntReset() {
        sst.update("Event.reset");
    }
}
