	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Rating"%>
<%@ page import="entity.Section"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>
	
		<c:if test="${not empty item.getActiv() && item.getActiv() !=-1}">

		<span class="post-date"> <i class="fa fa-clock-o fa-fw"></i> <span class="updated" style="color: maroon; font-weight: 600;">
			<c:if test="${0==item.getActiv()}">Активна</c:if>
			<c:if test="${1==item.getActiv()}">Запланована подія</c:if>
			<c:if test="${2==item.getActiv()}">Запис в архіві</c:if>
			<c:if test="${3==item.getActiv()}">Можна зараз відвідати</c:if>
		</span>
		</span>
		<!-- .post-date -->
	</c:if>