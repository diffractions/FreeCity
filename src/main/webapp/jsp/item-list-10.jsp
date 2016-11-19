<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>






<jsp:include page="item-list-view-toggle.jsp" />



<div id="list1" class="tabcontent tabs">
 
	<article class="post summary hentry" style="padding-left: 0;" itemscope itemprop="blogPost"
		itemtype="http://schema.org/Article"> 
		<c:forEach var="itemc" items="${items}">
			<c:set var="item" value="${itemc}" scope="request" />
			<c:if test="${item.getSections().get(0).getSectionView() == 10}">
				<jsp:include page="item-list-map.jsp" />
			</c:if>
		</c:forEach>
		<jsp:include page="left-right-menu.jsp" />
 
	</article>
</div> 

<jsp:include page="item-all-map.jsp" />