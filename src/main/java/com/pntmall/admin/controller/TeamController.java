package com.pntmall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Menu;
import com.pntmall.admin.domain.Team;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.TeamService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.html.Checkbox;
import com.pntmall.common.html.Li;
import com.pntmall.common.html.Ul;
import com.pntmall.common.type.Param;

@Controller
@RequestMapping("/admin/team")
public class TeamController {
	public static final Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	TeamService teamService;
	
	@GetMapping("/list")
	public ModelAndView head() {
		List<Team> list = teamService.getList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(Integer teamNo) {
		String mode = "create";
		Team team = new Team();
		
		if(teamNo != null && teamNo > 0) {
			mode = "modify";
			team = teamService.getInfo(teamNo);
		} else {
			teamNo = 0;
		}
		
		List<Menu> treeList = teamService.getTreeList(teamNo);
		
        int prevLevel = -1;

        Ul menuUl = Ul.create().addClass("menuTree");
        Ul workUl = menuUl;
        Ul prevWorkUl = null;
        Li prevLi = null;
        boolean isFirst = true;

        for (Menu menu : treeList) {
            int currLevel = menu.getLevel();
            if (isFirst) prevLevel = currLevel;

            Li li = Li.create();
            Checkbox checkbox = Checkbox.create("menuNo", menu.getMenuNo().toString(), "<span>" + menu.getName() + "</span>");
            if(menu.getAuthYn().equals("Y")) checkbox.check();
            li.add(checkbox);

            if (prevLevel < currLevel) {
				Ul ul = Ul.create();
				ul.add(li);

				prevLi.add(ul);

				prevWorkUl = workUl;
				workUl = ul;
            } else if (prevLevel > currLevel) {
            	workUl = (currLevel == 0) ? menuUl : prevWorkUl;
                workUl.add(li);

            } else {
                workUl.add(li);
            }

            prevLi = li;
            prevLevel = currLevel;
            isFirst = false;
        }
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("menuUl", menuUl);
		mav.addObject("mode", mode);
		mav.addObject("team", team);
		
		return mav;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Team team, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			team.setCuser(sess.getAdminNo());
			teamService.create(team, param.getValues("menuNo"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Team team, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}
		
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			team.setCuser(sess.getAdminNo());
			teamService.modify(team, param.getValues("menuNo"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
}
