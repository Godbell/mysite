<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<c:set var="result" value='<%=request.getAttribute("result") %>'/>
<c:if test='<%=request.getAttribute("email") != null %>'>
    <jsp:useBean id="email" type="java.lang.String" scope="request"/>
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>mysite</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link href="${path}/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>
    <div id="content">
        <div id="user">
            <form id="login-form" name="loginform" method="post" action="${path}/user">
                <input type="hidden" name="a" value="login">
                <label class="block-label" for="email">이메일</label>
                <input id="email" name="email" type="text"
                       value="${email}">
                <label class="block-label">패스워드</label>
                <input name="password" type="password" value="">
                <c:if test="${'fail'.equals(result)}">
                    <p>
                        로그인이 실패 했습니다.
                    </p>
                </c:if>
                <input type="submit" value="로그인">
            </form>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp"/>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>