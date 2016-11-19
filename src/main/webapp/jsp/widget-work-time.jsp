<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>



<h3 class="entry-title" style="margin-bottom: 1ex; margin-top: 1ex;">Час
	роботи:</h3>
<c:forEach var="work" items="${item.getWork().getDate()}">
	<div style="margin-bottom: 20px; margin-left: 20px;">
		<c:choose>
			<c:when test="${work.getDateTo().equals(work.getDateFrom())}">
				<span>${work.getDateTo()}</span>
			</c:when>
			<c:when test="${work.getDateTo().equals(\"31-12-2099\")}">
				<span></span>
			</c:when>
			<c:when test="${not empty work.getDateFrom()}">
				<span>з ${work.getDateFrom()}</span>
				<span>до ${work.getDateTo()}</span>
			</c:when>
		</c:choose>
		<c:forEach var="months" items="${work.getMonths()}">
 			<c:choose>
				<c:when
					test="${months.getMonthFrom() ==1 && months.getMonthTo() ==12 }">
					<span></span>
				</c:when>

				<c:when test="${months.getMonthFrom() ==  months.getMonthTo()  }">
					<c:choose>
						<c:when test="${months.getMonthTo()==1}">
							<span> Січень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==2}">
							<span> Лютий </span>
						</c:when>
						<c:when test="${months.getMonthTo()==3}">
							<span> Березень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==4}">
							<span> Квітень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==5}">
							<span> Травень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==6}">
							<span> Червень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==7}">
							<span> Липень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==8}">
							<span> Серпень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==9}">
							<span> Вересень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==10}">
							<span> Жовтень </span>
						</c:when>
						<c:when test="${months.getMonthTo()==11}">
							<span> Листопад </span>
						</c:when>
						<c:when test="${months.getMonthTo()==12}">
							<span> Грудень </span>
						</c:when>
					</c:choose>
				</c:when>

				<c:when test="${not empty months.getMonthFrom()}"> 
					<span>з</span>
					<c:choose>
						<c:when test="${months.getMonthFrom()==1}">
							<span> Січня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==2}">
							<span> Лютого </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==3}">
							<span> Березня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==4}">
							<span> Квітня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==5}">
							<span> Травня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==6}">
							<span> Червня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==7}">
							<span> Липня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==8}">
							<span> Серпня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==9}">
							<span> Вересня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==10}">
							<span> Жовтня </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==11}">
							<span> Листопада </span>
						</c:when>
						<c:when test="${months.getMonthFrom()==12}">
							<span> Грудня </span>
						</c:when>
					</c:choose>
					<span>до</span>
					<c:choose>
						<c:when test="${months.getMonthTo()==1}">
							<span> Січня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==2}">
							<span> Лютого </span>
						</c:when>
						<c:when test="${months.getMonthTo()==3}">
							<span> Березня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==4}">
							<span> Квітня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==5}">
							<span> Травня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==6}">
							<span> Червня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==7}">
							<span> Липня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==8}">
							<span> Серпня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==9}">
							<span> Вересня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==10}">
							<span> Жовтня </span>
						</c:when>
						<c:when test="${months.getMonthTo()==11}">
							<span> Листопада </span>
						</c:when>
						<c:when test="${months.getMonthTo()==12}">
							<span> Грудня </span>
						</c:when>
					</c:choose>
				</c:when>
			</c:choose>


			<c:forEach var="days" items="${months.getDays()}">
				<div style="margin-left: 20px;">
					<c:choose>
						<c:when test="${days.getDaysTo()==7 && days.getDaysFrom()==1}">
							<span>Щоденно - </span>
						</c:when>
						<c:when test="${days.getDaysTo() ==  days.getDaysFrom()}">
							<c:choose>
								<c:when test="${days.getDaysTo()==1}">
									<span> ПН </span>
								</c:when>
								<c:when test="${days.getDaysTo()==2}">
									<span> ВТ </span>
								</c:when>
								<c:when test="${days.getDaysTo()==3}">
									<span> СР </span>
								</c:when>
								<c:when test="${days.getDaysTo()==4}">
									<span> ЧТ </span>
								</c:when>
								<c:when test="${days.getDaysTo()==5}">
									<span> ПТ </span>
								</c:when>
								<c:when test="${days.getDaysTo()==6}">
									<span> СБ </span>
								</c:when>
								<c:when test="${days.getDaysTo()==7}">
									<span> НД </span>
								</c:when>
							</c:choose>
						</c:when>
						<c:when test="${not empty days.getDaysFrom()}">
							<span>з</span>
							<c:choose>
								<c:when test="${days.getDaysFrom()==1}">
									<span> ПН </span>
								</c:when>
								<c:when test="${days.getDaysFrom()==2}">
									<span> ВТ </span>
								</c:when>
								<c:when test="${days.getDaysFrom()==3}">
									<span> СР </span>
								</c:when>
								<c:when test="${days.getDaysFrom()==4}">
									<span> ЧТ </span>
								</c:when>
								<c:when test="${days.getDaysFrom()==5}">
									<span> ПТ </span>
								</c:when>
								<c:when test="${days.getDaysFrom()==6}">
									<span> СБ </span>
								</c:when>
								<c:when test="${days.getDaysFrom()==7}">
									<span> НД </span>
								</c:when>
							</c:choose>
							<span>до</span>
							<c:choose>
								<c:when test="${days.getDaysTo()==1}">
									<span> ПН </span>
								</c:when>
								<c:when test="${days.getDaysTo()==2}">
									<span> ВТН </span>
								</c:when>
								<c:when test="${days.getDaysTo()==3}">
									<span> СР </span>
								</c:when>
								<c:when test="${days.getDaysTo()==4}">
									<span> ЧТ </span>
								</c:when>
								<c:when test="${days.getDaysTo()==5}">
									<span> ПТ </span>
								</c:when>
								<c:when test="${days.getDaysTo()==6}">
									<span> СБ </span>
								</c:when>
								<c:when test="${days.getDaysTo()==7}">
									<span> НД </span>
								</c:when>
							</c:choose>
						</c:when>

					</c:choose>
					<c:forEach var="time" items="${days.getTime()}">
<!-- 						<div style="margin-left: 20px;"> -->
							<c:choose>
								<c:when
									test="${time.getTimeFrom().equals(\"00:00\") && time.getTimeTo().equals(\"23:59\")}">
									<span>Цілодобово</span>
								</c:when>
								<c:when test="${time.getTimeTo() == time.getTimeFrom()}">
									<span>${time.getTimeTo()}</span>
								</c:when>
								<c:when test="${not empty time.getTimeFrom()}">
									<span>з ${time.getTimeFrom()}</span>
									<span>до ${time.getTimeTo()}</span>
								</c:when>
							</c:choose>
<!-- 						</div> -->
					</c:forEach>
				</div>
			</c:forEach>
		</c:forEach>
	</div>
</c:forEach>