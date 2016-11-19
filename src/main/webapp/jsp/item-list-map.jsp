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
<%@ page import="entity.Adress"%>
<%@ page import="java.util.Calendar"%>

<c:if test="${0 != item.getAdress().size()}">
	<a href="<jsp:include page="item-link-href.jsp" />">
		<div class="entry-meta-map-list">
			<span class="post-city" itemprop="articleBody"> <i
				class="fa fa-map-marker  fa-fw"></i> <c:forEach var="addres"
					items="${item.getAdress()}">
					${addres.getAdress()}
				</c:forEach></span> <span><jsp:include page="item-header-action.jsp" /></span>
		</div> <!-- .entry-meta -->

	</a> 
</c:if>

