<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<select name="section-id">
	<c:forEach var="menu" items="${root_menu}">
		<c:set var="ch_section_item" value="${menu}" scope="request" />
		<option value="${menu.getSectionId()}" <c:if test="${item.getSections().get(0).getSectionId()==menu.getSectionId()}">selected="selected"</c:if> ><jsp:include
				page="item_section_tree.jsp" /></option>
		<c:if test="${not empty menu.childSections}">
			<optgroup label="${menu.getSectionName()}">
				<c:forEach var="ch_menu" items="${menu.childSections}">
					<c:set var="ch_menus" value="${ch_menu}" scope="request" />
					<jsp:include page="AddItemChildSection.jsp" />
				</c:forEach>
			</optgroup>
		</c:if>
	</c:forEach>
</select>