<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<style>
	.pageTitle {
   margin-top:0px;
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

.switch input {display:none;}

.slider {
  position: absolute;
  cursor: pointer;
  top: -2;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #3e1515;
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

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
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
 
 <c:url var="allSwitchOnOff" value="/allSwitchOnOff"></c:url> 
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

	<!--topLeft-nav-->
	<!-- <div class="sidebarOuter"></div> -->
	<!--topLeft-nav-->

	<!--wrapper-start-->
	<div class="wrapper">

		<!--topHeader-->
		<jsp:include page="/WEB-INF/views/include/logo.jsp">
		
			<jsp:param name="fr" value="${frDetails}"/>
		</jsp:include>




		<!--topHeader-->

		<!--rightContainer-->
		<div class="fullGrid center">
			<!--fullGrid-->
			<div class="wrapperIn2">

			 	<%-- <jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />

				</jsp:include> --%>  
   <%--     <input type="hidden" id="achievedTarget" value="${sessionScope.achievedTarget}">
       <input type="hidden" id="target" value="${sessionScope.fraTarget}"> --%>
        

				<!--rightSidebar-->
				<!-- <div  >
				<div   style="  overflow-x:scroll;  width: auto;" >   style="overflow-y:scroll; overflow-x:scroll; height:500px; width: 100%;"
  
  <table style="  overflow-x:scroll;  width: 500%;"><tr><td    style=" background-color:#aaa; width: 500px; ">
   
    <h2>Column 1</h2>
    <p>Some text..........................................
.............................................................................................................
..................................................................................
..................................................................................
..................................................................................
..................................................................................
..................................................................................
..................................................................................
..................................................................................
..................................................................................
.......................................................................................................................................................... </p>
  </td>
    <td style="background-color:#bbb; width: 500px;" >
   <h2>Column 2</h2>
    <p>Some text...  </p>
    </td>
  <td  style="background-color:#aaa; width: 500px;"> 
    <h2>Column 3</h2>
    <p>Some text...  </p>
  </td>
    <td style="background-color:#bbb; width:500px;">
   <h2>Column 4</h2>
    <p>Some text.... .</p>
    </td><td  style="background-color:#aaa; width: 500px;">
    <h2>Column 5</h2>
    <p>Some text.... </p>
  </td>
    <td style="background-color:#bbb; width: 500px;">
   <h2>Column 6</h2>
    <p>Some text...... </p>
    </td><td  style="background-color:#aaa; width: 500px;">
    <h2>Column 7</h2>
    <p>Some text..... </p>
  </td>
    <td style="background-color:#bbb; width: 500px;">
   <h2>Column 8</h2>
    <p>Some text..... .</p>
    </td><td  style="background-color:#aaa; width: 500px;">
    <h2>Column 9</h2>
    <p>Some text..... ..</p>
  </td>
    <td style="background-color:#bbb; width: 500px;">
   <h2>Column 10</h2>
    <p>Some text... ....</p>
    </td><td  style="background-color:#aaa; width: 500px;">
    <h2>Column 11</h2>
    <p>Some text.... .</p>
  </td>
    <td style="background-color:#aaa; width: 500px;">
   <h2>Column 12</h2>
    <p>Some text....... ........</p>
    </td>
     <td style="background-color:#bbb; width: 500px;">
   <h2>Column 13</h2>
    <p>Some text....... ........</p>
    </td>
     <td style="background-color:#aaa; width: 500px;">
   <h2>Column 14</h2>
    <p>Some text....... ........</p>
    </td>
    </tr>
  </table>
</div> -->

<div  >
				<div   style="  overflow-x:scroll;  width: auto;" ><!--    style="overflow-y:scroll; overflow-x:scroll; height:500px; width: 100%;" -->
  
  <table style="  overflow-x:scroll;  width: auto;"><tr> 
  
  <c:forEach items="${roomList}" var="roomList" varStatus="count">
					  <td style="background-color:#9ceabb; width: 500px; padding-left:12px; border: 1px solid black;">
									  <br>
									    <h2 align="center" >${roomList.roomName}</h2> 
									 <br>
									   <c:choose>
									   	<c:when test="${roomList.deviceList.size()>0}">
											  <table style="border:0px solid white;">
											  <tr><td  align="left" style="width:300px;">All
												   <td align="right" style=" padding-right:12px;">    
													 <label  class="switch">
													 	<input  type="checkbox"   id="switchAll${roomList.roomId}" onchange="allOnAndOff(${roomList.roomId});"  >
													  <span class="slider round" ></span>
													</label>
										    	   </td>
										    	   </tr>
									    	 </table>
									   	</c:when>
									   	<c:otherwise>
									   	 		 <div align="center">No Switch</div>
									   	</c:otherwise>
									   
									   </c:choose><br>
									     
									   		 <c:forEach items="${roomList.deviceList}" var="deviceList" varStatus="count">
												  <table style="border:0px solid white;">
												   <tr><td  align="left" style="width:300px;">${deviceList.devCaption} </td>
													   <td align="right" style=" padding-right:12px;"> 
															  <label  class="switch">
															  <input type="checkbox" value="${deviceList.devId}" id="switch${deviceList.devId}" onchange="onAndOff(${deviceList.devId});"  >
															  <span class="slider round"></span>
															</label>
														</td>
													</tr></table>
											</c:forEach>
					    
					    </td>
											 
				</c:forEach>
  
    </tr>
  </table>
</div>
 

 

 
										<%-- <h2 class="pageTitle">Hi <span>${login.exhName},</span> Welcome Back</h2> 
										<div style="text-align: right; "><b><a href="${pageContext.request.contextPath}/exhibitorDashboard">Dashboard</a></b></div>
             --%>
             
					<!--slider-->
					<!--slider thum size : width:850px height:350px-->
						<%-- <div class="latestNews">
					
						<h3 class="latestNewsTitle">Event List</h3>
							<div class="microsoft marquee">
						<c:forEach items="${schedulerLists}" var="schedulerLists"  varStatus="count">
						
					            <c:set var="colors" value=""/>
					            <c:choose>
					            <c:when test="${count.index%2==0}">
					            <c:set var="colors" value="white"/>
					           </c:when>
					           <c:otherwise>
					            <c:set var="colors" value="lightblue"/>
					          </c:otherwise>
					          </c:choose>
								<span style="color:${colors}"> ${schedulerLists.schMessage} </span>
							
						
						</c:forEach>
						</div>
					</div> --%>
			<!-- 		<div id="owl-example" class="owl-carousel"> -->

						<c:forEach items="${eventList}" var="eventList">

							<div class="gallery">
  <a target="_blank" href="${pageContext.request.contextPath}/getEventDetail/${eventList.eventId}/${eventList.orgId}">
    <img src="http://132.148.143.124:8080/uploads/MSPCAKE/13:17:38-aa.png" alt="Trolltunga Norway" width="300" height="200">
  </a>
 <p align="left"> Event Name: <b>${eventList.eventName}</b></p>
  <p align="left">Place: <b>${eventList.eventLocation}</b> </p>
  <p align="left">Event From : <b>${eventList.eventFromDate}</b> &nbsp;&nbsp;&nbsp;&nbsp;  To : <b>${eventList.eventToDate}</b></p>
  <div class="desc">
  <c:choose>
    <c:when test="${eventList.subStatus==0}">
    <input type="button" value="Subscribe" class="btn btn-info"/>
    </c:when>
  <c:otherwise>
    <input type="button" disabled value="Subscribe" class="btn btn-success"/>
  </c:otherwise>
  </c:choose>
    </div>
  </div>
 		</c:forEach>
 		

						<%-- <div class="item">
							<div class="screen4plan">
								<div class="homesliderImg">
									<img
										src="${pageContext.request.contextPath}/resources/images/slide1.jpg"
										alt="img">
								</div>
								<h3 class="homesliderTitle">
								
								Contrary to popular belief,
									Lorem Ipsum is not simply in some form, by injected humour, or
									randomised words which don't look even slightly believable.</h3>
							</div>
						</div>

						<div class="item">
							<div class="screen4plan">
								<div class="homesliderImg">
									<img
										src="${pageContext.request.contextPath}/resources/images/slide2.jpg"
										alt="img">
								</div>
								<h3 class="homesliderTitle">Contrary to popular belief,
									Lorem Ipsum is not simply in some form, by injected humour, or
									randomised words which don't look even slightly believable.</h3>
							</div>
						</div>

						<div class="item">
							<div class="screen4plan">
								<div class="homesliderImg">
									<img
										src="${pageContext.request.contextPath}/resources/images/slide1.jpg"
										alt="img">
								</div>
								<h3 class="homesliderTitle">Contrary to popular belief,
									Lorem Ipsum is not simply in some form, by injected humour, or
									randomised words which don't look even slightly believable.</h3>
							</div>
						</div>

						<div class="item">
							<div class="screen4plan">
								<div class="homesliderImg">
									<img
										src="${pageContext.request.contextPath}/resources/images/slide1.jpg"
										alt="img">
								</div>
								<h3 class="homesliderTitle">Contrary to popular belief,
									Lorem Ipsum is not simply in some form, by injected humour, or
									randomised words which don't look even slightly believable.</h3>
							</div>
						</div>

						<div class="item">
							<div class="screen4plan">
								<div class="homesliderImg">
									<img
										src="${pageContext.request.contextPath}/resources/images/slide1.jpg"
										alt="img">
								</div>
								<h3 class="homesliderTitle">Contrary to popular belief,
									Lorem Ipsum is not simply in some form, by injected humour, or
									randomised words which don't look even slightly believable.</h3>
							</div>
						</div>
 --%>
					</div>
					<!--slider-->

					<!--latestNews-->
					
					
				<%-- 	<div class="latestNews">
					
						<h3 class="latestNewsTitle">Latest News</h3>
							<div class="microsoft marquee">
						<c:forEach items="${schedulerLists}" var="schedulerLists">
						
					

								<span> ${schedulerLists.schMessage} </span>
							
						
						</c:forEach>
						</div>
					</div> --%>
					
					
					<!--latestNews-->

				</div>
				<!--rightSidebar-->

			</div>
			<!--fullGrid-->
		</div>
		<!--rightContainer-->
	</div>
	<!--wrapper-end-->

  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
  
  function onAndOff(devId) {
		 
		 
		var devId = $('#switch'+devId).val();
		 
		if(document.getElementById("switch"+devId).checked == true)
			alert("ON");
		else
			alert("OFF");
		 
}
  
  function allOnAndOff(roomId) {
		 
		  
		$.getJSON('${allSwitchOnOff}',

				{
					 
					 
			roomId : roomId, 
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
