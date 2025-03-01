<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<c:import url="/WEB-INF/views/includes/head.jsp">
    <c:param name="stylesheetPath" value="board"/>
</c:import>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>
    <div id="content">
        <div id="board">
            <form class="board-form" method="post" action="${path}/board/add">
                <c:if test='${!(empty pageContext.request.getParameter("parent_post_id"))}'>
                    <c:set var="parentPostId" value='${pageContext.request.getParameter("parent_post_id")}'/>
                    <input type="hidden" name="parentPostId" value="${parentPostId}">
                </c:if>
                <table class="tbl-ex">
                    <tr>
                        <th colspan="2">글쓰기</th>
                    </tr>
                    <tr>
                        <td class="label">제목</td>
                        <td><input type="text" name="title" value=""></td>
                    </tr>
                    <tr>
                        <td class="label">내용</td>
                        <td>
                            <textarea id="content" name="contents"></textarea>
                        </td>
                    </tr>
                </table>
                <div class="bottom">
                    <a href="${path}/board">취소</a>
                    <input type="submit" value="등록">
                </div>
            </form>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp">
        <c:param name="menu" value="board"/>
    </c:import>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>