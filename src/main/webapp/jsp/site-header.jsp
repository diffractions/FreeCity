<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>




<%@ page import="utils.HTTP_URL_Utils"%>

<%
	String toresp = "";
	int from = -1;
	if ((from = request.getRequestURI().toString().indexOf("/list")) != -1)
		toresp = (request.getRequestURI().substring(from)
				+ ((request.getQueryString()) != null ? "?" + request.getQueryString() : ""));
%>
 <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
 <script>
   $(document).ready(function () {
       $(window).scroll(function () {
           if ($(this).scrollTop() > 100) {
               $('.scrollup').fadeIn();
           } else {
               $('.scrollup').fadeOut();
           }
       });
       $('.scrollup').click(function () {
           $("html, body").animate({
               scrollTop: 0
           }, 600);
           return false;
       });
   });
 </script>
 <a href="#" class="scrollup">Scroll</a>
<div id="site-header" role="banner">
	<div class="container">
		<div class="row">
 
			<div id="branding">
				<a class="logo" onclick="delete_cookie ('city')"
					href="http://freecity.in.ua/">FreeCity.in.ua</a>
			</div> 

			<!-- #branding -->

			<nav id="main-menu" role="navigation" itemscope
				itemtype="http://schema.org/SiteNavigationElement">
				<ul class="horizontal-navigation">


					<li class="menu-home"><c:choose>

							<c:when test="${(empty city_selected) && (empty city_atr)}">
								<a class="menu-home" href="javascript:void(0)" id="city-list"
									onclick="myFunction('myUL')">Вибрати місто</a>
							</c:when>


							<c:when test="${not empty city_selected}">
								<a class="menu-home" href="javascript:void(0)"
									onclick="myFunction('myUL')" id="city-list">${city_selected.getName()}
								</a>
							</c:when>


							<c:when test="${not empty city_atr}">
								<a class="menu-home" href="javascript:void(0)"
									onclick="myFunction('myUL')" id="city-list">${city_atr.getName()}
								</a>
							</c:when>



						</c:choose>

						<ul class="vertical-navigation" id="myUL"
							style="height: 300px; overflow: auto;">
							<li><input class="cityListHeader" type="text"
								placeholder="Search city.." id="myInput" onkeyup="myFunction1()"></li>
							<li><a
								href="${pageContext.request.contextPath}<%= toresp %>"
								onclick="delete_cookie ('city')"
								title="показати всі міста">ВСІ МІСТА</a></li>
							<c:forEach var="city" items="${cities}">
								<li class="menu-home" itemprop="url"><a
									href="${pageContext.request.contextPath}/${city.getShortName()}<%= toresp %>"
									onclick="setCookie('city', '${city.getId()}', 5 )"
									title="${city.getName()}">${city.getName()} </a>
								<li>
							</c:forEach>
						</ul></li>





					<c:forEach var="menu" items="${root_menu}">
						<li
							class="menu-home <c:if test="${rootSection==menu.getSectionId()}">active</c:if>"
							itemprop="url"><a
							href="<c:if test="${not empty city_atr}"><%@ include file="href-path.jspf"%></c:if><c:if test="${empty city_atr}">${pageContext.request.contextPath}/</c:if>list?section=${menu.getSectionId()}">${menu.getSectionName()}</a>
							<ul class="vertical-navigation">
								<c:forEach var="ch_menu" items="${menu.childSections}">
									<c:set var="ch_menus" value="${ch_menu}" scope="request" />
									<jsp:include page="section-header-section-childs.jsp" />
								</c:forEach>
							</ul></li>
					</c:forEach>


					<c:if test="${not empty sessionScope.user}">
						<li class="menu-home menu-home-not"><a>Привіт: ${user.getFirstName()}</a>
							<ul class="vertical-navigation ">
								<li class="menu-home"><a onclick="delete_cookie ('city')"
					href="${pageContext.request.contextPath}/create">Створити запис</a></li>
								<li class="menu-home"><a onclick="delete_cookie ('city')"
					href="${pageContext.request.contextPath}/list?user=${sessionScope.user.getUserId()}">Записи користувача</a></li>
								<li class="menu-home"><a onclick="delete_cookie ('city')"
					href="${pageContext.request.contextPath}/user?user=${sessionScope.user}">Редагувати дані</a></li>
								<li class="menu-home"><a onclick="delete_cookie ('city')"
					href="${pageContext.request.contextPath}/login?logout=exit">Вихід</a></li>
							</ul></li>
					</c:if>
<%-- 					<c:if test="${ empty sessionScope.user}"> --%>
<!-- 						<li class="menu-home"><a -->
<!-- 						onclick="delete_cookie ('city')" -->
<%-- 					href="${pageContext.request.contextPath}/login?request=${pageContext.request.requestURI}<%=HTTP_URL_Utils.encode("?"+request.getQueryString())%>"> --%>
<!-- 							 Вхід</a></li>  -->
<%-- 					</c:if> --%>
				</ul>
			</nav>
			<!-- #main-menu --> 

		</div>
		<!-- .row -->
	</div>
	<!-- .container -->
</div>
<!-- #site-header -->


<!-- Scripts -->
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script>
	// Used toselect city
	function myFunction1() {
		var input, filter, ul, li, a, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		ul = document.getElementById("myUL");
		li = ul.getElementsByTagName("a");
		for (i = 0; i < li.length; i++) {
			if (li[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
				li[i].style.display = "";
			} else {
				li[i].style.display = "none";
			}
		}
	}

	function delete_cookie(cookie_name) {
		var cookie_date = new Date(); // Текущая дата и время
		cookie_date.setTime(cookie_date.getTime() - 1);
		document.cookie = cookie_name
				+ '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';

		location.replace('${pageContext.request.contextPath}<%=toresp%>'); 
	}
	

	function delete_cook(cookie_name, lok) {
		var cookie_date = new Date(); // Текущая дата и время
		cookie_date.setTime(cookie_date.getTime() - 1);
		document.cookie = cookie_name
				+ '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';

		location.replace(lok); 
	}

		function setCookie(cname, cvalue, exdays ) {
		    var d = new Date();
		    d.setTime(d.getTime() + (exdays*24*60*60*1000));
		    var expires = "expires="+ d.toUTCString();
		    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/"; 
		}
	
		function myFunction(myUL) {
			
		    if ("action"==(myUL)) {
		    var a = document.getElementById("myUL");
		    if (a.className.indexOf("w3-show") != -1) {
		        a.className = a.className.replace(" w3-show", "");
		    }  } 
		    
		    if ("myUL"==(myUL)) {
		    var y = document.getElementById("action");
		    if(y!=null){
		    if (y.className.indexOf("w3-show") != -1) {
		        y.className = y.className.replace(" w3-show", "");
		    }  } 
		    
		    if(( (window.innerWidth > 0) ? window.innerWidth : screen.width)>1010){location.replace('${pageContext.request.contextPath}/${city_selected.getShortName()}')}}
		    
		    var x = document.getElementById(myUL);
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		        if ("myUL"==(myUL)) {
		        location.replace('${pageContext.request.contextPath}/${city_selected.getShortName()}');}
		    }
		}
 
	    $(document).click(function(event){  
	    	if(!($(event.target).is("#city-list") | $(event.target).is("#myInput" )| $(event.target).is("#times")))
	    	{ 
			    var x = document.getElementById("myUL");
			    if (x.className.indexOf("w3-show") != -1) {
			        x.className = x.className.replace(" w3-show", "");
			    } 
			    
			    var y = document.getElementById("action");

			    if(y!=null){
			    if (y.className.indexOf("w3-show") != -1) {
			        y.className = y.className.replace(" w3-show", "");
			    } }
	    	}

	    }); 
	    
 
	 
	
</script>