<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.pageTitle {
	margin-top: 0px;
}
</style>
<%-- <!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<title>Monginis</title>
<link
	href="${pageContext.request.contextPath}/resources/css/monginis.css"
	rel="stylesheet" type="text/css" />
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/feviconicon.png"
	type="image/x-icon" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<!--rightNav-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#menuzord").menuzord({
			align : "left"
		});
	});
	$(".toggle").on("click", function() {
		$(".marquee").toggleClass("microsoft");
	});
</script>
<!--rightNav-->

</head>
<body> comment by sachin --%>
<div id="overlay">
	<div id="text">Please Wait...</div>
</div>
<style type="text/css">
.fit-img {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	margin: auto;
	max-width: 60%;
	max-height: 60%
}

.fit-img-bottom {
	top: auto;
}

.fit-img-tight {
	max-width: 60%;
	max-height: 60%
}

/* my style  */
div.gallery {
	margin: 5px;
	border: 1px solid #ccc;
	float: left;
	width: 320px;
}

div.gallery:hover {
	border: 1px solid #777;
}

div.gallery img {
	width: 100%;
	height: auto;
}

div.desc {
	padding: 15px;
	text-align: center;
}
/* table, td {
    border-right: 1px solid black;
} */
</style>

<style>
.switch {
	position: relative;
	display: inline-block;
	width: 50px;
	height: 20px;
}

.switch input {
	display: none;
}

.slider {
	position: absolute;
	cursor: pointer;
	top: -2;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: #a98f8f;
	-webkit-transition: .4s;
	transition: .4s;
}

.slider:before {
	position: absolute;
	content: "";
	height: 20px;
	width: 20px;
	left: 3px;
	bottom: 1px;
	background-color: white;
	-webkit-transition: .4s;
	transition: .4s;
}

input:checked+.slider {
	background-color: #2196F3;
}

input:focus+.slider {
	box-shadow: 0 0 1px #2196F3;
}

input:checked+.slider:before {
	-webkit-transform: translateX(26px);
	-ms-transform: translateX(26px);
	transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
	border-radius: 34px;
}

.slider.round:before {
	border-radius: 50%;
}
</style>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	height: 40%;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

#overlay {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(101, 113, 119, 0.5);
	z-index: 2;
	cursor: pointer;
}

#text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 25px;
	color: white;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
}
</style>

<c:url var="allSwitchOnOff" value="/allSwitchOnOff"></c:url>
<c:url var="setScheduler" value="/setScheduler"></c:url>
<c:url var="setSchedulerForAll" value="/setSchedulerForAll"></c:url>
<c:url var="singleSwitchOnOff" value="/singleSwitchOnOff"></c:url>
<c:url var="selectAllSchedulerCheckBox" value="/selectAllSchedulerCheckBox"></c:url>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<!--topLeft-nav-->
<!-- <div class="sidebarOuter"></div> -->
<!--topLeft-nav-->

<!--wrapper-start-->
<div class="wrapper">

	<!--topHeader-->
	<jsp:include page="/WEB-INF/views/include/logo.jsp">

		<jsp:param name="fr" value="${frDetails}" />
	</jsp:include>



	<header> </header>
	<!--topHeader-->

	<!--rightContainer-->
	<div class="fullGrid center">
		<!--fullGrid-->
		<div class="wrapperIn2">

			<div>
				<div align="right">
					<i class="fa fa-clock-o" style="font-size: 100px" id="myBtn"
						onclick="popupMsgForAll()"></i>
				</div>
				<div style="overflow-x: scroll; width: auto;">
					<!--    style="overflow-y:scroll; overflow-x:scroll; height:500px; width: 100%;" -->

					<table style="overflow-x: scroll; width: auto; color: #fff;">
						<tr>

							<c:forEach items="${roomList}" var="roomList" varStatus="count">
							<c:set var="on" value="on"></c:set>
										<c:set var="off" value="off"></c:set>
								<td
									style="background-color: #202522; width: 500px; padding-left: 12px; border: 1px solid white;">
									<c:choose>
										<c:when test="${roomList.deviceList.size()>0}">

										</c:when>
									</c:choose> <br>
									<h2 align="center">${roomList.roomName}</h2> <br> <c:choose>
										<c:when test="${roomList.deviceList.size()>0}">
											<table style="border: 0px solid white; color: #fff;">
												<tr>
													<td>
														<div style="padding-right: 5px;">
															<input type="checkbox" value="${roomList.roomId}"
																id="allScheduler${roomList.roomId}" name="allScheduler"
																onchange="selectAllScheduler(${roomList.roomId});">
														</div>
													<td align="left" style="width: 300px; padding-right: 5px;">All
													
													<td align="right" style="padding-right: 12px; padding-left: 10px;">
														<label class="switch"> 
														<c:set var="allSts" value="1"></c:set>
														<c:set var="in" value="0"></c:set>
														
														 <c:forEach items="${roomList.deviceList}" var="deviceList" varStatus="count">
																	<c:forEach items="${currentStatusList}" var="currentStatusList" >
																	<c:choose>
																		<c:when test="${currentStatusList.devMac==deviceList.devMac && currentStatusList.devType==deviceList.devType}">
																			<c:choose> 
																				<c:when test="${currentStatusList.status eq off}">
																				<c:set var="allSts" value="0"></c:set> 
																				</c:when>
																				 
																			</c:choose>
																			 
																		</c:when>
																		<c:when test="${currentStatusList.devMac!=deviceList.devMac}">
																		<c:set var="allSts" value="2"></c:set>
																		</c:when>
																		 
																	</c:choose>
																	 
																</c:forEach>
																 
														 </c:forEach>
														 
														 <c:choose>
														 	<c:when test="${allSts==0}">
														 	<input type="checkbox" id="switchAll${roomList.roomId}"
															onchange="allOnAndOff(${roomList.roomId});"  >
														 	</c:when>
														 	<c:when test="${allSts==1}">
														 		<c:choose>
														 			<c:when test="${currentStatusList.size()!=0}">
														 				<input type="checkbox" id="switchAll${roomList.roomId}"
																	onchange="allOnAndOff(${roomList.roomId});" checked>
														 			</c:when>
														 		</c:choose>
																 	
														 	</c:when>
														 	
														 </c:choose>
														 
														 
														<%-- <input type="checkbox" id="switchAll${roomList.roomId}"
															onchange="allOnAndOff(${roomList.roomId});">  --%>
															
															<span class="slider round"></span>
													</label> </td>
												</tr>
											</table>
										</c:when>
										<c:otherwise>
											<div align="center">No Switch</div>
										</c:otherwise>

									</c:choose><br>
								<br> <c:forEach items="${roomList.deviceList}" var="deviceList" varStatus="count">
										
										 		
										<table style="border: 0px solid white; color: #fff;">
											<tr>
												<td align="left"
													style="padding-bottom: 10px; padding-right: 5px;"><input
													type="checkbox" value="${deviceList.devId}"
													name="scheduler" id="scheduler${deviceList.devId}"></td>
												<td align="left" style="width: 300px; padding-bottom: 10px;">${deviceList.devCaption}
												</td>
												<td style="padding-bottom: 10px;"><i
													class="fa fa-clock-o" style="font-size: 24px"
													id="myBtn${deviceList.devId}"
													onclick="popupMsg(${deviceList.devId})"></i>

													<div id="myModal${deviceList.devId}" class="modal">

														<div class="modal-content" style="color: #202522;">
															<span class="close" id="close${deviceList.devId}">&times;</span>
															<h3 style="text-align: center;">Set
																Scheduler..${deviceList.devId}</h3>

															<div class="colOuter">
																<div class="col-md-2">Schedule Time*</div>
																<div class="col-md-3">
																	<input type="time"
																		name="scheduleTime${deviceList.devId}"
																		class="form-control"
																		id="scheduleTime${deviceList.devId}" />
																</div>
																<div class="col-md-2">Is Daily*</div>
																<div class="col-md-3">
																	<select class="selectpicker" data-live-search="true"
																		title="Please Select" name="daily${deviceList.devId}"
																		id="daily${deviceList.devId}">


																		<option value="0">NO</option>
																		<option value="1">Yes</option>


																	</select>

																	<%-- <input type="radio" id="daily${deviceList.devId}" name="daily${deviceList.devId}" value="0" checked> NO 
  																	<input type="radio" id="daily${deviceList.devId}" name="daily${deviceList.devId}"   value="1"> YES  --%>

																</div>
															</div>
															<br>

															<div class="colOuter">
																<div class="col-md-2">ON/OFF*</div>
																<div class="col-md-3">
																	<select class="selectpicker" data-live-search="true"
																		title="Please Select" name="onOFF${deviceList.devId}"
																		id="onOFF${deviceList.devId}">


																		<option value="0">OFF</option>
																		<option value="1">ON</option>


																	</select>
																</div>
															</div>
															<br>

															<div class="colOuter">
																<div align="center">
																	<input type="button"
																		onclick="setScheduler(${deviceList.devId},${deviceList.roomId})"
																		value="Set Shedule" class="btn btn-info">
																</div>

															</div>
														</div>

													</div></td>
													
												<td align="right"
													style="padding-right: 12px; padding-left: 10px; padding-bottom: 10px;">
													<label class="switch"> 
													  <c:forEach items="${currentStatusList}" var="currentStatusList" >
														<c:choose>
															<c:when test="${currentStatusList.devMac==deviceList.devMac && currentStatusList.devType==deviceList.devType}">
																<c:choose>
																	<c:when test="${currentStatusList.status eq on}">
																	<input type="checkbox" value="${deviceList.devId}" id="switch${deviceList.devId}"
														onchange="onAndOff(${deviceList.devId},${deviceList.roomId});" checked> 
																	</c:when>
																	<c:when test="${currentStatusList.status eq off}">
																	<input type="checkbox" value="${deviceList.devId}" id="switch${deviceList.devId}"
														onchange="onAndOff(${deviceList.devId},${deviceList.roomId});">
																	</c:when>
																	
																</c:choose>
															
															</c:when>
														
														</c:choose>
										
													</c:forEach>
													<%-- 
													 <input type="checkbox"
														value="${deviceList.devId}" id="switch${deviceList.devId}"
														onchange="onAndOff(${deviceList.devId},${deviceList.roomId});" checked> --%>
														
														<span
														class="slider round"></span>
												</label>
												</td>
											</tr>
										</table>
									</c:forEach>

								</td>

							</c:forEach>

						</tr>
					</table>
				</div>

				<div id="myModal" class="modal">

					<div class="modal-content">
						<span class="close" id="close">&times;</span>
						<h3 style="text-align: center;">Set Scheduler To All..</h3>

						<div class="colOuter">
							<div class="col-md-2">Schedule Time*</div>
							<div class="col-md-3">
								<input type="time" name="scheduleTime" class="form-control"
									id="scheduleTime" />
							</div>
							<div class="col-md-2">Daily*</div>
							<div class="col-md-3">
								<select class="selectpicker" data-live-search="true"
									title="Please Select" name="daily" id="daily">


									<option value="0">NO</option>
									<option value="1">Yes</option>


								</select>
							</div>
						</div>
						<br>

						<div class="colOuter">
							<div class="col-md-2">ON/OFF*</div>
							<div class="col-md-3">
								<select class="selectpicker" data-live-search="true"
									title="Please Select" name="onOFF"
									id="onOFF${deviceList.devId}">


									<option value="0">OFF</option>
									<option value="1">ON</option>


								</select>
							</div>
						</div>
						<br>

						<div class="colOuter">
							<div align="center">
								<input type="button" onclick="setSchedulerForAll()"
									value="Set Shedule To All" class="btn btn-info">
							</div>

						</div>
					</div>

				</div>
				<!-- The Modal -->


			</div>

			<!--latestNews-->

		</div>
		<!--rightSidebar-->

	</div>
	<!--fullGrid-->
</div>
<!--rightContainer-->

<!--wrapper-end-->
<!-- <footer>
	<div class="colOuter">
						<div align="right">
							<i class="fa fa-clock-o" style="font-size:100px" id="myBtn" onclick="popupMsgForAll()"></i>
							</div>
				 
					</div>
	
	</footer> -->

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script>
function on() {
    document.getElementById("overlay").style.display = "block";
}

function off() {
    document.getElementById("overlay").style.display = "none";
}
</script>
<script>
  function popupMsg(devId) {
		 
		 /*  alert("first "+devId); */
		  
	// Get the modal
	  var modal = document.getElementById('myModal'+devId);
	 

	  // Get the button that opens the modal
	  var btn = document.getElementById("myBtn"+devId);
	    
	  // Get the <span> element that closes the modal
	  var span = document.getElementById("close"+devId);
	   
	  // When the user clicks the button, open the modal 
	 /*  btn.onclick = function() {
		    alert("in Open"+devId);   */
	      modal.style.display = "block";
	 /*  } */

	  // When the user clicks on <span> (x), close the modal
	  span.onclick = function() {
	      modal.style.display = "none";
	      document.getElementById("scheduleTime"+devId).value="";
	      document.getElementById("daily"+devId).value=0;
	      document.getElementById("onOFF"+devId).value=0;
	  }

	  // When the user clicks anywhere outside of the modal, close it
	  window.onclick = function(event) {
	      if (event.target == modal) {
	          modal.style.display = "none";
	          document.getElementById("scheduleTime"+devId).value="";
	          document.getElementById("daily"+devId).value=0;
	          document.getElementById("onOFF"+devId).value=0;
	      }
	  }
		 
}

</script>
<script>
// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
 var span = document.getElementById("close");

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
    document.getElementById("scheduleTime").value="";
    document.getElementById("daily").value=0;
    document.getElementById("onOFF").value=0;
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        document.getElementById("scheduleTime").value="";
	      document.getElementById("daily").value=0;
	      document.getElementById("onOFF").value=0;
    }
}
</script>

<script type="text/javascript">
  
  function setScheduler(devId,roomId) {
	  
	  var scheduleTime = $("#scheduleTime"+devId).val();
	  var daily = $("#daily"+devId).val();
	  var onOFF = $("#onOFF"+devId).val();
	   
		on();

		$
				.getJSON(
						'${setScheduler}',

						{
							 
							scheduleTime : scheduleTime, 
							daily : daily,
							roomId : roomId,
							devId : devId,
							onOFF : onOFF,
							ajax : 'true'

						},
						function(data) { 
							off();
							 if(data.isError==true)
								 {
								 alert("Scheduler Is Not Set ");
								 }
							 else{
								 alert("Scheduler Is Set ");
							 }
							var modal = document.getElementById('myModal'+devId);
							modal.style.display = "none"; 
						      document.getElementById("scheduleTime"+devId).value=""; 
						      document.getElementById("daily"+devId).value=0; 
						      document.getElementById("onOFF"+devId).value=0;
						});

	 
		   

	}
  
function setSchedulerForAll() {
	 
	  var scheduleTime = $("#scheduleTime").val();
	  var daily = $("#daily").val();
	  var onOFF = $("#onOFF").val();
	  
	  var checkboxes = document.getElementsByName('allScheduler');
	  var selected = "";
	  for (var i=0; i<checkboxes.length; i++) {
	      if (checkboxes[i].checked) {
	    	  selected=selected+","+checkboxes[i].value;
	      }
	  }
	  
	  var  selectedDivIdCheckboxes = document.getElementsByName('scheduler');
	  var selectedDivId = "";
	  for (var i=0; i<selectedDivIdCheckboxes.length; i++) {
	      if (selectedDivIdCheckboxes[i].checked) {
	    	  selectedDivId=selectedDivId+","+selectedDivIdCheckboxes[i].value;
	      }
	      
	  }
	 
	  
	     if(selected!="")
		   {
		   on();
		   $('#loader').show();

			$ .getJSON(
							'${setSchedulerForAll}',

							{
								 
								scheduleTime : scheduleTime, 
								daily : daily, 
								selected : selected,
								selectedDivId : selectedDivId,
								onOFF : onOFF,
								ajax : 'true'

							},
							function(data) { 
								off();
								if(data.isError==true)
								 {
								 alert("Scheduler Is Not Set ");
								 }
							 else{
								 alert("Scheduler Is Set ");
							 	}
								 
								var modal = document.getElementById('myModal');
								modal.style.display = "none"; 
							      document.getElementById("scheduleTime").value=""; 
							      document.getElementById("daily").value=0; 
							      document.getElementById("onOFF").value=0;
							      
							      for (var i=0; i<checkboxes.length; i++) {
								      if (checkboxes[i].checked) {
								    	  document.getElementById("allScheduler"+checkboxes[i].value).checked= false;
								      }
								  }
							      
							      for (var i=0; i<selectedDivIdCheckboxes.length; i++) {
								      if (selectedDivIdCheckboxes[i].checked) { 
								    	  document.getElementById("scheduler"+selectedDivIdCheckboxes[i].value).checked= false;
								      }
								      
								  }
							});
		   
		   
		   }
	   else
		   {
		   alert("Select Minimum One Room ");
		   var modal = document.getElementById('myModal');
			modal.style.display = "none"; 
		      document.getElementById("scheduleTime").value=""; 
		      document.getElementById("daily").value=0; 
		      document.getElementById("onOFF").value=0;
		   }  
		 
	}
  
  function onAndOff(devId,roomId) {
		 
		 
		var devId = $('#switch'+devId).val();
		var operation="";
		 
		  if(document.getElementById("switch"+devId).checked == true)
		  {
				operation="on";
				}else{
					operation="off";			
				}
		  on();
		  $.getJSON('${singleSwitchOnOff}',

					{
						 
						 
				roomId : roomId, 
				devId : devId,
				operation: operation,
						ajax : 'true'

					},
					function(data) {
						
						off();   		
					});
		 
}
  
  function allOnAndOff(roomId) {
		 
	  var operation="";
	  
		if(document.getElementById("switchAll"+roomId).checked == true)
			{
			operation="on";
			}else{
				operation="off";			
			}
	  
	
		on();
		$.getJSON('${allSwitchOnOff}',

				{
					 
					 
			roomId : roomId, 
			operation: operation,
					ajax : 'true'

				},
				function(data) {
					
					if(document.getElementById("switchAll"+roomId).checked == true)
						{
						for(var i =0 ;i<data.length;i++)
						 { 
						 document.getElementById("switch"+data[i]).checked = true;
						 }
						} 
					else
						{
						for(var i =0 ;i<data.length;i++)
						 { 
						 document.getElementById("switch"+data[i]).checked = false;
						 }
						}
					off();
						 		
				});
		 
 
		 
}
  
  function selectAllScheduler(roomId) {
		 
	   
		$.getJSON('${selectAllSchedulerCheckBox}',

				{
					 
					 
			roomId : roomId, 
					ajax : 'true'

				},
				function(data) {
					
					if(document.getElementById("allScheduler"+roomId).checked == true)
						{
						for(var i =0 ;i<data.length;i++)
						 { 
						 document.getElementById("scheduler"+data[i]).checked = true;
						 }
						} 
					else
						{
						for(var i =0 ;i<data.length;i++)
						 { 
						 document.getElementById("scheduler"+data[i]).checked = false;
						 }
						}
						 		
				});
		 

		 
}
  
	var achievedTarget=document.getElementById("achievedTarget").value;
	var target=document.getElementById("target").value;
	var monthTarget=target;
google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawMultSeries);

function drawMultSeries() {
	 
	achievedTarget=Math.ceil(achievedTarget);
	 
	if(achievedTarget>=target)
		{
		 
		target=0;
		}
	else{
		 
		 
		target=target-achievedTarget;
		
	}
	
      var data = google.visualization.arrayToDataTable([
        ['Target In Lakhs', 'Assigned Target'],
        ['Monthly Target:'+monthTarget,target],
        ['Achieved Target:'+achievedTarget,achievedTarget],
      ]);

      var options = {
    	        title: 'Franchise Target',
    	        chartArea: {width: '1220',height: '1220'},
    	        hAxis: {
    	          title: 'Target Details',
    	          minValue: 0
    	        },
    	        vAxis: {
    	          title: 'Target In Lakhs'
    	        }
    	     
    	      };


            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));

   
      chart.draw(data, options);
    }
</script>
</body>
</html>
