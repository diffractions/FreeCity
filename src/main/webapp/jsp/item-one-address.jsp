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
<%@ page import="entity.Adress"%>
<%@ page import="java.util.Calendar"%>


<c:if test="${1 < item.getAdress().size()}">
	<table style="width: 100%; margin-bottom: 48px; margin-bottom: 3rem;">
		<thead>
			<tr>
				<th style="width: 50%"><h3 class="entry-title"
						style="margin-bottom: 1ex; margin-top: 1ex;" align="left">Адреса</h3></th>
				<th style="width: 50%"><h3 class="entry-title"
						style="margin-bottom: 1ex; margin-top: 1ex;" align="left">Контакти</h3>
				<th>
			</tr>
		</thead>
		<c:forEach var="adres" items="${item.getAdress()}">
			<tr>
				<td>${adres.getAdress()}</td>
				<td>${adres.getContacts()}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${1 == item.getAdress().size()}">
	<c:if test="${0 != item.getAdress().get(0).getAdress().length()}">
		<h3 class="entry-title" style="margin-bottom: 1ex; margin-top: 1ex;"
			align="left">Адреса:</h3>
		<div style="margin-bottom: 20px; margin-left: 20px;">${item.getAdress().get(0).getAdress()}</div>
	</c:if>
	<c:if test="${0 != item.getAdress().get(0).getContacts().length()}">
		<h3 class="entry-title" style="margin-bottom: 1ex; margin-top: 1ex;"
			align="left">Контакти:</h3>
		<div style="margin-bottom: 20px; margin-left: 20px;">${item.getAdress().get(0).getContacts()}</div>
	</c:if>
</c:if>