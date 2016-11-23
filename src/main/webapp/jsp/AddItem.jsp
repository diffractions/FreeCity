<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>
<html>
<head>
<%@ include file="header.jspf"%>
<title>Free City : <c:if test="${ empty item}">Створити новий запис</c:if>
	<c:if test="${not empty item}">Редагувати запис</c:if></title>
</head>
<body class="contact" itemscope itemtype="http://schema.org/ContactPage">

	<div id="masthead">

		<jsp:include page="site-header.jsp" />

		<div id="page-title">
			<div class="container">
				<div class="row">

					<c:if test="${ empty item}">
						<h1 class="entry-title" itemprop="headline">Створити новий
							запис</h1>
					</c:if>
					<c:if test="${not empty item}">
						<h1 class="entry-title" itemprop="headline">Редагувати запис</h1>
					</c:if>
					<p class="description" itemprop="description">Користуйтеся
						всіма принадами життя у вашому місті</p>
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
									<c:if test="${not empty errorString}">
										<div class="box error-box">${errorString}</div>
									</c:if>
						<article class="post hentry" itemscope itemprop="blogPost"
							itemtype="http://schema.org/Article">

							<jsp:include page="create-item-form.jsp" /></article>
					</div>
				</div>
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
	</div>
	</main>



	<jsp:include page="widget-buttom-page.jsp" />
	<jsp:include page="widget-footer.jsp" />


	<!-- Scripts -->
	<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="js/custom.js"></script>

</body>
</html>