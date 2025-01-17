<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<jsp:useBean id="siteVo" scope="request" type="mysite.vo.SiteVo"/>
<% pageContext.setAttribute("newline", "\n"); %>

<!DOCTYPE html>
<html lang="ko">
<c:import url="/WEB-INF/views/includes/head.jsp">
    <c:param name="stylesheetPath" value="main"/>
</c:import>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>

    <div id="wrapper">
        <div id="content">
            <div id="site-introduction">
                <img id="profile" src="${path}/${siteVo.profile}"
                     style="width: 100%;">
                <h2 style="width: 100%">${siteVo.welcome}</h2>
                <p>
                    ${fn:replace(siteVo.description, newline, "<br>")}
                    <br><br>
                    <a href="${path}/guestbook">방명록</a>에 글 남기기<br>
                </p>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp"/>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>