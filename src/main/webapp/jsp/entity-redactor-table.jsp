
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>

					<c:if test="${not empty item}">

						<c:if
							test="${item.getUser().getUserId() == sessionScope.user.getUserId()}">
							<table>
								<tr>
									<td>
										<form action="${pageContext.request.contextPath}/delete"
											method="post">
											<input type="submit" value="Вилучити"> <input
												type="hidden" value="${item.getId()}" name="id">
										</form>
									</td>
									<td>
										<form action="${pageContext.request.contextPath}/update"
											method="get">
											<input type="hidden" value="${item.getId()}" name="id">
											<input type="submit" value="Редагувати">
										</form>
									</td>
								</tr>
							</table>

						</c:if>
					</c:if>