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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Admin;
import com.pntmall.admin.domain.AdminSearch;
import com.pntmall.admin.domain.Menu;
import com.pntmall.admin.domain.Team;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.AdminService;
import com.pntmall.admin.service.MenuService;
import com.pntmall.admin.service.TeamService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.html.Attr;
import com.pntmall.common.html.Checkbox;
import com.pntmall.common.html.Li;
import com.pntmall.common.html.Ul;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.SecurityUtils;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/admin/admin")
public class AdminController {
	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
	private AdminService adminService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MenuService menuService;
	
	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute AdminSearch adminSearch) {
		Utils.savePath();
		
		List<Admin> list = adminService.getList(adminSearch);
		Integer count = adminService.getCount(adminSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, adminSearch);
		List<Team> teamList = teamService.getList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("teamList", teamList);
		
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(Integer adminNo) {
		String mode = "create";
		Admin admin = new Admin();
		
		if(adminNo != null && adminNo > 0) {
			mode = "modify";
			admin = adminService.getInfo(adminNo);
		} else {
			adminNo = 0;
		}
		
		List<Menu> treeList = menuService.getTreeList(adminNo);
		
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
            checkbox.addAttr(new Attr("id", "menuNo" + menu.getMenuNo()));
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
        
		List<Team> teamList = teamService.getList();

		ModelAndView mav = new ModelAndView();
        mav.addObject("menuUl", menuUl);
		mav.addObject("mode", mode);
		mav.addObject("admin", admin);
		mav.addObject("teamList", teamList);
		
		return mav;
	}	

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Admin admin, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			admin.setPasswd(securityUtils.encodeSHA512(admin.getPasswd()));
			admin.setCuser(sess.getAdminNo());
			adminService.create(admin, param.getValues("menuNo"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Admin admin, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			if(!"".contentEquals(admin.getPasswd())) {
				admin.setPasswd(securityUtils.encodeSHA512(admin.getPasswd()));
			}
			admin.setCuser(sess.getAdminNo());
			adminService.modify(admin, param.getValues("menuNo"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
	@GetMapping("/exists")
	@ResponseBody
	public ResultMessage exists(@RequestParam String adminId) {
		Param param = new Param();
		param.set("isExists", adminService.isExists(adminId));
		return new ResultMessage(true, "", param);
	}

	@GetMapping("/teamAuth")
	@ResponseBody
	public ResultMessage teamAuth(Integer teamNo) {
		List<Menu> list = teamService.getTreeList(teamNo);
		Team team = teamService.getInfo(teamNo);
		Param param = new Param();
		param.set("list", list);
		param.set("updateAuth", team.getUpdateAuth());
		
		return new ResultMessage(true, "", param);
	}
}
