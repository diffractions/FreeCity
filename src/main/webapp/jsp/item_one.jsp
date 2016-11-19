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


<article class="post hentry" itemscope itemprop="blogPost"
	itemtype="http://schema.org/Article">
	<c:if test="${not empty item}">
	
		<header class="entry-header">
			<jsp:include page="item-header-entry-meta.jsp" />
		</header>
 

		<c:if test="${not empty item.getImagName()}">
			<div class="entry-image">
				<img
					src="${pageContext.request.contextPath}/user/img/${item.getImagName()}"
					alt="${item.getImagName()}" itemprop="image">
			</div>
<!-- 		 .entry-image --  -->
		</c:if>
		
		
		<c:if test="${not empty item.getDescription()}">
			<div class="entry-content" itemprop="articleBody">
				<p>${item.getDescription()}</p>
			</div>
			<!-- .entry-content -->
		</c:if>


		<div class="entry-content">
			<div class="half-block">

				<jsp:include page="item-one-address.jsp" />
				<c:if test="${not empty item.getWork()}">
					<!-- 	add work time -->
					<jsp:include page="widget-work-time.jsp" />
				</c:if>
				<c:if test="${not empty item.getURLs().get(0)}">
					<!-- 	add url -->
					<h1 class="entry-title"
						style="margin-bottom: 1ex; margin-top: 1ex;">Посилання:</h1>
					<c:forEach var="url" items="${item.getURLs()}">
						<div style=" margin-left: 20px; width: 370px; overflow: hidden; height: 50px; line-height: 50px; text-overflow: ellipsis;" >
							<a   target="_blank" href="http://${url.replace("http://","").replace("https://","")}" title="${url}">
								${url}<i class="fa fa-external-link fa-fw"></i>
							</a> 
						</div>
					</c:forEach>
					
					

					
					
				</c:if>
			</div>
			<!-- .half-block -->
			<c:if test="${not empty item.getMaps()}">
				<!-- 	add maps -->
				<div class="half-block" role="complementary">
					<jsp:include page="widget-map.jsp" />
				</div>
				<!-- .half-block -->
			</c:if>

		</div>
		<!-- .entry-content -->
	</c:if>
	<c:if test="${empty item}">
		<h4>Подія не знайдена</h4>
		<div class="box error-box">
			<ul>
				<li><a href="<%@ include file="href-path.jspf"%>">Повернутися
						до головної сторіки Міста - ${city_atr.getName()}</a></li>
				<li><a href="${pageContext.request.contextPath}"
					onclick="delete_cookie ('city')">Повернутися до головної
						сторіки </a></li>
			</ul>
		</div>
	</c:if>
</article>
<!-- .post -->



