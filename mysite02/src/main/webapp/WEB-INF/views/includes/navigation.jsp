<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<c:set var="path" value="<%=request.getContextPath()%>"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="navigation">
    <ul>
        <li><a href="${path}">김종하</a></li>
        <li><a href="${path}/guestbook">방명록</a></li>
        <li><a href="${path}/board">게시판</a></li>
    </ul>
</div>