<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="navigation">
    <ul>
        <li><a href="${path}/"><spring:message key="navigation.li.main"/></a></li>
        <li><a href="${path}/guestbook"><spring:message key="navigation.li.guestbook"/></a></li>
        <li><a href="${path}/board"><spring:message key="navigation.li.board"/></a></li>
    </ul>
</div>