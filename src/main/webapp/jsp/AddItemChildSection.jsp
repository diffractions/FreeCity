<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<c:if test="${empty ch_menus.childSections}">

	<c:set var="ch_section_item" value="${ch_menus}" scope="request" /> 
	<option value="${ch_menus.getSectionId()}" <c:if test="${item.getSections().get(0).getSectionId()==ch_menus.getSectionId()}">selected="selected"</c:if>><jsp:include
			page="item_section_tree.jsp" /></option>
</c:if>


<c:if test="${not empty ch_menus.childSections}">
	<c:set var="ch_section_item" value="${ch_menus}" scope="request" />
	<option value="${ch_menus.getSectionId()}" <c:if test="${item.getSections().get(0).getSectionId()==ch_menus.getSectionId()}">selected="selected"</c:if>><jsp:include
			page="item_section_tree.jsp" /></option>
	<optgroup label="${ch_menus.getSectionName()}">
		<c:forEach var="new_ch_menu" items="${ch_menus.childSections}">
			<c:set var="ch_menus" value="${new_ch_menu}" scope="request" />
			<jsp:include page="AddItemChildSection.jsp" />
		</c:forEach>
	</optgroup>
</c:if>