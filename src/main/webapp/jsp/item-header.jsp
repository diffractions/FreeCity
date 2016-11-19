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


<header class="entry-header">





	<h2 class="entry-title" itemprop="headline">
		<a href="<jsp:include page="item-link-href.jsp" />">${item.getHeader()}</a>
	</h2>





	<jsp:include page="item-header-entry-meta.jsp" />

</header>
<!-- .entry-header -->