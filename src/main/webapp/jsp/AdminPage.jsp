<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="header.jspf"%>
</head>
<body>

	<!-- add menu -->
	<jsp:include page="site-header.jsp" />



	<div class="content">
		<form action="admin" method="post">
			<input type="text" name="city"><input type="submit"
				value="create city">
		</form>
		<form action="admin" method="post">
			<select name="del_city">
				<c:forEach var="city" items="${atr_city}">
					<option value="${city.getId()}">${city.getName()}</option>
				</c:forEach>
			</select> <input type="submit" value="delete city">
		</form>

		<form action="admin" method="post">
			<input type="text" name="section"><jsp:include
				page="create-item-form-section.jsp" /><input type="submit"
				value="create section">
		</form>

		<form action="admin" method="post">
			<input type="hidden" name="del_section" value="1"><jsp:include
				page="create-item-form-section.jsp" /><input type="submit"
				value="delete section">
		</form>
	</div>
</body>
</html>