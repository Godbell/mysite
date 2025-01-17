<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="header">
    <h1>관리자페이지</h1>
    <ul>
        <li><a href="${pageContext.request.contextPath }">사이트 메인</a>
        <li>
        <li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a>
        <li>
    </ul>
</div>