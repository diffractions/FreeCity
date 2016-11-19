<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>



<div id="view-toggle-wrapper">
		<div class="viewer-toggle-nav">
			<ul class="horizontal-navigation">
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
				<li class="menu-home"><a href="javascript:void(0)"
					onclick="myFunction('action')" id="times"> <c:if
							test="${empty param['action']}"> Анонси подій</c:if> <c:if
							test="${not empty param['action']}">
							<i
								onclick="delete_cook ('times', '<%@ include file="href-path.jspf"%>list?<%=timresp.startsWith("&") ? timresp.replaceFirst("&", "") : timresp%>')"
								class="fa fa-close fa-fw"></i>
							<c:if test="${param['action'].equals(\"active\")}"> Активні події </c:if>
							<c:if test="${param['action'].equals(\"now\")}"> Відбуваються зараз </c:if>
							<c:if test="${param['action'].equals(\"future\")}"> Заплановані події </c:if>
							<c:if test="${param['action'].equals(\"archive\")}"> Архів </c:if> 
						</c:if></a>
					<ul class="vertical-navigation" id="action">
						<li class="menu-home"><a id="action-active" href="<%@ include file="href-path.jspf"%>list?action=active<%=timresp%>">Активні </a></li>
						<li class="menu-home"><a id="action-now" href="<%@ include file="href-path.jspf"%>list?action=now<%=timresp%>">Відбуваються зараз</a></li>
						<li class="menu-home"><a id="action-future" href="<%@ include file="href-path.jspf"%>list?action=future<%=timresp%>">Заплановані</a></li>
						<li class="menu-home"><a id="action-archive" href="<%@ include file="href-path.jspf"%>list?action=archive<%=timresp%>">Архів</a></li>
					</ul></li> 
				<li><a id="list_ref" href="#list1" onclick="openCity(event, 'list1')" class="tablinks"><i class="fa  fa-th-list fa-fw" aria-hidden="true"></i>Список</a></li>
				<li><a id="map_ref" href="#map" onclick="openCity(event, 'map')" class="tablinks"><i class="fa fa-map-marker fa-fw" aria-hidden="true"></i>Мапа</a></li> 
 			</ul>
		</div>
		<div id="counter">Записів: ${items_count}</div>
	</div>