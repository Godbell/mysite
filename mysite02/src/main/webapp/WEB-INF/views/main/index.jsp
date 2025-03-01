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
    <link href="${path}/assets/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>

    <div id="wrapper">
        <div id="content">
            <div id="site-introduction">
                <img id="profile" src="https://godbell.kr/content/images/size/w2000/2023/05/nest-og.png"
                     style="width: 100%;">
                <h2>안녕하세요. 김종하의 mysite에 오신 것을 환영합니다.</h2>
                <p>
                    이 사이트는 자바 프로그램밍 실습과제 예제 사이트입니다.<br>
                    메뉴는 사이트 소개, 방명록, 게시판이 있구요. Python 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서
                    만들어 놓은 사이트 입니다.<br><br>
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