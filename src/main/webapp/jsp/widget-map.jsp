
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.Map"%>



				
<input id="centerlat" type="hidden" value=" ${item.getMaps().get(0).getLat()}">
<input id="centerlng" type="hidden" value=" ${item.getMaps().get(0).getLng()}">
<input id="mapzoom" type="hidden" value="16">

<!-- <input id="centerlat" type="hidden" value="40.6700"> -->
<!-- <input id="centerlng" type="hidden" value="-75.9400"> -->
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDdHypi0QFDrPvzWoSskDcCpdq5lGwzQ-4&sensor=false"></script>
<script type="text/javascript" src="js/map2.js"></script>
<div id="map"></div>

<script type="text/javascript">
					function codeAddress() {
						<c:forEach var="map" items="${item.getMaps()}"> 
									add(${map.getLat()},${map.getLng()}, '${item.getHeader()}', '<div><h2><a href="entity?id=${item.getId()}">' + '${item.getHeader()}' + '</a></h2><p>'+'${item.getAdress().get(0).getAdress()}'+'</p></div>');
						</c:forEach>
					}
					window.onload = codeAddress;
				</script>
				 