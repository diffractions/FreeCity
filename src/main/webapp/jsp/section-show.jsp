<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Tag"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>


<%-- <c:if test="${showedSection==ch_menus}"> --%>
<%-- 	<c:forEach var="rms" --%>
<%-- 		items="${menus.getRootSetion().getChildSections()}"> --%>
<%-- 		<li><c:set var="s_menu" value="${rms}" scope="request" /> <jsp:include --%>
<%-- 				page="section-show-changed-with-city.jsp" /></li> --%>
<%-- 	</c:forEach> --%>
<%-- </c:if> --%>



<%-- <c:if test="${showedSection!=ch_menus}"> --%>
<%-- 	<c:forEach var="menu" items="${ch_menu}"> --%>
<%-- 		<c:set var="menus" value="${menu}" scope="request" /> --%>
<%-- 		<c:set var="ch_menus" value="${menu.getSectionId()}" scope="request" /> --%>
<%-- 		<c:set var="ch_menu" value="${menu.getChildSections()}" --%>
<%-- 			scope="request" /> --%>
<%-- 		<jsp:include page="section-show.jsp" /> --%>
<%-- 	</c:forEach> --%>
<%-- </c:if> --%>

<c:if test="${showedSection==ch_menus}">
	<c:forEach var="rms"
	
		items="${menus.getRootSetion().getChildSections()}">
		
		<c:forEach var="rms1" items="${rms.getAllChilds()}"><c:if test="${showedSection==rms1.getSectionId()}"><c:set var="s" value="true" scope="page"/></c:if></c:forEach>
		
		 
		<li <c:if test="${ch_menus==rms.getSectionId() || s}">class="active"</c:if>><c:set var="s_menu" value="${rms}" scope="request" /> <jsp:include
				page="section-show-changed-with-city.jsp" /></li>
		<c:set var="s" value="false" scope="page"/>
	</c:forEach>
</c:if>




<c:if test="${showedSection!=ch_menus}">
	<c:forEach var="menu" items="${ch_menu}">
		<c:set var="menus" value="${menu}" scope="request" />
		<c:set var="ch_menus" value="${menu.getSectionId()}" scope="request" />
		<c:set var="ch_menu" value="${menu.getChildSections()}"
			scope="request" />
		<jsp:include page="section-show.jsp" />
	</c:forEach>
</c:if>
