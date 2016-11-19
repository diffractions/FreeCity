<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="character_encoding_in_UTF-8.jspf"%>
<%@ include file="JSPConst.jspf"%>


<%@ page import="entity.ShowedItem"%>
<%@ page import="entity.ShowedUser"%>
<%@ page import="entity.City"%>
<%@ page import="entity.Rating"%>
<%@ page import="entity.Section"%>
<%@ page import="entity.Section"%>
<%@ page import="java.util.Calendar"%>


<html>
<head>
<%@ include file="header.jspf"%>
<title>Free City : Вхід</title>
</head>
<body class="blog" itemscope itemtype="http://schema.org/Blog">

	<div id="masthead">

		<jsp:include page="site-header.jsp" />

		<div id="page-title">
			<div class="container">
				<div class="row">

					<h1 class="entry-title" itemprop="headline" id="title">Вхід</h1>
					<p class="description" itemprop="description">Авторизуйтесь і Ви зможете додавати події та залишати відгуки</p>
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
			<div class="row">


				<div class="three-quarters-block">
					<div class="content">

						<nav id="isotope-menu">
							<ul class="horizontal-navigation">
								<li><a id="login_ref" href="javascript:void(0)"
									onclick="openCity(event, 'login')" class="tablinks"><i
										class="fa  fa-unlock fa-fw" aria-hidden="true"></i>Вхід</a></li>
								<li><a id="registration_ref" href="javascript:void(0)"
									onclick="openCity(event, 'registration')" class="tablinks"><i
										class="fa fa-user-plus fa-fw" aria-hidden="true"></i>Реєестрація</a></li>
							</ul>
						</nav>


						<div class="entry-content">
							<div class="contact-form">
								<form id="login" class="tabcontent tabs"
									action="login?request=<%=request.getParameter("request")%> "
									method="post">


									<c:if test="${not empty errorLoginString}">
										<div class="box error-box">${errorLoginString}</div>
									</c:if>

									<input placeholder="Login" name="login" type="text"
										id="inputLogin"> <input placeholder="Password"
										name="password" type="password" id="inputPassword"> <input
										class="button" name="submit" id="login_in" type="submit"
										value="Вхід" >
								</form>
								<form id="registration" class="tabcontent tabs" action="user"
									method="post">

									<c:if test="${not empty errorRegistrationString}">
										<div class="box error-box">${errorRegistrationString}</div>
										<% session.removeAttribute("errorRegistrationString"); %>
									</c:if>


									<span style="float: right; color: red; "><i class="fa fa-info-circle" aria-hidden="true"></i> <sup>*</sup> - Обов'язкові поля</span>
									<div class="tooltip"
										style="width: 100%; height: inherit; border: inherit;">
										<span class="tooltiptext">Логін має бути латиницею і більше 4
											символів</span> <input placeholder="* Login" type="text" id="logs"
											name="login">
									</div>
									<input placeholder="* @mail" id="email" type="text"
										name="email" onkeyup="isEmail()"> <input
										placeholder="* First name" type="text" name="first_name"
										id="fname"><input placeholder="Last name" type="text"
										name="last_name" id="lname">

									<div class="tooltip"
										style="width: 100%; height: inherit; border: inherit;">
										<span class="tooltiptext">Пароль має бути більше 6
											символів і містити лише латинські символи і цифри</span> <input placeholder="* Password" type="password"
											name="password" id="password">
									</div>
									<div class="tooltip"
										style="width: 100%; height: inherit; border: inherit;">
										<span class="tooltiptext">Підтвердіть пароль</span> <input
											placeholder="* Сonfirm password" type="password"
											name="confirm_password" id="confirm_password" />
									</div>
									<input id="registration_in"  type="submit"
										value="Реєестрація" >
								</form>



							</div>
						</div>
						<!-- .content -->
						
									<span style="float: right; color: red;"><i class="fa fa-info-circle" aria-hidden="true"></i> При помилці входу або реєстрації звертайтеся до адміністратора ${AdminMail} </span>
					</div>

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
	<script>
	
	
	
    $('#login > input').keyup(function() {

        var empty = false;
        $('#login > input').each(function() {
            if ($(this).val() == '') {
                empty = true;
            }
        });

        if (empty) {
            $('#login_in').attr('disabled', 'disabled'); 
        } else {
            $('#login_in').removeAttr('disabled'); 
        }
   		 });
	
	 
    


	    $('#registration > input, #registration > div > input').on('keyup',function() {
	
	        var empty = false;
			var logo = /^[A-Za-z0-9]\w{3,14}$/;
			if (!$('#logs').val().match(logo)) {
				empty = true; 
	        } 
	
			var fnames = /[A-Za-zа-яА-ЯІі]\w{0,14}$/;
			if (!$('#fname').val().match(fnames)) {
				empty = true; 
			}
			
	
			var passw = /[A-Za-z0-9]\w{5,14}$/;
			if (!$('#password').val().match(passw)) {
	            empty = true; 
	        }   
			
			if ($('#password').val() != $('#confirm_password').val()) { 
		        empty = true; 
		    } 
	
	
	        
			var str = document.getElementById("email").value;
			var re = /^[^\s()<>@,;:\/]+@[\w\.-]+\.[a-z]{2,}$/i;
			if (!re.test(str)){
				empty = true; 
			}

        if (empty) {
            $('#registration_in').attr('disabled', 'disabled');
			$('#registration_in').attr('title', 'Будь-ласка, заповніть всі обов\'язкові поля');
        } else {
            $('#registration_in').removeAttr('disabled'); 
			$('#registration_in').attr('title', 'Зареєструватися в системі');
        }
  		  });
    
    
    
		$('#logs').on('keyup', function() {
			var passw = /^[A-Za-z0-9]\w{3,14}$/;
			if ($('#logs').val().match(passw)) {
				$('#logs').css({
					"border-color" : "#C1E0FF",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
			} else
				$('#logs').css({
					"border-color" : "red",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
		});
		
		
		$('#fname').on('keyup', function() {
			var passw = /[A-Za-zа-яА-ЯІі]\w{0,14}$/;
			if ($('#fname').val().match(passw)) {
				$('#fname').css({
					"border-color" : "#C1E0FF",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
			} else
				$('#fname').css({
					"border-color" : "red",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
		});
		
		$('#password').on('keyup', function() {
			var passw = /[A-Za-z0-9]\w{5,14}$/;
			if ($('#password').val().match(passw)) {
				$('#password').css({
					"border-color" : "#C1E0FF",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
			} else
				$('#password').css({
					"border-color" : "red",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
		});

		$('#password, #confirm_password').on('keyup', function() {
			if ($('#password').val() == $('#confirm_password').val()) {
				$('#confirm_password').css({
					"border-color" : "#C1E0FF",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
			} else
				$('#confirm_password').css({
					"border-color" : "red",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
		});

		function isEmail() {
			var str = document.getElementById("email").value;
			var re = /^[^\s()<>@,;:\/]+@[\w\.-]+\.[a-z]{2,}$/i;
			if (re.test(str))
				$('#email').css({
					"border-color" : "#C1E0FF",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
			else
				$('#email').css({
					"border-color" : "red",
					"border-weight" : "1px",
					"border-style" : "solid"
				});
		}
		
		function isEmpty(str) {
			return (str == null) || (str.length == 0);
		}

		$(window)
				.load(
						function(e) {
							$('#registration_in').attr('disabled', 'disabled');
							$('#registration_in').attr('title', 'Будь-ласка, заповніть всі обов\'язкові поля');
							$('#login_in').attr('disabled', 'disabled');
							
							if (window.location.hash == "") {
								window.scrollTo(0, 0);
								setTimeout(function() {
									window.scrollTo(0, 0);
								}, 1);
								
								document.getElementById("login").style.display = "block";
							} else {
								document.getElementById(window.location.hash.substring(1)).style.display = "block";
							}
						});
		$('#login_ref').click(function(e) {
			e.preventDefault();
			history.pushState({}, null, "#login");
		});
		$('#registration_ref').click(function(e) {
			e.preventDefault();
			history.pushState({}, null, "#registration");
		});
	</script>


</body>
</html>
</body>