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



<div class="search-widget widget">
	<form class="widget-form" action="#" method="get" role="search">
		<label> <span class="screen-reader-text">Search this
				website:</span> <input type="search" class="search-field"
			placeholder="Search &hellip;" value="" name="search" title="Search" />
		</label>
		<button>
			<i class="fa fa-search"></i>
		</button>
	</form>
</div>
<!-- .search.widget -->


<div class="recent-posts-widget widget">
	<h4 class="widget-title">
		Останні оновлення:</br> <a href="<%@include file="href-path.jspf"%>list?section=101">Місця
		<c:if test="${not empty city_atr}"> - ${city_atr.getName()}</c:if></a>
	</h4>
	<ul>
		<c:forEach var="place" items="${last_places}">

			<li class="recent-post"><c:set var="item" value="${place}"
					scope="request" /> <a
				href="<jsp:include page="item-link-href.jsp" />"
				title="${place.getHeader()}" rel="bookmark"> <span
					class="recent-post-title"> ${place.getHeader()} </span> <span
					class="recent-post-thumbnail"> <img
						src="${place.getSections().get(0).getSectionId()}_img.svg"
						alt="${place.getSections().get(0).getSectionName()}">
				</span> <span class="recent-post-summary">
						${item.getDate().get(5)}.${1+item.getDate().get(2)}.${item.getDate().get(1)}
				</span>
			</a></li>

		</c:forEach>
	</ul>
</div>


<div class="recent-posts-widget widget">
	<h4 class="widget-title">
		Останні оновлення:</br> <a href="<%@include file="href-path.jspf"%>list?section=102">Послуги
		<c:if test="${not empty city_atr}"> - ${city_atr.getName()}</c:if></a>
	</h4>
	<ul>
		<c:forEach var="offer" items="${last_odffer}">
			<li class="recent-post"><c:set var="item" value="${offer}"
					scope="request" /> <a
				href="<jsp:include page="item-link-href.jsp" />"
				title="${offer.getHeader()}" rel="bookmark"> <span
					class="recent-post-title"> ${offer.getHeader()} </span> <span
					class="recent-post-thumbnail"> <img
						src="${offer.getSections().get(0).getSectionId()}_img.svg"
						alt="${offer.getSections().get(0).getSectionName()}">
				</span> <span class="recent-post-summary">
						${item.getDate().get(5)}.${1+item.getDate().get(2)}.${item.getDate().get(1)}
				</span>
			</a></li>
		</c:forEach>
	</ul>
</div>

<div class="tags widget">
	<h4 class="widget-title">Tags</h4>
	<ul>
		<c:forEach var="tag" items="${tag_atr}">
			<a class="screen-reader-text" href="<%@ include file="href-path.jspf"%>list?tag=${tag.getTagId()}">${tag.getTagName()}</a>
		</c:forEach>
	</ul>

</div>

