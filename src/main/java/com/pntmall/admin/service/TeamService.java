package com.pntmall.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Menu;
import com.pntmall.admin.domain.Team;
import com.pntmall.admin.domain.TeamMenuAuth;

@Service
public class TeamService {
    public static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Team> getList() {
    	return sst.selectList("Team.list");
    }
    
    public Team getInfo(int teamNo) {
    	return sst.selectOne("Team.info", teamNo);
    }

    public List<Menu> getTreeList(int teamNo) {
        List<Menu> list = new ArrayList<>();
        List<Menu> sourceList = sst.selectList("Team.treeList", teamNo);

        fillChildList(0, 0, sourceList, list);

        return list;
    }
    
    private void fillChildList(int menuNo, int level, List<Menu> sourceList, List<Menu> targetList) {
        List<Menu> childList = getTopChildList(menuNo, sourceList);

        if (childList.size() > 0) {
            for (Menu menu : childList) {
                menu.setLevel(level);
                targetList.add(menu);
                fillChildList(menu.getMenuNo(), level + 1, sourceList, targetList);
            }
        }
    }

    private List<Menu> getTopChildList(int menuNo, List<Menu> sourceList) {
        List<Menu> list = new ArrayList<>();

        boolean flag = false;
        for (Menu menu : sourceList) {
            if (menu.getPmenuNo() == menuNo) {
                list.add(menu);
                flag = true;
            }

            if (flag && menu.getPmenuNo() != menuNo) break;
        }

        return list;
    }

    @Transactional
    public void create(Team team, String[] menuNos) {
    	sst.insert("Team.insert", team);
    	
    	for(String menuNo :  menuNos) {
    		TeamMenuAuth teamMenuAuth = new TeamMenuAuth();
    		teamMenuAuth.setTeamNo(team.getTeamNo());
    		teamMenuAuth.setMenuNo(Integer.parseInt(menuNo));
    		
    		sst.insert("Team.insertMenuAuth", teamMenuAuth);
    	}
    }

    @Transactional
    public void modify(Team team, String[] menuNos) {
    	sst.update("Team.update", team);
    	sst.delete("Team.deleteMenuAuth", team.getTeamNo());
    	
    	for(String menuNo :  menuNos) {
    		TeamMenuAuth teamMenuAuth = new TeamMenuAuth();
    		teamMenuAuth.setTeamNo(team.getTeamNo());
    		teamMenuAuth.setMenuNo(Integer.parseInt(menuNo));
    		
    		sst.insert("Team.insertMenuAuth", teamMenuAuth);
    	}
    }
}
