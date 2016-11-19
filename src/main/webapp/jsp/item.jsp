<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>



<c:forEach var="itemc" items="${items}">
	<c:set var="item" value="${itemc}" scope="request" />
	<c:if test="${item.getSections().get(0).getSectionView() < 10}">

		<article class="post hentry" itemscope itemprop="blogPost"
			itemtype="http://schema.org/Article">

			<jsp:include page="item-header.jsp" />
			<!-- .entry-header -->

			<c:if test="${not empty item.getImagName()}">
				<div class="entry-image">
					<img
						src="${pageContext.request.contextPath}/user/img/${item.getImagName()}"
						alt="${item.getImagName()}" itemprop="image">
				</div>
				<!-- 		 .entry-image --  -->
			</c:if>
			<c:if test="${empty item.getImagName()}">
				<div class="entry-image">
					<img src="${item.getSections().get(0).getSectionId()}_img.svg"
						alt="${item.getSections().get(0).getSectionName()}">
				</div>
				<!-- 		 .entry-image --  -->
			</c:if>

			<div class="entry-content" itemprop="articleBody">
				<p>${item.getDescription()}</p>
				<a class="more button"
					href="<jsp:include page="item-link-href.jsp" />">Читати далі</a>
			</div>




			<!-- 	.entry-content -->



		</article>
		<!-- .post -->
	</c:if>
</c:forEach>
<div class="pagination">

 
		
		
	<div style="width: 100%">
		<a class="more button"
			href="<%@ include file="href-path.jspf"%>list"
			style="float:right; margin: 0 auto; text-align: center;">Показати
			більше оновлень</a> 
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>

	<table style="width: 99%;">
		<tr>  
			<td><a class="more button" href="<%@ include file="href-path.jspf"%>list?section=100" style="width: 100%; margin: 0 auto; text-align: center;">Перейти в розділ подій</a></td>
			<td><a class="more button" href="<%@ include file="href-path.jspf"%>list?section=101" style="width: 100%; margin: 0 auto; text-align: center;">Перейти в розділ місць</a></td>
			<td><a class="more button" href="<%@ include file="href-path.jspf"%>list?section=102" style="width: 100%; margin: 0 auto; text-align: center;">Перейти в розділ послуг</a></td>
		</tr>
	</table>
 
</div>


