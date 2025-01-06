<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<jsp:useBean id="metadata" scope="application" type="mysite.web.SiteMetadata"/>

<head>
    <title>${not empty param.title ? param.title : metadata.title}</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link href="${path}/assets/css/${param.stylesheetPath}.css" rel="stylesheet" type="text/css">
</head>