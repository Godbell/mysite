<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<jsp:useBean id="metadata" scope="request" type="mysite.vo.SiteVo"/>

<!DOCTYPE html>
<html lang="ko">
<c:import url="/WEB-INF/views/includes/head.jsp">
    <c:param name="stylesheetPath" value="admin/main"/>
</c:import>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/admin/include/header.jsp"/>
    <div id="wrapper">
        <div id="content">
            <div id="site-form">
                <form method="post" action="${path}/admin/main/update"
                      enctype="multipart/form-data">
                    <label class="block-label" for="title">사이트 타이틀</label>
                    <input id="title" name="title" type="text" value="${metadata.title}">

                    <label class="block-label" for="welcome">환영 메세지</label>
                    <input id="welcome" name="welcome" type="text" value="${metadata.welcome}">

                    <label class="block-label">프로필 이미지</label>
                    <img id="profile" src="${path}/${metadata.profile}">
                    <input type="file" name="file">

                    <label class="block-label">사이트 설명</label>
                    <textarea name="description">${metadata.description}</textarea>

                    <input type="submit" value="변경"/>
                </form>
            </div>
        </div>
        <c:import url="/WEB-INF/views/admin/include/navigation.jsp">
            <c:param name="menu" value="main"/>
        </c:import>
    </div>
</div>
</body>
</html>