
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty ch_menus.childSections}"> 
	<c:set var="s_menu" value="${ch_menus}" scope="request" />
	<li><jsp:include page="section-show-changed-with-city.jsp" /></li>
</c:if>
 
<c:if test="${not empty ch_menus.childSections}"> 
	<c:set var="s_menu" value="${ch_menus}" scope="request" />
	<li><jsp:include page="section-show-changed-with-city.jsp" />
		<ul class="vertical-navigation-right">
			<c:forEach var="new_ch_menu" items="${ch_menus.childSections}">
				<c:set var="ch_menus" value="${new_ch_menu}" scope="request" />
				<jsp:include page="section-header-section-childs.jsp" />
			</c:forEach>
		</ul></li>
</c:if>

<!--  class="fa fa-angle-left fa-fw" -->


