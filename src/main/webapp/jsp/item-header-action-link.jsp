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
						<%
					String timresp = "";
						if (request.getParameterMap().size() != 0) {
							timresp = "&" + request.getQueryString();
						}
						if (request.getParameter("action") != null) {
							timresp = request.getQueryString();
							timresp = timresp.replaceAll("action=active", "").replaceAll("action=now", "")
									.replaceAll("action=future", "").replaceAll("action=archive", "");
						}
 		%> 
		<span class="post-date"> <i class="fa fa-clock-o fa-fw"></i> <span class="updated">
			<c:if test="${0==item.getActiv()}"><a href="<%@ include file="href-path.jspf"%>list?action=active<%=timresp%>">Активна</a></c:if>
			<c:if test="${1==item.getActiv()}"><a href="<%@ include file="href-path.jspf"%>list?action=future<%=timresp%>">Запланована подія</a></c:if>
			<c:if test="${2==item.getActiv()}"><a href="<%@ include file="href-path.jspf"%>list?action=archive<%=timresp%>">Запис в архіві</a></c:if>
			<c:if test="${3==item.getActiv()}"><a href="<%@ include file="href-path.jspf"%>list?action=now<%=timresp%>">Можна зараз відвідати</a></c:if>
		</span>
		</span>
		<!-- .post-date -->
	</c:if>