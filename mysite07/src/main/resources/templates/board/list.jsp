<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
<jsp:useBean id="list" scope="request" type="java.util.List<mysite.vo.PostVo>"/>
<jsp:useBean id="postsCountPerPage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="totalCount" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="currentPage" scope="request" type="java.lang.Integer"/>

<c:if test='<%=request.getAttribute("q") != null%>'>
    <jsp:useBean id="q" scope="request" type="java.lang.String"/>
</c:if>

<!DOCTYPE html>
<html lang="ko">
<c:import url="/WEB-INF/views/includes/head.jsp">
    <c:param name="stylesheetPath" value="board"/>
</c:import>
<body>
<div id="container">
    <c:import url="/WEB-INF/views/includes/header.jsp"/>
    <div id="content">
        <div id="board">
            <form id="search_form" action="${path}/board" method="get">
                <input type="text" id="kwd" name="q" value="">
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

            <div class="pager">
            </div>

            <div class="bottom">
                <c:if test='${!empty pageContext.session.getAttribute("user")}'>
                    <a href="${path}/board/add" id="new-book">글쓰기</a>
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
    const boardData = {
        posts: [
            <c:forEach var="post" items="${list}" varStatus="status">
            {
                id: ${post.id},
                index: ${post.index},
                title: '${post.title}',
                userId: ${post.userId},
                username: '${post.username}',
                depth: ${post.depth},
                groupNo: ${post.groupNo},
                regDate: '${post.regDate}',
                hit: ${post.hit},
            },
            </c:forEach>
        ],
        totalCount: ${totalCount},
        postsCountPerPage: ${postsCountPerPage},
        currentPage: ${currentPage},
        pageHrefsCountPerPage: 5,
    };

    const renderBoard = () => {
        let userId = ${empty authUser ? -1 : authUser.id};
        const table = document.getElementById('board_list')

        for (const post of boardData.posts) {
            const tr = document.createElement('tr');

            const idColumn = document.createElement('td');
            idColumn.innerHTML = post.index;
            tr.appendChild(idColumn);

            const titleColumn = document.createElement('td');
            titleColumn.className = 'title-area';
            const title = document.createElement('a');
            title.href = '${path}/board/' + post.id;
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
                deleteButton.href = '${path}/board/delete/' + post.id;
            } else {
                deleteButton.style.opacity = '0';
            }
            deleteButtonColumn.appendChild(deleteButton);
            tr.appendChild(deleteButtonColumn);

            table.appendChild(tr);
        }
    }

    const renderPager = () => {
        const pagerContainer = document.getElementsByClassName('pager')[0];
        const pager = document.createElement('ul');
        pagerContainer.appendChild(pager);

        const lastPageButtonContainer = document.createElement('li');
        const lastPageButton = document.createElement('a');
        lastPageButton.textContent = '◀';
        lastPageButton.href = boardData.currentPage > 1
            ? `${path}/board?page=\${boardData.currentPage - 1}${q == null ? "" : "&q=".concat(q)}`
            : '';
        lastPageButtonContainer.appendChild(lastPageButton);
        pager.appendChild(lastPageButtonContainer);

        const maxPage = Math.ceil(boardData.totalCount / boardData.postsCountPerPage)
        let startIndex = 1;
        let endIndex = 5;

        if (boardData.currentPage <= Math.ceil(boardData.pageHrefsCountPerPage / 2)) {
            startIndex = 1;
            endIndex = boardData.pageHrefsCountPerPage;
        } else if (
            boardData.currentPage > maxPage - Math.ceil(boardData.pageHrefsCountPerPage / 2)
        ) {
            startIndex = maxPage - boardData.pageHrefsCountPerPage + 1;
            startIndex = startIndex > 0 ? startIndex : 1;
            endIndex = maxPage < boardData.pageHrefsCountPerPage ? 5 : maxPage;
        } else {
            startIndex = boardData.currentPage - 2;
            endIndex = boardData.currentPage + 2;
        }

        for (let page = startIndex; page <= endIndex; ++page) {
            const pageHrefContainer = document.createElement('li');
            pageHrefContainer.className = page === boardData.currentPage ? 'selected' : '';

            if (page <= maxPage) {
                const pageHref = document.createElement('a');
                pageHref.href = `${path}/board?page=\${page}${q == null ? "" : "&q=".concat(q)}`;
                pageHref.textContent = `\${page}`
                pageHrefContainer.appendChild(pageHref);
            } else {
                pageHrefContainer.textContent = `\${page}`;
            }

            pager.appendChild(pageHrefContainer);
        }

        const nextPageButtonContainer = document.createElement('li');
        const nextPageButton = document.createElement('a');
        nextPageButton.textContent = '▶';
        nextPageButton.href = boardData.currentPage < maxPage
            ? `${path}/board?page=\${boardData.currentPage + 1}${q == null ? "" : ("&q=".concat(q))}`
            : '';
        nextPageButtonContainer.appendChild(nextPageButton);
        pager.appendChild(nextPageButtonContainer);
    }
</script>
<script>
    renderBoard();
    renderPager();
</script>
</html>