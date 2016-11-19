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


<div class="entry-meta">

	<c:if test="${not empty item.getDate()}">
		<span class="post-date"> <i class="fa fa-pencil fa-fw"></i> <span
			class="updated">${item.getDate().get(11)}:${item.getDate().get(12)}
				<a
				href="<c:if test="${not empty city_atr}"><%@ include file="href-path.jspf"%></c:if><c:if test="${empty city_atr}">${pageContext.request.contextPath}/</c:if>list?date=${item.getDate().getTimeInMillis()}">${item.getDate().get(5)}.${1+item.getDate().get(2)}.${item.getDate().get(1)}</a>
		</span>
		</span>
		<!-- .post-date -->
	</c:if>



	


	<c:if test="${not empty item.getSections()}">
		<span class="post-categories"> <i class="fa fa-folder fa-fw"></i>
			<c:set var="ch_section_item" value="${item.getSections().get(0)}"
				scope="request" /> <jsp:include page="item_section_tree.jsp" />
		</span>
		<!-- .post-categories -->
	</c:if>

	<c:if test="${not empty item.getTags()}">
		<span class="post-tags"> <i class="fa fa-tags fa-fw"></i> <c:forEach
				var="tag" items="${item.getTags()}"> 
	 		'<a rel="tag"
					href="<c:if test="${not empty city_atr}"><%@ include file="href-path.jspf"%></c:if><c:if test="${empty city_atr}">${pageContext.request.contextPath}/</c:if>list?tag=${tag.getTagId()}">${tag.getTagName()}</a>' 
	 	</c:forEach>
		</span>
		<!-- .post-tags -->
	</c:if>

	<c:if test="${not empty item.getCities()}">
		<span class="post-city"> <i class="fa fa-building-o fa-fw"></i>
			<c:forEach var="city" items="${item.getCities()}">
				<a class="news_city"
<%-- 					href="<%@ include file="href-path.jspf"%>list?city=${city.getId()}">${city.getName()}</a> --%>
								href="${pageContext.request.contextPath}/${city.getShortName()}" onclick="setCookie('city', '${city.getId()}', 5 )">${city.getName()} </a>
					
			</c:forEach>
		</span>
		<!-- 		<!-- .post-city -->
	</c:if>
	
<jsp:include page="item-header-action-link.jsp" />

</div>
<!-- .entry-meta -->