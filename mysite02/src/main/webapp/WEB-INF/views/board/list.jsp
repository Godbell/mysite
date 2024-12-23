<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<jsp:useBean id="list" scope="request" type="java.util.List<mysite.vo.BoardVo>"/>
<jsp:useBean id="postsCountPerPage" scope="request" type="java.lang.Integer"/>

<c:choose>
    <c:when test='<%=session.getAttribute("authUser") != null %>'>
        <jsp:useBean id="authUser" scope="session" type="mysite.vo.UserVo"/>
    </c:when>
</c:choose>

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
            <form id="search_form" action="" method="post">
                <input type="text" id="kwd" name="kwd" value="">
                <input type="submit" value="찾기">
            </form>
            <table id="board_list" class="tbl-ex">
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>글쓴이</th>
                    <th>조회수</th>
                    <th>작성일</th>
                    <th>&nbsp;</th>
                </tr>
            </table>

            <!-- pager 추가 -->
            <div class="pager">
                <ul>
                    <li><a href="">◀</a></li>
                    <li><a href="">1</a></li>
                    <li class="selected">2</li>
                    <li><a href="">3</a></li>
                    <li>4</li>
                    <li>5</li>
                    <li><a href="">▶</a></li>
                </ul>
            </div>
            <!-- pager 추가 -->

            <div class="bottom">
                <c:if test='${!empty pageContext.session.getAttribute("authUser")}'>
                    <a href="${path}/board?a=write" id="new-book">글쓰기</a>
                </c:if>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/views/includes/navigation.jsp">
        <c:param name="menu" value="board"/>
    </c:import>
    <c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
<script>
    const boardData = [
        <c:forEach var="post" items="${list}" varStatus="status">
        {
            id: ${post.id},
            title: '${post.title}',
            userId: ${post.userId},
            username: '${post.username}',
            depth: ${post.depth},
            groupNo: ${post.groupNo},
            regDate: '${post.regDate}',
            hit: ${post.hit},
        },
        </c:forEach>
    ];

    const renderBoard = () => {
        let userId = ${empty authUser ? -1 : authUser.id};
        const table = document.getElementById('board_list')

        for (const post of boardData) {
            const tr = document.createElement('tr');

            const idColumn = document.createElement('td');
            idColumn.innerHTML = post.id;
            tr.appendChild(idColumn);

            const titleColumn = document.createElement('td');
            titleColumn.className = 'title-area';
            const title = document.createElement('a');
            title.href = '${path}/board?a=view&post_id=' + post.id;
            for (let i = 0; i < post.depth; ++i) {
                title.innerHTML += '⎿';
            }
            title.innerHTML += ' ';
            title.innerHTML += post.title;
            titleColumn.appendChild(title);
            tr.appendChild(titleColumn);

            const usernameColumn = document.createElement('td');
            usernameColumn.innerHTML = post.username;
            tr.appendChild(usernameColumn);

            const hitColumn = document.createElement('td');
            hitColumn.innerHTML = post.hit;
            tr.appendChild(hitColumn);

            const regDateColumn = document.createElement('td');
            regDateColumn.innerHTML = post.regDate;
            tr.appendChild(regDateColumn);

            const deleteButtonColumn = document.createElement('td');
            const deleteButton = document.createElement('a');
            deleteButton.className = 'del';
            deleteButton.innerHTML = '삭제';
            if (userId === post.userId) {
                deleteButton.href = '${path}/board?a=delete&id=' + post.id;
            } else {
                deleteButton.style.opacity = '0';
            }
            deleteButtonColumn.appendChild(deleteButton);
            tr.appendChild(deleteButtonColumn);

            table.appendChild(tr);
        }
    }
</script>
<script>
    renderBoard();
</script>
</html>