package com.pntmall.admin.controller;

import com.pntmall.admin.domain.Event;
import com.pntmall.admin.domain.EventSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.service.EventService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/promotion/event")
public class EventController {
    public static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public ModelAndView list(@ModelAttribute EventSearch eventSearch) {
        Utils.savePath();

        List<Event> list = eventService.getList(eventSearch);
        Integer count = eventService.getCount(eventSearch);
        AdminPaging paging = new AdminPaging(Utils.getUrl(), count, eventSearch);

        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.addObject("count", count);
        mav.addObject("paging", paging);

        return mav;
    }

    @PutMapping("/reset")
    @ResponseBody
    public ResultMessage reset() {
        try {
            eventService.eventCntReset();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResultMessage(false, "오류가 발생했습니다.");
        }

        return new ResultMessage(true, "응모횟수가 1로 초기화되었습니다.");
    }
}
