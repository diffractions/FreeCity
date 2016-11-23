<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>

<c:if test="${(not empty section_view &&  section_view < 10)}">


<jsp:include page="item-list-view-toggle.jsp" />

	
</c:if>


<c:if test="${(not empty section_view &&  section_view < 10)}">
	<div id="list1" class="tabcontent tabs">
</c:if>
<c:forEach var="itemc" items="${items}">

	<c:if test="${itemc.getStatus() >=chowedItemStatus || (itemc.getStatus()>=chowedItemUserCreateStatus && itemc.getUser().getUserId()==sessionScope.user.getUserId())}"> 
	<c:set var="item" value="${itemc}" scope="request" />
	<c:if test="${item.getSections().get(0).getSectionView() < 10}">
		<article class="post summary hentry" itemscope itemprop="blogPost"
			itemtype="http://schema.org/Article">

			<jsp:include page="item-header.jsp" />
			<!-- .entry-header -->

			<c:if test="${not empty item.getImagName()}">
				<div class="entry-thumbnail">
					<a href="<jsp:include page="item-link-href.jsp"/>"
						title="${item.getHeader()}"> <img
						src="${pageContext.request.contextPath}/user/img/${item.getImagName()}"
						alt="${item.getImagName()}" itemprop="image">
					</a>
				</div>
				<!-- .entry-thumbnail -->
			</c:if>
			<c:if test="${empty item.getImagName()}">
				<div class="entry-thumbnail">
					<a href="<jsp:include page="item-link-href.jsp"/>"
						title="${item.getHeader()}"> <img
						src="${item.getSections().get(0).getSectionId()}_img.svg"
						alt="${item.getSections().get(0).getSectionName()}">
					</a>
				</div>
				<!-- .entry-thumbnail -->
			</c:if>






			<div class="entry-summary" itemprop="articleBody">
				<p>${item.getDescription()}</p>
				<a class="more button"
					href="<jsp:include page="item-link-href.jsp" />">Читати далі</a>
			</div>
			<!-- 	.entry-content -->
			

<jsp:include page="entity-redactor-table.jsp" />

		</article>
		<!-- .post -->
	</c:if>
	</c:if>
</c:forEach>
<div class="block-divider"></div>
<jsp:include page="left-right-menu.jsp" />
<c:if test="${(not empty section_view &&  section_view < 10 )}">


</div>


 <jsp:include page="item-all-map.jsp" />

</c:if>





