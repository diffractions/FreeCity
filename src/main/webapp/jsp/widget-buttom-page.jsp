<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%-- 	pageEncoding="UTF-8"%> --%>
<%-- <%@ include file="character_encoding_in_UTF-8.jspf"%> --%>

<%-- <%@ page import="entity.ShowedItem"%> --%>
<%-- <%@ page import="entity.ShowedUser"%> --%>
<%-- <%@ page import="entity.City"%> --%>
<%-- <%@ page import="entity.Section"%> --%>
<%-- <%@ page import="java.util.Calendar"%> --%>


<!-- <div id="prefooter" role="complementary"> -->
<!-- 	<div class="container"> -->
<!-- 		<div class="row"> -->

<!-- 			<div class="one-third-block"> -->

<!-- 				<div class="contact-widget widget" itemscope="" -->
<!-- 					itemtype="http://schema.org/LocalBusiness"> -->

<!-- 					<div class="name" itemprop="name"> -->
<!-- 						<strong>Canvas</strong> -->
<!-- 					</div> -->
<!-- 					.name -->

<!-- 					<div class="description" itemprop="description">Lorem ipsum -->
<!-- 						dolor sit amet, orci a faucibus, ante curabitur augue ornare nulla -->
<!-- 						facilisi bibendum</div> -->

<!-- 					<div class="address" itemprop="address" itemscope="" -->
<!-- 						itemtype="http://schema.org/PostalAddress"> -->
<!-- 						<div class="street" itemprop="streetAddress">Street</div> -->
<!-- 						<div class="city" itemprop="addressLocality">City</div> -->
<!-- 						<div class="region" itemprop="addressRegion">State / Region</div> -->
<!-- 						<div class="postcode" itemprop="postalCode">Zip / Post Code</div> -->
<!-- 						<div class="country" itemprop="addressCountry">Country</div> -->
<!-- 					</div> -->
<!-- 					.address -->

<!-- 					<div class="telephone"> -->
<!-- 						<i class="fa fa-phone fa-fw"></i> <a href="tel:08007654321" -->
<!-- 							title="Call us" itemprop="telephone">0800 765 4321</a> -->
<!-- 					</div> -->
<!-- 					<div class="email-address"> -->
<!-- 						<i class="fa fa-envelope fa-fw"></i> <a href="contact.html" -->
<!-- 							title="Email us" itemprop="email">you@yourdomain.com</a> -->
<!-- 					</div> -->
<!-- 					<div class="website"> -->
<!-- 						<i class="fa fa-globe fa-fw"></i> <a -->
<!-- 							href="http://www.yourdomain.com" title="Visit our website" -->
<!-- 							itemprop="url">www.yourdomain.com</a> -->
<!-- 					</div> -->

<!-- 					<div class="rating" itemprop="aggregateRating" itemscope -->
<!-- 						itemtype="http://schema.org/AggregateRating"> -->

<!-- 						<div class="rating-stars"> -->
<!-- 							<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i -->
<!-- 								class="fa fa-star"></i> <i class="fa fa-star"></i> <i -->
<!-- 								class="fa fa-star-half"></i> -->
<!-- 						</div> -->
<!-- 						<span itemprop="ratingValue">4.6</span> stars based on <span -->
<!-- 							itemprop="reviewCount">99</span> reviews -->

<!-- 					</div> -->
<!-- 					.rating -->

<!-- 				</div> -->
<!-- 				.contact-widget -->

<!-- 			</div> -->
<!-- 			<!-- .one-third-block --> -->

<!-- 			<div class="one-third-block"> -->

<!-- 				<div class="recent-posts-widget widget"> -->
<!-- 					<h4 class="widget-title">Recent Posts</h4> -->
<!-- 					<ul> -->
<!-- 						<li class="recent-post"><a href="article.html" -->
<!-- 							title="Lorem ipsum dolar" rel="bookmark"> <span -->
<!-- 								class="recent-post-title"> Lorem ipsum dolar </span> <span -->
<!-- 								class="recent-post-thumbnail"> <img -->
<!-- 									src="http://dummyimage.com/80x80/f3f3f3/999999.jpg&text=Thumbnail" -->
<!-- 									alt="Thumbnail"> -->
<!-- 							</span> <span class="recent-post-summary"> -->
<!-- 									<p>Integer rutrum ligula non purus sit amet gravida neque -->
<!-- 										vulputate.</p> -->
<!-- 							</span> -->
<!-- 						</a></li> -->
<!-- 						.recent-post -->
<!-- 						<li class="recent-post"><a href="article.html" -->
<!-- 							title="Lorem ipsum dolar" rel="bookmark"> <span -->
<!-- 								class="recent-post-title"> Lorem ipsum dolar </span> <span -->
<!-- 								class="recent-post-thumbnail"> <img -->
<!-- 									src="http://dummyimage.com/80x80/f3f3f3/999999.jpg&text=Thumbnail" -->
<!-- 									alt="Thumbnail"> -->
<!-- 							</span> <span class="recent-post-summary"> -->
<!-- 									<p>Integer rutrum ligula non purus sit amet gravida neque -->
<!-- 										vulputate.</p> -->
<!-- 							</span> -->
<!-- 						</a></li> -->
<!-- 						.recent-post -->
<!-- 						<li class="recent-post"><a href="article.html" -->
<!-- 							title="Lorem ipsum dolar" rel="bookmark"> <span -->
<!-- 								class="recent-post-title"> Lorem ipsum dolar </span> <span -->
<!-- 								class="recent-post-thumbnail"> <img -->
<!-- 									src="http://dummyimage.com/80x80/f3f3f3/999999.jpg&text=Thumbnail" -->
<!-- 									alt="Thumbnail"> -->
<!-- 							</span> <span class="recent-post-summary"> -->
<!-- 									<p>Integer rutrum ligula non purus sit amet gravida neque -->
<!-- 										vulputate.</p> -->
<!-- 							</span> -->
<!-- 						</a></li> -->
<!-- 						.recent-post -->
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 				.recent-posts-widget -->

<!-- 			</div> -->
<!-- 			<!-- .one-third-block --> -->

<!-- 			<div class="one-third-block"> -->

<!-- 				<div class="connect-widget widget"> -->
<!-- 					<div class="mailing-list"> -->
<!-- 						<h4 class="widget-title">Join our Mailing List</h4> -->
<!-- 						<p>Lorem ipsum dolor sit amet, orci a faucibus, ante curabitur -->
<!-- 							augue</p> -->
<!-- 						<form class="widget-form" action="#" method="get"> -->
<!-- 							<label> <span class="screen-reader-text">Join our -->
<!-- 									mailing list for news and exclusive offers:</span> <input type="email" -->
<!-- 								class="email-field" placeholder="Enter your email address" -->
<!-- 								value="" name="email" title="Enter your email address" /> -->
<!-- 							</label> -->
<!-- 							<button> -->
<!-- 								<i class="fa fa-angle-right"></i> -->
<!-- 							</button> -->
<!-- 						</form> -->
<!-- 					</div> -->
<!-- 					.mailing-list -->
<!-- 					<div class="social-icons"> -->
<!-- 						<h4 class="widget-title">Connect with us</h4> -->
<!-- 						<p>Lorem ipsum dolor sit amet, orci a faucibus, ante curabitur -->
<!-- 							augue ornare nulla facilisi</p> -->
<!-- 						<ul class="horizontal-navigation"> -->
<!-- 							<li class="facebook"><a href="#" title="Facebook" -->
<!-- 								target="_blank"><i class="fa fa-facebook fa-2x fa-fw"></i></a></li> -->
<!-- 							<li class="twitter"><a href="#" title="Twitter" -->
<!-- 								target="_blank"><i class="fa fa-twitter fa-2x fa-fw"></i></a></li> -->
<!-- 							<li class="google-plus"><a href="#" title="Google+" -->
<!-- 								target="_blank"><i class="fa fa-google-plus fa-2x fa-fw"></i></a></li> -->
<!-- 							<li class="pinterest"><a href="#" title="Pinterest" -->
<!-- 								target="_blank"><i class="fa fa-pinterest fa-2x fa-fw"></i></a></li> -->
<!-- 							<li class="instagram"><a href="#" title="Instagram" -->
<!-- 								target="_blank"><i class="fa fa-instagram fa-2x fa-fw"></i></a></li> -->
<!-- 							<li class="youtube"><a href="#" title="YouTube" -->
<!-- 								target="_blank"><i class="fa fa-youtube fa-2x fa-fw"></i></a></li> -->
<!-- 						</ul> -->
<!-- 						.horizontal-navigation -->
<!-- 					</div> -->
<!-- 					.social-icons -->
<!-- 				</div> -->
<!-- 				.connect-widget -->

<!-- 			</div> -->
<!-- 			<!-- .one-third-block --> -->

<!-- 		</div> -->
<!-- 		<!-- .row --> -->
<!-- 	</div> -->
<!-- 	<!-- .container --> -->
<!-- </div> -->
<!-- <!-- #prefooter --> -->