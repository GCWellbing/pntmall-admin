package com.pntmall.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Menu;
import com.pntmall.common.utils.Utils;

/**
 * 메뉴 관리를 위한 서비스
 */
@Service
public class MenuService {
    public static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private SqlSessionTemplate sst;

    @Autowired
    private CacheService cacheService;

    /**
     * 현재 메뉴 Location
     *
     * @param menuNo 메뉴 순번
     * @param adminNo 관리자 번호
     * @return 대에서 소메뉴명의 순서로 스트링 배열을 반환한다.
     */
    public String[] getPath(int menuNo, int adminNo) {
        int _menuNo = menuNo;

        List<String> pathList = new ArrayList<>();
        while(_menuNo > 0) {
			pathList.add(getInfo(_menuNo, adminNo).getName());
            _menuNo = getPmenuNo(_menuNo, adminNo);
        }

        String[] paths = new String[pathList.size()];
        int idx = pathList.size();
        for (String path : pathList) {
            paths[--idx] = path;
        }

        return paths;
    }

    /**
     * 대메뉴만의 목록을 조회.
     *
     * @param adminNo 관리자 번호
     * @return 대메뉴 목록
     */
    public List<Menu> getTopList(int adminNo) {
        List<Menu> list = new ArrayList<>();
        for (Menu menu : getAuthList(adminNo)) {
            if (menu.getPmenuNo() == 0) list.add(menu);
            else break;
        }

        return list;
    }

    /**
     * 현 menuNo의 최상위 대메뉴 정보를 반환한다.
     * @param menuNo 메뉴 순번
     * @param adminId 관리자ID
     * @return 현 menuNo의 최상위 대메뉴 정보
     */
    public Menu getRootInfo(int menuNo, int adminNo) {
        int _menuNo = menuNo;
        Menu rootMenu = null;

        while(_menuNo > 0) {
            if(_menuNo > 0) rootMenu = getInfo(_menuNo, adminNo);
            _menuNo = getPmenuNo(_menuNo, adminNo);
        }

        return rootMenu;
    }

    /**
     * 해당 관리자의 권한이 존재하는 자식메뉴 리스트를 조회.
     *
     * @param menuNo 메뉴 순번
     * @param adminNo 관리자 번호
     * @return 해당 관리자의 권한이 존재하는 자식메뉴 리스트
     */
    public List<Menu> getSubList(int menuNo, int adminNo) {
        List<Menu> list = new ArrayList<>();

        fillChildList(menuNo, 0, getAuthList(adminNo), list);

        return list;
    }

    /**
     * 해당 관리자의 전체 메뉴트리 구조를 조회. 해당 관리자의 권한과 관계없이 모든 리스트를 조회하지만, 권한유무는 표기한다.
     *
     * @param adminNo 관리자 번호
     * @return 해당 관리자의 전체 메뉴트리 구조
     */
    public List<Menu> getTreeList(int adminNo) {
        List<Menu> list = new ArrayList<>();
        List<Menu> sourceList = sst.selectList("Menu.treeList", adminNo);

        fillChildList(0, 0, sourceList, list);

        return list;
    }

    /**
     * 해당 menuSeq의 url을 조회
     *
     * @param menuNo 메뉴순번
     * @param adminNo 관리자 번호
     * @return 해당 menuSeq의 url
     */
    public String getUrl(int menuNo, int adminNo) {
        List<Menu> list = this.getSubList(menuNo, adminNo);

        for(Menu menu : list) {
            if(Utils.isValued(menu.getUrl())) return menu.getUrl();
        }

        return null;
    }

    public List<Menu> getAuthList(int adminNo) {
//        return sst.selectList("Menu.authList", adminNo);
    	List<Menu> list = cacheService.getAuthList(adminNo);
//    	logger.debug("--------------- getAuthList : " + list);
    	return list;
    }

    private Menu getInfo(int menuNo, int adminNo) {
        for(Menu menu : getAuthList(adminNo)) {
            if (menu.getMenuNo() == menuNo) return menu;
        }

        return null;
    }

    private int getPmenuNo(int menuNo, int adminNo) {
        return getInfo(menuNo, adminNo).getPmenuNo();
    }

    private List<Menu> getTopChildList(int menuNo, List<Menu> sourceList) {
        List<Menu> list = new ArrayList<>();

        boolean flag = false;
        for(Menu menu : sourceList) {
            if (menu.getPmenuNo() == menuNo) {
                list.add(menu);
                flag = true;
            }

            if (flag && menu.getPmenuNo() != menuNo) break;
        }

        return list;
    }

    private void fillChildList(int menuNo, int level, List<Menu> sourceList, List<Menu> targetList) {
        List<Menu> childList = getTopChildList(menuNo, sourceList);

        if(childList.size() > 0) {
            for(Menu menu : childList) {
                menu.setLevel(level);
                targetList.add(menu);
                fillChildList(menu.getMenuNo(), level + 1, sourceList, targetList);
            }
        }
    }

    /**
     * 해당 URL이 등록된 menuNo를 조회. 정상적인 경우라면 단 하나여야 한다.
     *
     * @param url 어드민 URL
     * @return 해당 URL이 등록된 menu
     */
    public List<Menu> findMenuList(String url) {
//        return sst.selectList("Menu.findMenuList", url);
    	return cacheService.findMenuList(url);
    }
}
