<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page import="mysite.vo.UserVo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    UserVo authUser = (UserVo)session.getAttribute("authUser");
%>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>

<div id="header">
    <h1>MySite</h1>
    <ul>
        <%
            if (authUser == null) {
        %>
        <li><a href="${path}/user?a=loginform">로그인</a>
        <li>
        <li><a href="${path}/user?a=joinform">회원가입</a>
        <li>
                <%
        } else {
      %>
        <li><a href="${path}/user?a=updateform">회원정보수정</a>
        <li>
        <li><a href="${path}/user?a=logout">로그아웃</a>
        <li>
        <li><%=authUser.getName() %>님 안녕하세요 ^^;</li>
        <%
            }
        %>
    </ul>
</div>