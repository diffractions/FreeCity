
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>


<html>
<head>
<%@ include file="header.jspf"%>
<title>Free City : HOME</title>


</head>
<body class="blog" itemscope itemtype="http://schema.org/Blog">



	<div id="masthead">

		<jsp:include page="site-header.jsp" />

				<div id="page-title"  <c:if test="${not empty city_selected}">style="background-image: url('${city_selected.getShortName()}.jpg'); " </c:if> >
			<div class="container">
				<div class="row">
					<c:choose>
						<c:when test="${not empty city_selected}">
							<h1 class="entry-title" itemprop="headline">Останні події в
								м. ${city_selected.getName()}</h1>
						</c:when>
						<c:when test="${empty head_title}">
							<h1 class="entry-title" itemprop="headline">Останні події</h1>
						</c:when>
						<c:when test="${not empty head_title}">
							<h1 class="entry-title" itemprop="headline">${head_title}</h1>
						</c:when>
					</c:choose>
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


									<c:if test="${not empty errorString}">
										<div class="box error-box">${errorLoginString}</div>
										<% session.removeAttribute("errorLoginString"); %>
									</c:if> 
 
			<c:if test="${not empty showedSection}">
				<nav id="isotope-menu">
					<ul class="horizontal-navigation">
						<li <c:if test="${showedSection==102 || showedSection==101  || showedSection==100 }">class="active"</c:if>><a
							href="<%@ include file="href-path.jspf"%>list?section=${rootSection}">Всі</a></li>
						<c:set var="ch_menus" value="-1" scope="request" />
						<c:set var="ch_menu" value="${root_menu}" scope="request" />
						<jsp:include page="section-show.jsp" />
					</ul>
				</nav>
			</c:if>

			<div class="row">

				<div class="three-quarters-block">

					<div class="content"> 
						<c:choose>
							<c:when test="${not empty section_view && section_view==10}">
								<jsp:include page="item-list-10.jsp" />
							</c:when>
							<c:when test="${(not empty section_view &&  section_view < 10)}">
								<jsp:include page="item-list-9.jsp" />
							</c:when>
							<c:when test="${(empty section_view) }">
								<jsp:include page="item.jsp" />
							</c:when>
						</c:choose>

					</div>
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