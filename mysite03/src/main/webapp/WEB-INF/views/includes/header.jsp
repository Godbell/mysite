<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<c:if test='<%=session.getAttribute("authUser") != null %>'>
    <jsp:useBean id="authUser" scope="session" type="mysite.vo.UserVo"/>
</c:if>
<jsp:useBean id="metadata" scope="application" type="mysite.web.SiteMetadata"/>
<script>
    window.addEventListener('load', function () {
        anchors = document.querySelectorAll('#languages a');
        anchors.forEach(function (el) {
            el.addEventListener('click', function (event) {
                event.preventDefault();
                document.cookie =
                    'lang=' + this.getAttribute('data-lang') + ';' +
                    'path=' + "${path}" + ';' +
                    'max-age=' + (30 * 24 * 60 * 60);

                location.reload();
            });
        });
    });
</script>
<div id="header">
    <h1>${metadata.title}</h1>
    <div id="languages">
        <c:choose>
            <c:when test='${lang == "en"}'>
                <a href="" data-lang="ko">KR</a>
                <a href="" data-lang="en" class="active">EN</a>
            </c:when>
            <c:otherwise>
                <a href="" data-lang="ko" class="active">KR</a>
                <a href="" data-lang="en">EN</a>
            </c:otherwise>
        </c:choose>
    </div>
    <ul>
        <c:choose>
            <c:when test="${empty authUser}">
                <li>
                    <a href="${path}/user/login"><spring:message key="header.gnb.login"/></a>
                <li>
                <li>
                    <a href="${path}/user/join"><spring:message key="header.gnb.join"/></a>
                <li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="${path}/user/update"><spring:message key="header.gnb.settings"/></a>
                <li>
                <li>
                    <a href="${path}/user/logout"><spring:message key="header.gnb.logout"/></a>
                <li>
                <li><spring:message key="header.gnb.greeting"/> ${authUser.name}</li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>