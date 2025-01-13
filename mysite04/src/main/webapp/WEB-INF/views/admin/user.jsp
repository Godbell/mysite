<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/views/includes/head.jsp">
    <c:param name="stylesheetPath" value="admin/main"/>
</c:import>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/admin/include/header.jsp"/>
    <div id="wrapper">
        <div id="content">
            <div id="site-form">
                <h2>사용자 관리 페이지</h2>
            </div>
        </div>
        <c:import url="/WEB-INF/views/admin/include/navigation.jsp">
            <c:param name="menu" value="user"/>
        </c:import>
    </div>
</div>
</body>
</html>