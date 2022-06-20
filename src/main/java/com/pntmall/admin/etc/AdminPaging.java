package com.pntmall.admin.etc;

import com.pntmall.common.type.SearchDomain;
import com.pntmall.common.utils.Paging;
import com.pntmall.common.utils.Utils;

/**
 * 게시판 페이징 HTML Render 클래스
 */
public class AdminPaging extends Paging {

	private static String PREV_BTN = "<img src=\"/static/images/pgPrev.gif\">";
	private static String PREV_BTN2 = "<img src=\"/static/images/pgPrev2.gif\">";
	private static String PREV_BTN_DISABLED = "<img src=\"/static/images/pgPrev_disabled.gif\">";
	private static String PREV_BTN2_DISABLED = "<img src=\"/static/images/pgPrev2_disabled.gif\">";
	private static String NEXT_BTN = "<img src=\"/static/images/pgNext.gif\">";
	private static String NEXT_BTN2 = "<img src=\"/static/images/pgNext2.gif\">";
	private static String NEXT_BTN_DISABLED = "<img src=\"/static/images/pgNext_disabled.gif\">";
	private static String NEXT_BTN2_DISABLED = "<img src=\"/static/images/pgNext2_disabled.gif\">";
	
    /**
     * 페이징 생성자
     * @param totalRows 게시물 총카운트
     * @param pagingDomain 페이징 정보가 있는 {@link SearchDomain} 상속 도메인
     */
    public AdminPaging(int totalRows, SearchDomain pagingDomain) {
        super(Utils.getUrl(), totalRows, pagingDomain.getPage(), pagingDomain.getPageSize(), pagingDomain.getBlockSize());
    }

    /**
     * 페이징 생성자
     * @param url 페이징 링크 URL
     * @param totalRows 게시물 총카운트
     * @param pagingDomain 페이징 정보가 있는 {@link SearchDomain} 상속 도메인
     */
    public AdminPaging(String url, int totalRows, SearchDomain pagingDomain) {
        super(url, totalRows, pagingDomain.getPage(), pagingDomain.getPageSize(), pagingDomain.getBlockSize());
    }

    /**
     * 페이징 생성자
     * @param totalRows 게시물 총카운트
     * @param currentPage 현재 페이지
     * @param pageSize 페이지별 아티클 사이즈
     * @param blockSize 본문에 노출할 페이지들의 숫자
     */
    public AdminPaging(int totalRows, int currentPage, int pageSize, int blockSize) {
        super(Utils.getUrl(), totalRows, currentPage, pageSize, blockSize);
    }

    /**
     * 페이징 생성자
     * @param url 페이징 링크 URL
     * @param totalRows 게시물 총카운트
     * @param currentPage 현재 페이지
     * @param pageSize 페이지별 아티클 사이즈
     * @param blockSize 본문에 노출할 페이지들의 숫자
     */
    public AdminPaging(String url, int totalRows, int currentPage, int pageSize, int blockSize) {
        super(url, totalRows, currentPage, pageSize, blockSize);
    }

    /**
     * 페이징 HTML Render
     *
     * @return 페이징 HTML Tags
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();

        if (needPrevBlockAction) {
            buf.append("<a href=\"" + getReturnURL(startPage - blockSize) + "\">" + AdminPaging.PREV_BTN2 + "</a>\n");
        } else {
            buf.append(AdminPaging.PREV_BTN2_DISABLED + "\n");
        }
        if (needPrevPageAction) {
            buf.append("<a href=\"" + getReturnURL(currentPage - 1) + "\">" + AdminPaging.PREV_BTN + "</a>\n");
        } else {
            buf.append(AdminPaging.PREV_BTN_DISABLED + "\n");
        }

        for (int i = 0; i < blockSize; i++) {
            String sHtml = "";
            int n = i + startPage;

            if (n <= totalPages) {
                if (n == currentPage) {
                    sHtml = "<strong>" + n + "</strong>\n";
                } else {
                    sHtml = "<a href=\"" + getReturnURL(n) + "\">" + n + "</a>\n";
                }

                buf.append(sHtml);
            } else break;
        }

        if (needNextPageAction) {
            buf.append("<a href=\"" + getReturnURL(currentPage + 1) + "\">" + AdminPaging.NEXT_BTN + "</a>\n");
        } else {
            buf.append(AdminPaging.NEXT_BTN_DISABLED + "\n");
        }
        if (needNextBlockAction) {
            buf.append("<a href=\"" + getReturnURL(startPage + blockSize) + "\">" + AdminPaging.NEXT_BTN2 + "</a>\n");
        } else {
            buf.append(AdminPaging.NEXT_BTN2_DISABLED + "\n");
        }

        return buf.toString();
    }
}
