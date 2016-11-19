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


<c:if test="${empty city_selected}">
	<c:choose>
		<c:when test="${8 > s_menu.getSectionView()}">
			<a href="<c:if test="${not empty city_atr}"><%@ include file="href-path.jspf"%></c:if><c:if test="${empty city_atr}">${pageContext.request.contextPath}/</c:if>list?section=${s_menu.getSectionId()}">${s_menu.getSectionName()}
			</a>
		</c:when>
		<c:when test="${9 == s_menu.getSectionView()}">
			<a href="<c:if test="${not empty city_atr}"><%@ include file="href-path.jspf"%></c:if><c:if test="${empty city_atr}">${pageContext.request.contextPath}/</c:if>list?section=${s_menu.getSectionId()}">${s_menu.getSectionName()}
			</a>
		</c:when>
	</c:choose>
</c:if>
<c:if test="${not empty city_selected}">
	<c:choose>
		<c:when test="${8 > s_menu.getSectionView()}">
			<a href="<c:if test="${not empty city_atr}"><%@ include file="href-path.jspf"%></c:if><c:if test="${empty city_atr}">${pageContext.request.contextPath}/</c:if>list?section=${s_menu.getSectionId()}">${s_menu.getSectionName()}
			</a>
		</c:when>
		<c:when test="${9 == s_menu.getSectionView()}">
			<a href="<c:if test="${not empty city_atr}"><%@ include file="href-path.jspf"%></c:if><c:if test="${empty city_atr}">${pageContext.request.contextPath}/</c:if>list?section=${s_menu.getSectionId()-1}">${s_menu.getSectionName()}
			</a>
		</c:when>
	</c:choose>
</c:if>

