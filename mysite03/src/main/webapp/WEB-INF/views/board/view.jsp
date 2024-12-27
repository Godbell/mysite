<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<% pageContext.setAttribute("newline", "\n"); %>
<jsp:useBean id="post" scope="request" type="mysite.vo.PostVo"/>

<c:choose>
    <c:when test='<%=session.getAttribute("authUser") != null %>'>
        <jsp:useBean id="authUser" scope="session" type="mysite.vo.UserVo"/>
    </c:when>
</c:choose>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>${post.title}</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link href="${path}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>
    <div id="content">
        <div id="board" class="board-form">
            <table class="tbl-ex">
                <tr>
                    <th colspan="2">글보기</th>
                </tr>
                <tr>
                    <td class="label">제목</td>
                    <td>${post.title}</td>
                </tr>
                <tr>
                    <td class="label">내용</td>
                    <td>
                        ${fn:replace(post.contents, newline, '<br>')}
                    </td>
                </tr>
            </table>
            <div class="bottom">
                <a href="${path}/board">글목록</a>
                <c:if test="${!(empty authUser)}">
                    <a href="${path}/board/add?parent_post_id=${post.id}">답글 달기</a>
                    <c:if test="${authUser.id == post.userId}">
                        <a href="${path}/board/update/${post.id}">글수정</a>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp">
        <c:param name="menu" value="board"/>
    </c:import>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>