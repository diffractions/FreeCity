
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


<html>
<head>
<%@ include file="header.jspf"%>
<title>Free City : ${item.getHeader()}</title>
<link media="all" type="text/css" href="css/skins/green.css"
	rel="stylesheet">
</head>
<body class="blog" itemscope itemtype="http://schema.org/Blog">

	<div id="masthead">

		<jsp:include page="site-header.jsp" />



		
		 
		
		
		<div id="page-title"  <c:if test="${not empty city_selected}">style="background-image: url('${city_selected.getShortName()}.jpg'); " </c:if> >
			<div class="container">
				<div class="row">

					<c:if test="${not empty item}">
						<h1 class="entry-title" itemprop="headline">${item.getHeader()}</h1>
						<%-- 					<p class="description" itemprop="description">${item.description()}</p> --%>
					</c:if>
					<c:if test="${empty item}">
						<h1 class="entry-title" itemprop="headline">404</h1>
						<p class="description" itemprop="description">Обрана подія не
							знайдена</p>
					</c:if>

				</div>
				<!-- .row -->
			</div>
			<!-- .container -->
		</div>
		<!-- #page-title -->

	</div>
	<!-- #masthead -->

	<main id="content" role="main">

	<div class="section">
		<div class="container">
			<div class="row">


				<div class="three-quarters-block">
					<div class="content">
						<jsp:include page="item_one.jsp" />
					</div>

					<jsp:include page="entity-redactor-table.jsp" />

 
					<!-- .content -->
				</div>
				<!-- .three-quarters-block -->

				<div class="one-quarter-block" role="complementary">
					<div class="sidebar">
						<jsp:include page="widget-right-column.jsp" />
					</div>
					<!-- .sidebar -->
				</div>
				<!-- .one-quarter-block -->

			</div>
			<!-- .row -->
		</div>
		<!-- .container -->
	</div>
	<!-- .section --> </main>
	<!-- #content -->

	<jsp:include page="widget-buttom-page.jsp" />
	<jsp:include page="widget-footer.jsp" />


	<!-- Scripts -->
	<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="js/custom.js"></script>



</body>
</html>
</body>