<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>



<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDdHypi0QFDrPvzWoSskDcCpdq5lGwzQ-4&sensor=false"></script>
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>



<!-- <input id="centerlat" type="hidden" value="50.4500796"> -->
<!-- <input id="centerlng" type="hidden" value="30.5233478"> -->
<!-- <input id="mapzoom" type="hidden" value="11.8"> -->


<c:if test="${not empty city_selected}">
	<input id="centerlat" type="hidden" value="${city_selected.lat}">
	<input id="centerlng" type="hidden" value="${city_selected.lng}">
	<input id="mapzoom" type="hidden" value="${city_selected.getZoom()}">
</c:if>
<c:if test="${empty city_selected}">
	<input id="centerlat" type="hidden" value="50.4500796">
	<input id="centerlng" type="hidden" value="30.5233478">
	<input id="mapzoom" type="hidden" value="12">
</c:if>


<script type="text/javascript" src="js/map2.js"></script>

<script type="text/javascript">
					function codeAddress() { 
						<c:forEach var="item" items="${items}">  

						<c:if test="${item.getStatus() >=chowedItemStatus || (item.getStatus()>=chowedItemUserCreateStatus && item.getUser().getUserId()==sessionScope.user.getUserId())}"> 
							<c:if test="${(item.getSections().get(0).getSectionView() < 10) || (item.getSections().get(0).getSectionView() == 10 && section_view==10)}">
								<c:forEach var="map" items="${item.getMaps()}">  
										<c:if test="${not empty map}"> 
											add(${map.getLat()},${map.getLng()}, '${item.getHeader()}', '<div><h2><a href="<%@ include file="href-path.jspf"%>entity?id=${item.getId()}">' + '${item.getHeader()}' + '</a></h2><p>'+'${item.getAdress().get(0).getAdress()}'+'</p></div>');
										</c:if>
								</c:forEach> 
								</c:if>
								</c:if>
								
						</c:forEach>
					}
					window.onload = codeAddress; 
				</script>


<div id="map" class="tabcontent tabs"></div>
<script> 
		$(window).load(function(e){  
			if (window.location.hash == "") {
				window.scrollTo(0, 0); 
				  setTimeout(function() { 
				    window.scrollTo(0, 0);
				  }, 1);
				document.getElementById("list1").style.display = "block";
			}else{
				document.getElementById(window.location.hash.substring(1)).style.display = "block";
			} 
		}); 
		$('#list_ref').click(function(e) {  
			  e.preventDefault();
			  history.pushState({}, null, "#list1");
			}); 
		$('#map_ref').click(function(e) {   
			  e.preventDefault();
			  history.pushState({}, null, "#map");
			});
</script>