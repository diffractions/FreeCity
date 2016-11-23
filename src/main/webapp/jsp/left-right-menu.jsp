

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>
<%@ include file="JSPConst.jspf"%>

<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>

<c:if test="${items_count >pageCount}">
<div class="pagination">
	<c:if test="${not empty showedSection}">
		</a>
		<c:if test="${empty page || page==0 }">
			</a>
			<a class="prev button"
				style="background: rgb(0, 0, 0); background: rgba(0, 0, 0, 0.15);">
				<i class="fa fa-chevron-left"></i> Попередня сторінка
			</a>
		</c:if>
		<c:if test="${page==1}">
			</a>
			<a class="prev button" href="<%@ include file="href-path.jspf"%>list?section=${showedSection}"> <i
				class="fa fa-chevron-left"></i> Попередня сторінка
			</a>
		</c:if>
		<c:if test="${page>1}">
			<a class="prev button"
				href="<%@ include file="href-path.jspf"%>list?section=${showedSection}&page=${page-1}"> <i
				class="fa fa-chevron-left"></i> Попередня сторінка
			</a>
		</c:if>  
			<c:if test="${((page+1)*pageCount) <= items_count }">
				<a class="next button"
					href="<%@ include file="href-path.jspf"%>list?section=${showedSection}&page=${page+1}">Наступна
					сторінка<i class="fa fa-chevron-right"></i>
				</a>
			</c:if>
			 
			
			<c:if test="${((page+1)*pageCount) > items_count }">
				</a>
				<a class="next button"
					style="background: rgb(0, 0, 0); background: rgba(0, 0, 0, 0.15);">
					<i class="fa fa-chevron-right"></i> Наступна сторінка
				</a>
			</c:if>
		</c:if>
	<c:if test="${empty showedSection}">
		<c:if test="${empty page || page==0 }">
			</a>
			<a class="prev button"
				style="background: rgb(0, 0, 0); background: rgba(0, 0, 0, 0.15);">
				<i class="fa fa-chevron-left"></i> Попередня сторінка
			</a>
		</c:if>
		<c:if test="${page==1}">
			</a>
			<a class="prev button" href="<%@ include file="href-path.jspf"%>list"> <i class="fa fa-chevron-left"></i>
				Попередня сторінка
			</a>
		</c:if>
			<c:if test="${((page+1)*pageCount) <= items_count }">
		<a class="next button" href="<%@ include file="href-path.jspf"%>list?page=${page+1}">Наступна
			сторінка<i class="fa fa-chevron-right"></i>
		</a></c:if>
			<c:if test="${((page+1)*pageCount) > items_count }">
			</a>
			<a class="next button"
				style="background: rgb(0, 0, 0); background: rgba(0, 0, 0, 0.15);">
				<i class="fa fa-chevron-right"></i> Наступна
			сторінка
			</a>
		</c:if>
	</c:if>
</div>
<!-- .pagination -->
</c:if>