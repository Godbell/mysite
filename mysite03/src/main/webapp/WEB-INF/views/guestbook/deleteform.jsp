<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<c:set var="id" value='<%=request.getAttribute("id")%>'/>

<!DOCTYPE html>
<html lang="ko">
<c:import url="/WEB-INF/views/includes/head.jsp">
    <c:param name="stylesheetPath" value="guestbook"/>
</c:import>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>
    <div id="content">
        <div id="guestbook" class="delete-form">
            <form method="post" action="${path}/guestbook/delete/${id}">
                <label>비밀번호</label>
                <input type="password" name="password">
                <input type="submit" value="확인">
            </form>
            <a href="">방명록 리스트</a>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp"/>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>