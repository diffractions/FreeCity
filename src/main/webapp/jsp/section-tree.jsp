<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>


<c:set var="ch1_section_item" value="${ch_section_item}" />
<c:if test="${not empty ch_section_item.getParrent()}">
	<c:set var="ch_section_item" value="${ch_section_item.getParrent()}" scope="request" />
	<jsp:include page="item_section_tree.jsp" />
	<c:if test="${10 > ch1_section_item.getSectionView()}">/</c:if>
</c:if>


		<c:set var="s_menu" value="${ch1_section_item}" scope="request" />
<jsp:include page="section-show-changed-with-city.jsp" />



