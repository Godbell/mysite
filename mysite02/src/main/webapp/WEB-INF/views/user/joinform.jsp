<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>

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
            <form id="join-form" name="joinForm" method="post" action="${path}/user?a=join">
                <label class="block-label" for="name">이름</label>
                <input id="name" name="name" type="text" value="">

                <label class="block-label" for="email">이메일</label>
                <input id="email" name="email" type="text" value="">
                <input type="button" value="id 중복체크">

                <label class="block-label">패스워드</label>
                <input name="password" type="password" value="">

                <fieldset>
                    <legend>성별</legend>
                    <label>여</label> <input type="radio" name="gender" value="female" checked="checked">
                    <label>남</label> <input type="radio" name="gender" value="male">
                </fieldset>

                <fieldset>
                    <legend>약관동의</legend>
                    <input id="agree-prov" type="checkbox" name="agreeProv" value="y">
                    <label>서비스 약관에 동의합니다.</label>
                </fieldset>

                <input type="submit" value="가입하기">

            </form>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp"/>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>