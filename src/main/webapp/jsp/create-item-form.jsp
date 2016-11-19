<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Rating"%>
<%@ page import="entity.Section"%> 
<%@ page import="entity.Adress"%>
<%@ page import="java.util.Calendar"%>



<form <c:if test="${ empty item}">action="create" </c:if> <c:if test="${not empty item}">action="update?id=${item.getId()}" </c:if> method="post" enctype="multipart/form-data"> <fieldset>

 
										<span style="float: right; color: red; "><i class="fa fa-info-circle" aria-hidden="true"></i> <sup>*</sup> - Обов'язкові поля</span>



	<c:if test="${not empty item}">
<%-- 		<input type="hidden" name="id" value="${item.getId()}"> --%>
		<input type="hidden" name="c_header" value="${item.getHeader()}">
		<input type="hidden" name="c_description" value="${item.getDescription()}">
		<input type="hidden" name="c_city-id" value="${item.getCities().get(0).getId()}">
		<input type="hidden" name="c_map-link-lat" value="<c:if test="${not empty item.getMaps()}">${item.getMaps().get(0).getLat()}</c:if>">
		<input type="hidden" name="c_map-link-lng" value="<c:if test="${not empty item.getMaps()}">${item.getMaps().get(0).getLng()}</c:if>">
		<input type="hidden" name="c_url-link" value="${item.getURLs().get(0)}">
		<input type="hidden" name="c_section-id" value="${item.getSections().get(0).getSectionId()}">
		
			<input type="hidden" name="c_adress-link" value="<c:if test="${not empty item.getAdress()}">${item.getAdress().get(0).getAdress()}</c:if>">
			<input type="hidden" name="c_contacts-link" value="<c:if test="${not empty item.getAdress()}">${item.getAdress().get(0).getContacts()}</c:if>"> 
		
		<input type="hidden" name="c_date-from" value="${item.getWork().getDate().get(0).getDateFrom()}">
		<input type="hidden" name="c_date-to" value="${item.getWork().getDate().get(0).getDateTo()}">
		<input type="hidden" name="c_month-from" value="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()}">
		<input type="hidden" name="c_month-to" value="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()}">
		<input type="hidden" name="c_days-from" value="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()}">
		<input type="hidden" name="c_days-to" value="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()}">
		<input type="hidden" name= "c_time-from" value="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getTime().get(0).getTimeFrom()}">
		<input type="hidden" name="c_time-to" value="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getTime().get(0).getTimeTo()}">
		<input type="hidden" name="c_photo" value="${item.getImagName()}">
	</c:if>

		<span style=" color: red; ">*</span> Назва: <br> <input type="text" name="header"
			value="${item.getHeader()}" id="it-head"> <br> Опис : <br>
		<textarea name="description" rows="10">${item.getDescription()}</textarea>
		<br><span style=" color: red; ">*</span>Розділ : <br>
		<jsp:include page="create-item-form-section.jsp" />
		
		
		<br> <span style=" color: red; ">*</span>Місто : <br> <select name="city-id" id="city-id" title="Виберіть місто зі списку, та встановіть позначку на карті">
			<c:forEach var="city" items="${cities}">
				<option value="${city.getId()}"
					<c:if test="${item.getCities().get(0).getId()==city.getId()}">selected="selected"</c:if>>${city.getName()}</option>
			</c:forEach>
			<c:forEach var="city" items="${cities}">
				<input type="hidden" value="${city.getLat()}"
					id="lat-city-${city.getId()}" />
				<input type="hidden" value="${city.getLng()}"
					id="lng-city-${city.getId()}" />
			</c:forEach>
		</select> <br> Карта : <br>
		
		
		<input id="centerlat" type="hidden"
			<c:if test="${not empty item && not empty  item.getMaps()}">value="${item.getMaps().get(0).getLat()}"</c:if>
			<c:if test="${empty item}">value="50.4504434"</c:if>>
		<input id="centerlng" type="hidden"
			<c:if test="${not empty item && not empty  item.getMaps()}">value="${item.getMaps().get(0).getLng()}"</c:if>
			<c:if test="${empty item}">value="30.523386"</c:if>> 
		<input id="map-link-lat" name="map-link-lat" type="hidden">
		<input id="map-link-lng" name="map-link-lng" type="hidden">

		<div>
			<div id="map"></div>
			<script type="text/javascript"
				src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDdHypi0QFDrPvzWoSskDcCpdq5lGwzQ-4&language=ua&signed_in=true"></script>
			<script type="text/javascript" src="js/map1.js"></script>
		</div>


		<c:if test="${not empty item && not empty  item.getMaps()}">
			<script>window.onload = function () { placeMarker(new google.maps.LatLng(${item.getMaps().get(0).getLat()}, ${item.getMaps().get(0).getLng()}))}</script>
		</c:if>






		<br> <span style=" color: red; ">*</span>Адреса : <br>
		<div  style="display: flex;">
			<input type="hidden" value="" id="map-adress-link"> <input 
				type="text" name="adress-link" id="adress-link" style="flex: 1;"
				value="<c:if test="${not empty item.getAdress() &&  0!=item.getAdress().size()}">${item.getAdress().get(0).getAdress()}</c:if>" />
			<a class="button" style="padding: 16px; padding: 1rem;"
				title="Натисніть, щоб прочитати адресу мітки на карті" onclick="loadAdrr() ">Адреса
				з карти</a>
		</div>


		<br> Контакти : <br>
			<input type="text" name="contacts-link" id="contacts-link"
				value="<c:if test="${not empty item.getAdress() && 0!=item.getAdress().size()}">${item.getAdress().get(0).getContacts()}</c:if>" />
 
		<br> Посилання : <br> <input type="text" name="url-link"
			value="<c:if test="${0!=item.getURLs().size()}">${item.getURLs().get(0)}</c:if>">

<br> Час роботи : <br>
		<table id="time">
			<tr>
				<th>З</th>
				<th>ДО</th>
			</tr>

			<tr>
				<td><input name="date-from" id="date-from" type="date"
					<c:if test="${not empty item}">value="${item.getWork().getDate().get(0).getDateFrom().substring(6)}-${item.getWork().getDate().get(0).getDateFrom().substring(3,5)}-${item.getWork().getDate().get(0).getDateFrom().substring(0,2)}"</c:if>></td>
				<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
				<script type="text/javascript" src="js/custom.js"></script>
				<td><input name="date-to" id="date-to" type="date"
					<c:if test="${not empty item}">value="${item.getWork().getDate().get(0).getDateTo().substring(6)}-${item.getWork().getDate().get(0).getDateTo().substring(3,5)}-${item.getWork().getDate().get(0).getDateTo().substring(0,2)}"</c:if>
					<c:if test="${empty item}">value="2099-12-31"</c:if>></td>
			</tr>




			<tr>
				<td><select name="month-from" id="month-from">
						<option value="1"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==1}">selected="selected"</c:if>
							<c:if test="${empty item}">selected="selected"</c:if>>Січень</option>
						<option value="2"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==2}">selected="selected"</c:if>>Лютий</option>
						<option value="3"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==3}">selected="selected"</c:if>>Березень</option>
						<option value="4"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==4}">selected="selected"</c:if>>Квітень</option>
						<option value="5"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==5}">selected="selected"</c:if>>Травеь</option>
						<option value="6"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==6}">selected="selected"</c:if>>Червеь</option>
						<option value="7"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==7}">selected="selected"</c:if>>Липень</option>
						<option value="8"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==8}">selected="selected"</c:if>>Серпень</option>
						<option value="9"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==9}">selected="selected"</c:if>>Вересень</option>
						<option value="10"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==10}">selected="selected"</c:if>>Жовтень</option>
						<option value="11"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==11}">selected="selected"</c:if>>Листопад</option>
						<option value="12"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthFrom()==12}">selected="selected"</c:if>>Грудень</option>
				</select></td>
				<td><select name="month-to" id="month-to">
						<option value="1"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==1}">selected="selected"</c:if>>Січень</option>
						<option value="2"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==2}">selected="selected"</c:if>>Лютий</option>
						<option value="3"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==3}">selected="selected"</c:if>>Березень</option>
						<option value="4"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==4}">selected="selected"</c:if>>Квітень</option>
						<option value="5"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==5}">selected="selected"</c:if>>Травеь</option>
						<option value="6"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==6}">selected="selected"</c:if>>Червеь</option>
						<option value="7"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==7}">selected="selected"</c:if>>Липень</option>
						<option value="8"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==8}">selected="selected"</c:if>>Серпень</option>
						<option value="9"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==9}">selected="selected"</c:if>>Вересень</option>
						<option value="10"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==10}">selected="selected"</c:if>>Жовтень</option>
						<option value="11"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==11}">selected="selected"</c:if>>Листопад</option>
						<option value="12"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getMonthTo()==12}">selected="selected"</c:if>
							<c:if test="${empty item}">selected="selected"</c:if>>Грудень</option>
				</select></td>
			</tr>
			<tr>


				<td><select name="days-from" id="days-from">
						<option value="1"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()==1}">selected="selected"</c:if>
							<c:if test="${empty item}">selected="selected"</c:if>>ПН</option>
						<option value="2"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()==2}">selected="selected"</c:if>>ВТ</option>
						<option value="3"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()==3}">selected="selected"</c:if>>СР</option>
						<option value="4"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()==4}">selected="selected"</c:if>>ЧТ</option>
						<option value="5"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()==5}">selected="selected"</c:if>>ПТ</option>
						<option value="6"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()==6}">selected="selected"</c:if>>СБ</option>
						<option value="7"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysFrom()==7}">selected="selected"</c:if>>НД</option>
				</select></td>
				<td><select name="days-to" id="days-to">
						<option value="1"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()==1}">selected="selected"</c:if>>ПН</option>
						<option value="2"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()==2}">selected="selected"</c:if>>ВТ</option>
						<option value="3"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()==3}">selected="selected"</c:if>>СР</option>
						<option value="4"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()==4}">selected="selected"</c:if>>ЧТ</option>
						<option value="5"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()==5}">selected="selected"</c:if>>ПТ</option>
						<option value="6"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()==6}">selected="selected"</c:if>>СБ</option>
						<option value="7"
							<c:if test="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getDaysTo()==7}">selected="selected"</c:if>
							<c:if test="${empty item}">selected="selected"</c:if>>НД</option>
				</select></td>
			</tr>


			<tr>
				<td><input name="time-from" id="time-from" type="time"
					<c:if test="${empty item}">value="00:00"</c:if>
					<c:if test="${not empty item}">value="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getTime().get(0).getTimeFrom()}"</c:if>></td>
				<td><input type="time" name="time-to" id="time-to"
					<c:if test="${empty item}">value="23:59"</c:if>
					<c:if test="${not empty item}">value="${item.getWork().getDate().get(0).getMonths().get(0).getDays().get(0).getTime().get(0).getTimeTo()}"</c:if>></td>
			</tr>
		</table>






 
			<br>Зображення:<br>
			<input type="file" name="photo" size="50" />  


		<c:if test="${not empty item && not empty item.getImagName()}"> 
			<div class="entry-image">
				<img
					src="${pageContext.request.contextPath}/user/img/${item.getImagName()}"
					alt="${item.getImagName()}" itemprop="image">
			</div>
		</c:if>






<br>

		<c:if test="${ empty item}">
			<input type="hidden" value="simple" name="type">
			<input type="submit" id="create-sub" value="Створити">
		</c:if>

		<c:if test="${not empty item}">
			<input type="submit" value="Обновити">
		</c:if>



	</fieldset>
</form>

<c:if test="${not empty item}">
	<form action="${pageContext.request.contextPath}/delete" method="post">
		<input type="submit" value="Вилучити"> <input type="hidden"
			value="${item.getId()}" name="id">
	</form> 
	<a class="button" href="${pageContext.request.contextPath}/${item.getCities().get(0).getShortName()}/entity?id=${item.getId()}">Перейти до запису</a>
</c:if>


<c:if test="${empty item}">
<script>	
$(window) .load( function(e) {
	 		$('#create-sub').attr('disabled', 'disabled');
 });
 
 
$('#it-head, #adress-link').on('keyup', function() {
	if ($('#it-head').val().length==0 || $('#adress-link').val().length==0 )  {
        $('#create-sub').attr('disabled', 'disabled'); 
    } else {
        $('#create-sub').removeAttr('disabled');  
    }
});

function loadAdrr() {
	document.getElementById('adress-link').value= 	  document.getElementById('map-adress-link').value; 
	$('#adress-link').trigger('keyup');
	}
 </script>
</c:if>