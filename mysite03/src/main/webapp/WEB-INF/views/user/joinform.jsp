<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<c:import url="/WEB-INF/views/includes/head.jsp">
    <c:param name="stylesheetPath" value="user"/>
</c:import>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>
    <div id="content">
        <div id="user">
            <%--@elvariable id="user" type="mysite.vo.UserVo"--%>
            <form:form modelAttribute="user"
                       id="join-form"
                       name="joinForm"
                       method="post"
                       action="${path}/user/join">
                <label class="block-label" for="name">이름</label>
                <form:input path="name" id="name"/>
                <br>
                <form:errors path="name"/>

                <label class="block-label" for="email">이메일</label>
                <form:input path="email" id="email"/>
                <input type="button" value="id 중복체크">
                <br>
                <form:errors path="email"/>

                <label class="block-label">패스워드</label>
                <form:password path="password" id="password"/>
                <br>
                <form:errors path="password"/>

                <fieldset>
                    <legend>성별</legend>
                    <label>여</label>
                    <form:radiobutton path="gender" value="female" checked="true"/>
                    <label>남</label>
                    <form:radiobutton path="gender" value="male"/>
                </fieldset>

                <fieldset>
                    <legend>약관동의</legend>
                    <input id="agree-prov" type="checkbox" name="agreeProv" value="y">
                    <label>서비스 약관에 동의합니다.</label>
                </fieldset>

                <input type="submit" value="가입하기">

            </form:form>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp"/>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>