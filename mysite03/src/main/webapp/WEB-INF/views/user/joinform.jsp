<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                       action="${path}/user/join"
                       onsubmit="onSubmit()">
                <label class="block-label" for="name"><spring:message key="user.join.label.name"/></label>
                <form:input path="name" id="name"/>
                <br>
                <form:errors path="name"/>

                <label class="block-label" for="email"><spring:message key="user.join.label.email"/></label>
                <form:input path="email" id="email"/>
                <input type="button" value="<spring:message key="user.join.label.email.check"/>" onclick="">
                <br>
                <form:errors path="email"/>

                <label class="block-label"><spring:message key="user.join.label.password"/></label>
                <form:password path="password" id="password"/>
                <br>
                <form:errors path="password"/>

                <fieldset>
                    <legend><spring:message key="user.join.label.gender"/></legend>
                    <label><spring:message key="user.join.label.gender.female"/></label>
                    <form:radiobutton path="gender" value="female" checked="true"/>
                    <label><spring:message key="user.join.label.gender.male"/></label>
                    <form:radiobutton path="gender" value="male"/>
                </fieldset>

                <fieldset>
                    <legend><spring:message key="user.join.label.terms"/></legend>
                    <input id="agree-prov" type="checkbox" name="agreeProv">
                    <label><spring:message key="user.join.label.terms.message"/></label>
                </fieldset>

                <input type="submit" value="<spring:message key="user.join.button.signup" />">

            </form:form>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp"/>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
<script>
    const formStatus = {
        isDupChecked: false,
    }

    function checkEmailDuplication() {
        const email = document.getElementById('email');
        fetch(`${path}/user/email/availability?email=\${email.value}}`)
    }

    function onSubmit() {
        const termCheckBox = document.getElementById('agree-prov');
        return termCheckBox.checked && formStatus.isDupChecked;
    }
</script>
</html>