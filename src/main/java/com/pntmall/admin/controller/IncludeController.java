package com.pntmall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Menu;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.MenuService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.html.Li;
import com.pntmall.common.html.Ul;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/include")
public class IncludeController {
	public static final Logger logger = LoggerFactory.getLogger(IncludeController.class);
	
	@Autowired
	MenuService menuService;
	
	@GetMapping("/head")
	public ModelAndView head() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentTime", System.currentTimeMillis());
		
		return mav;
	}
	
	@GetMapping("/header")
	public ModelAndView header(HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance(request);
		
		String obj =(String) request.getAttribute("menuNo");
		int menuNo = (obj == null) ? 0 : Integer.parseInt(obj);
		
		Menu rootInfo = menuService.getRootInfo(menuNo, sess.getAdminNo());
		List<Menu> topList = menuService.getTopList(sess.getAdminNo());
		
		Ul leftUl = null;
		if(menuNo > 0) {
			List<Menu> leftList = menuService.getSubList(rootInfo.getMenuNo(), sess.getAdminNo());
			int prevLevel = -1;

			leftUl = Ul.create().addClass("nav");
			Ul workUl = leftUl;
			Ul prevWorkUl = null;
			Li parentLi = null;
			Li prevLi = null;
			boolean isFirst = true;

			for(Menu menu : leftList) {
				int currLevel = menu.getLevel();
				if (isFirst) prevLevel = currLevel;

				Li li = Li.create();
				if(Utils.isValued(menu.getUrl())) {
					li.setText("<p><a href='" + menu.getUrl() + "'>" + menu.getName() + "</a></p>");
				}
				else {
					li.setText("<p><a href='#'>" + menu.getName() + "</a></p>");
				}

				//하위메뉴 진입
				if (prevLevel < currLevel) {
					Ul ul = Ul.create();
					ul.add(li);

					parentLi = prevLi;
					parentLi.add(ul);

					prevWorkUl = workUl;
					workUl = ul;
				}
				//하위메뉴 끝
				else if (prevLevel > currLevel) {
					workUl = prevWorkUl;
					workUl.add(li);
				}
				//동일레벨 메뉴
				else {
					workUl.add(li);
				}

				if (menu.getMenuNo() == menuNo) {
					li.addClass("on");
					if (menu.getLevel() > 0) {
						parentLi.addClass("on");
					}
				}

				prevLi = li;
				prevLevel = currLevel;
				isFirst = false;
			}
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("menuNo", menuNo);
		mav.addObject("topList", topList);
		mav.addObject("rootInfo", rootInfo);
		mav.addObject("leftUl", leftUl);
		mav.addObject("sess", sess);
		mav.addObject("profile", Utils.getActiveProfile().toUpperCase());
		
		return mav;
	}

	@GetMapping("/location")
	public ModelAndView location(HttpServletRequest request) {
		int menuNo = Integer.parseInt((String) request.getAttribute("menuNo"));

		String[] paths = menuService.getPath(menuNo, AdminSession.getInstance(request).getAdminNo());

		Ul locUl = Ul.create();
		int idx = 0;
		for(String path : paths) {
			if(idx == paths.length - 1) {
				locUl.add(Li.create(path).addClass("on"));
			} else {
				locUl.add(Li.create(path));
			}

			idx++;
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("paths", paths);
		mav.addObject("name", paths[paths.length - 1]);
		mav.addObject("locUl", locUl);

		return mav;
	}

	@GetMapping("/url")
    @ResponseBody
    public ResultMessage url(@RequestParam("menuNo") int menuNo) {
    	AdminSession sess = AdminSession.getInstance();
    	if(sess.isLogin()) {
    		String url = menuService.getUrl(menuNo, sess.getAdminNo());
    		Param param = new Param("url", url);
    		return new ResultMessage(true, "", param);
    	} else {
    		return new ResultMessage(false, "");
    	}
    }
}
