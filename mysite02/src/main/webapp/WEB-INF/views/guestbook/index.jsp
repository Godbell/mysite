<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<%@ page import="mysite.vo.GuestBookVo" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<GuestBookVo> list = (List<GuestBookVo>)request.getAttribute("list");
%>
<c:set var="path" value="<%=request.getContextPath()%>"/>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${path}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="guestbook">
				<form action="${path}/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<li id="guestbook-list">
					</li>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
<script>
	const getGuestBookList = () => {
		return [
				<%
					int count = list.size();
					int index = 0;
					for (GuestBookVo vo : list) {
				%>
			{
				id: <%=vo.getId() %>,
				name: '<%=vo.getName() %>',
				regDate: '<%=vo.getRegDate() %>',
				content: `<%=vo.getContents().replaceAll("\n", "<br>") %>`,
			},
				<%
				}
				%>
		]
	}

	const renderGuestBookList = () => {
		const list = getGuestBookList();
		const guestBookListElement = document.getElementById('guestbook-list');

		let count = list.length;
		let index = 0;

		for (const guestBook of list) {
			const guestBookTableElement = document.createElement('table');
			const guestBookMetadataRowElement = document.createElement('tr');

			const idCol = document.createElement('td');
			idCol.innerHTML = '[' + (count - index++) + ']';
			guestBookMetadataRowElement.appendChild(idCol);

			const nameCol = document.createElement('td');
			nameCol.innerHTML = guestBook.name;
			guestBookMetadataRowElement.appendChild(nameCol);

			const regDateCol = document.createElement('td');
			regDateCol.innerHTML = guestBook.regDate;
			guestBookMetadataRowElement.appendChild(regDateCol);

			const deleteHrefCol = document.createElement('td');
			const deleteHref = document.createElement('a');
			deleteHref.href =
					'${path}/guestbook?a=deleteform&id=' + guestBook.id;
			deleteHref.innerHTML = '삭제';
			deleteHrefCol.appendChild(deleteHref);
			guestBookMetadataRowElement.appendChild(deleteHrefCol);

			guestBookTableElement.appendChild(guestBookMetadataRowElement);

			const guestBookContentRowElement = document.createElement('tr');

			const contentCol = document.createElement('td');
			contentCol.innerHTML = guestBook.content;
			contentCol.colSpan = 4;
			guestBookMetadataRowElement.appendChild(contentCol);

			guestBookContentRowElement.appendChild(contentCol);
			guestBookTableElement.appendChild(guestBookContentRowElement);

			guestBookListElement.appendChild(guestBookTableElement);
			guestBookListElement.appendChild(document.createElement('br'));
		}
	}
</script>
<script>
	renderGuestBookList();
</script>
</html>