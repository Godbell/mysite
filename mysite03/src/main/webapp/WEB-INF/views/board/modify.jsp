<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>

<jsp:useBean id="post" scope="request" type="mysite.vo.PostVo"/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>mysite</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link href="${path}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>
    <div id="content">
        <div id="board">
            <form class="board-form" method="post" action="${path}/board/update">
                <input type="hidden" name="id" value="${post.id}">
                <table class="tbl-ex">
                    <tr>
                        <th colspan="2">글수정</th>
                    </tr>
                    <tr>
                        <td class="label">제목</td>
                        <td>
                            <input type="text" name="title" value="${post.title}">
                        </td>
                    </tr>
                    <tr>
                        <td class="label">내용</td>
                        <td>
                            <textarea id="content" name="contents">${post.contents}</textarea>
                        </td>
                    </tr>
                </table>
                <div class="bottom">
                    <a href="${path}/board/${post.id}">취소</a>
                    <input type="submit" value="수정">
                </div>
            </form>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp">
        <c:param name="menu" value="board"/>
    </c:import>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
<script>
    const post = {
        title: `${fn:replace(post.title, "`", "\\`")}`,
        contents: `${fn:replace(post.contents, "`", "\\`")}`
    }

    document.getElementsByName("title")[0].value = post.title;
    document.getElementsByName("contents")[0].textContent = post.contents;
</script>
</html>