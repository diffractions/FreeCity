
	<a class="prev button" onclick="myFunction11()">Додати</a> <input
		type="submit">

	<script>
								var count = 0;
								function myFunction11() {
									count += 1;

									var x = document.createElement("TH");
									x.setAttribute("id", "myTr" + count);
									x.setAttribute("name", "myTr" + count);
									document.getElementById("time")
											.appendChild(x);

									var y = document.createElement("TD");
									y.colSpan = "2";
									var t1 = document
											.createTextNode("ADD new work time information");
									y.appendChild(t1);
									document.getElementById("myTr" + count)
											.appendChild(y);

									var x1 = document.createElement("TR");
									x1.setAttribute("id", "myTr1" + count);
									x1.setAttribute("name", "myTr1" + count);
									document.getElementById("time")
											.appendChild(x1);

									// 						var y1 = document.createElement("TD");
									// 						var t0 = document.createTextNode("Days of your event:");
									// 						y1.appendChild(t0);
									// 						document.getElementById("myTr1" + count)
									// 								.appendChild(y1);

									var y2 = document.createElement("TD");
									var t1 = document.createElement("INPUT");
									t1.setAttribute("id", "t-date-from-"
											+ count);
									t1.setAttribute("name", "t-date-from-"
											+ count);
									t1.setAttribute("type", "date");
									y2.appendChild(t1);
									document.getElementById("myTr1" + count)
											.appendChild(y2);

									var y3 = document.createElement("TD");
									var t2 = document.createElement("INPUT");
									t2.setAttribute("id", "t-date-to-" + count);
									t2.setAttribute("name", "t-date-to-"
											+ count);
									t2.setAttribute("type", "date");
									y3.appendChild(t2);
									document.getElementById("myTr1" + count)
											.appendChild(y3);

									var x13 = document.createElement("TR");
									x13.setAttribute("id", "myTr3" + count);
									x13.setAttribute("name", "myTr3" + count);
									document.getElementById("time")
											.appendChild(x13);

									// 						var y13 = document.createElement("TD");
									// 						var t03 = document
									// 								.createTextNode("Working hours of your event:");
									// 						y13.appendChild(t03);
									// 						document.getElementById("myTr3" + count).appendChild(
									// 								y13);

									var y23 = document.createElement("TD");
									var t13 = document.createElement("SELECT");
									t13.setAttribute("id", "t-days-from-"
											+ count);
									t13.setAttribute("name", "t-days-from-"
											+ count);

									var z = document.createElement("option");
									z.setAttribute("value", "1");
									var t = document.createTextNode("ПН");
									z.appendChild(t);
									t13.appendChild(z);

									var z2 = document.createElement("option");
									z2.setAttribute("value", "2");
									var t2 = document.createTextNode("ВТ");
									z2.appendChild(t2);
									t13.appendChild(z2);

									var z3 = document.createElement("option");
									z3.setAttribute("value", "3");
									var t3 = document.createTextNode("СР");
									z3.appendChild(t3);
									t13.appendChild(z3);

									var z4 = document.createElement("option");
									z4.setAttribute("value", "4");
									var t4 = document.createTextNode("ЧТ");
									z4.appendChild(t4);
									t13.appendChild(z4);

									var z5 = document.createElement("option");
									z5.setAttribute("value", "5");
									var t5 = document.createTextNode("ПТ");
									z5.appendChild(t5);
									t13.appendChild(z5);

									var z6 = document.createElement("option");
									z6.setAttribute("value", "6");
									var t6 = document.createTextNode("СБ");
									z6.appendChild(t6);
									t13.appendChild(z6);

									var z7 = document.createElement("option");
									z7.setAttribute("value", "7");
									var t7 = document.createTextNode("НД");
									z7.appendChild(t7);
									t13.appendChild(z7);

									y23.appendChild(t13);
									document.getElementById("myTr3" + count)
											.appendChild(y23);

									var y33 = document.createElement("TD");
									var t23 = document.createElement("SELECT");
									t23
											.setAttribute("id", "t-days-to-"
													+ count);
									t23.setAttribute("name", "t-days-to-"
											+ count);

									var z1 = document.createElement("option");
									z1.setAttribute("value", "1");
									var t1 = document.createTextNode("ПН");
									z1.appendChild(t1);
									t23.appendChild(z1);

									var z21 = document.createElement("option");
									z21.setAttribute("value", "2");
									var t21 = document.createTextNode("ВТ");
									z21.appendChild(t21);
									t23.appendChild(z21);

									var z31 = document.createElement("option");
									z31.setAttribute("value", "3");
									var t31 = document.createTextNode("СР");
									z31.appendChild(t31);
									t23.appendChild(z31);

									var z41 = document.createElement("option");
									z41.setAttribute("value", "4");
									var t41 = document.createTextNode("ЧТ");
									z41.appendChild(t41);
									t23.appendChild(z41);

									var z51 = document.createElement("option");
									z51.setAttribute("value", "5");
									var t51 = document.createTextNode("ПТ");
									z51.appendChild(t51);
									t23.appendChild(z51);

									var z61 = document.createElement("option");
									z61.setAttribute("value", "6");
									var t61 = document.createTextNode("СБ");
									z61.appendChild(t61);
									t23.appendChild(z61);

									var z71 = document.createElement("option");
									z71.setAttribute("value", "7");
									var t71 = document.createTextNode("НД");
									z71.appendChild(t71);
									t23.appendChild(z71);

									y33.appendChild(t23);
									document.getElementById("myTr3" + count)
											.appendChild(y33);

									var x12 = document.createElement("TR");
									x12.setAttribute("id", "myTr2" + count);
									x12.setAttribute("name", "myTr2" + count);
									document.getElementById("time")
											.appendChild(x12);

									// 						var y12 = document.createElement("TD");
									// 						var t02 = document
									// 								.createTextNode("Working hours of your event:");
									// 						y12.appendChild(t02);
									// 						document.getElementById("myTr2" + count).appendChild(
									// 								y12);

									var y22 = document.createElement("TD");
									var t12 = document.createElement("INPUT");
									t12.setAttribute("id", "t-time-from-"
											+ count);
									t12.setAttribute("name", "t-time-from-"
											+ count);
									t12.setAttribute("type", "time");
									y22.appendChild(t12);
									document.getElementById("myTr2" + count)
											.appendChild(y22);

									var y32 = document.createElement("TD");
									var t22 = document.createElement("INPUT");
									t22
											.setAttribute("id", "t-time-to-"
													+ count);
									t22.setAttribute("name", "t-time-to-"
											+ count);
									t22.setAttribute("type", "time");
									y32.appendChild(t22);
									document.getElementById("myTr2" + count)
											.appendChild(y32);

								}
							</script>