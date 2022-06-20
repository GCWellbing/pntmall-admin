package com.pntmall.admin.controller;

import com.pntmall.admin.domain.MemGrade;
import com.pntmall.admin.domain.PopUp;
import com.pntmall.admin.domain.PopUpSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.MemGradeService;
import com.pntmall.admin.service.PopUpService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/content/popup")
public class PopUpController {
    public static final Logger logger = LoggerFactory.getLogger(PopUpController.class);

    @Autowired
    private PopUpService popUpService;

    @Autowired
    private MemGradeService memGradeService;

    @GetMapping("/list")
    public ModelAndView list(@ModelAttribute PopUpSearch popUpSearch) {
        Utils.savePath("popup");

        List<PopUp> list = popUpService.getList(popUpSearch);
        Integer count = popUpService.getCount(popUpSearch);
        AdminPaging paging = new AdminPaging(Utils.getUrl(), count, popUpSearch);

        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.addObject("count", count);
        mav.addObject("paging", paging);

        return mav;
    }

    @GetMapping("/edit")
    public ModelAndView edit(Integer popupid) {
        String mode = "create";
        PopUp popUp = new PopUp();
        if(popupid != null && popupid > 0) {
            mode = "modify";
            popUp = popUpService.getInfo(popupid);
        }

        List<MemGrade> memGrades = memGradeService.getList();

        ModelAndView mav = new ModelAndView();
        mav.addObject("mode", mode);
        mav.addObject("popup", popUp);
        mav.addObject("retrivePath", Utils.retrivePath("popup"));
        mav.addObject("memGrades",memGrades);

        return mav;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResultMessage create(@ModelAttribute PopUp popUp, HttpServletRequest request) {
        AdminSession session = AdminSession.getInstance();
        Param param = new Param(request.getParameterMap());

        try {
            popUp.setCuser(session.getAdminNo());
            popUpService.create(popUp, param);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResultMessage(false, "오류가 발생했습니다.");
        }

        return new ResultMessage(true, "등록되었습니다.");
    }

    @PostMapping("/modify")
    @ResponseBody
    public ResultMessage modify(@ModelAttribute PopUp popUp, HttpServletRequest request) {
        AdminSession session = AdminSession.getInstance();
        Param param = new Param(request.getParameterMap());

        try {
            popUp.setCuser(session.getAdminNo());
            popUpService.modify(popUp, param);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResultMessage(false, "오류가 발생했습니다.");
        }

        return new ResultMessage(true, "등록되었습니다.");
    }
}