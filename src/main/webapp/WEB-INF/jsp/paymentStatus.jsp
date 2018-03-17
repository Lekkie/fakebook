<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<title>FakeBook</title>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

<meta charset="UTF-8">
<link href="${contextPath}/css/fakebook.css" rel="stylesheet">
<link href="${contextPath}/css/w3.css" rel="stylesheet">
<link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/css/common.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<head>
<script src="https://www.w3schools.com/lib/w3data.js"></script>
</head>
<body class="w3-theme-l5">


<c:if test="${pageContext.request.userPrincipal.name != null}">

<form id="logoutForm" method="POST" action="${contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>



<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Logo</a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Account Settings"><i class="fa fa-user"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Messages"><i class="fa fa-envelope"></i></a>

  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-right w3-padding-large w3-hover-white" title="My Account">
    <img src="https://www.w3schools.com/w3images/avatar2.png" class="w3-circle" style="height:23px;width:23px" alt="Avatar">
  </a>

  <a class="w3-bar-item w3-button w3-hide-small w3-right w3-padding-large w3-hover-white" title="Logout" onclick="document.forms['logoutForm'].submit()">Logout</a>
 </div>
</div>

<!-- Navbar on small screens -->
<div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 1</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 2</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 3</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">My Profile</a>
</div>

<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
  <!-- The Grid -->
  <div class="w3-row">


  <!-- Left Column -->
  <div class="w3-col m3">
      <!-- Profile -->
      <div class="w3-card w3-round w3-white">
          <div class="w3-container">
              <h4 class="w3-center">My Profile</h4>
              <p class="w3-center"><img src="https://www.w3schools.com/w3images/avatar3.png" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
              <hr>
              <p><i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i> ${pageContext.request.userPrincipal.name}</p>
              <p><i class="fa fa-home fa-fw w3-margin-right w3-text-theme"></i> London, UK</p>
              <p><i class="fa fa-birthday-cake fa-fw w3-margin-right w3-text-theme"></i> April 1, 1988</p>
          </div>
      </div>
      <br>

      <!-- Accordion -->
      <div class="w3-card w3-round">
          <div class="w3-white">
              <button onclick="myFunction('Demo1')" class="w3-button w3-block w3-theme-l1 w3-left-align">
                  <i class="fa fa-circle-o-notch fa-fw w3-margin-right"></i> My Payments</button>
              <div id="Demo1" class="w3-hide w3-container">
                  <p><a href="/recipient">Add a Recipient</a></p>
                  <p><a href="/friend">Add a Friend</a></p>
                  <p><a href="/payment">Make a Payment</a></p>
                  <p><a href="/paymentStatus">Check Payment Status</a></p>
              </div>
              <button onclick="myFunction('Demo2')" class="w3-button w3-block w3-theme-l1 w3-left-align">

                  <i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i> My Events</button>
              <div id="Demo2" class="w3-hide w3-container">
                  <p>Some other text..</p>
              </div>
              <button onclick="myFunction('Demo3')" class="w3-button w3-block w3-theme-l1 w3-left-align">

                  <i class="fa fa-users fa-fw w3-margin-right"></i> My Photos</button>
              <div id="Demo3" class="w3-hide w3-container">
                  <div class="w3-row-padding">
                      <br>
                      <div class="w3-half">
                          <img src="https://www.w3schools.com/w3images/lights.jpg" style="width:100%" class="w3-margin-bottom">
                      </div>
                      <div class="w3-half">
                          <img src="https://www.w3schools.com/w3images/nature.jpg" style="width:100%" class="w3-margin-bottom">
                      </div>
                      <div class="w3-half">
                          <img src="https://www.w3schools.com/w3images/mountains.jpg" style="width:100%" class="w3-margin-bottom">
                      </div>
                      <div class="w3-half">
                          <img src="https://www.w3schools.com/w3images/forest.jpg" style="width:100%" class="w3-margin-bottom">
                      </div>
                      <div class="w3-half">
                          <img src="https://www.w3schools.com/w3images/nature.jpg" style="width:100%" class="w3-margin-bottom">
                      </div>
                      <div class="w3-half">
                          <img src="https://www.w3schools.com/w3images/fjords.jpg" style="width:100%" class="w3-margin-bottom">
                      </div>
                  </div>
              </div>
          </div>
      </div>
      <br>

      <!-- Interests -->
      <div class="w3-card w3-round w3-white w3-hide-small">
          <div class="w3-container">
              <p>Interests</p>
              <p>
                  <span class="w3-tag w3-small w3-theme-d5">News</span>
                  <span class="w3-tag w3-small w3-theme-d4">W3Schools</span>
                  <span class="w3-tag w3-small w3-theme-d3">Labels</span>
                  <span class="w3-tag w3-small w3-theme-d2">Games</span>
                  <span class="w3-tag w3-small w3-theme-d1">Friends</span>
                  <span class="w3-tag w3-small w3-theme">Games</span>
                  <span class="w3-tag w3-small w3-theme-l1">Friends</span>
                  <span class="w3-tag w3-small w3-theme-l2">Food</span>
                  <span class="w3-tag w3-small w3-theme-l3">Design</span>
                  <span class="w3-tag w3-small w3-theme-l4">Art</span>
                  <span class="w3-tag w3-small w3-theme-l5">Photos</span>
              </p>
          </div>
      </div>
      <br>

      <!-- End Left Column -->
  </div>




    <!-- Middle Column -->
    <div class="w3-col m7">

      <div class="w3-row-padding">
        <div class="w3-col m12">
          <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">




            <form method="GET">
            <h3 class="form-heading">Payment Status</h3>
            <div>

                <table class="c">
                            <tr><th>Recipient</th> <th>Amount</th><th>Currency</th><th>Status</th><th>Payment Date</th></tr>
                                <c:forEach items="${requestScope.paymentStatusForm}" var="val">
                                    <tr>
                                        <!--<td> <c:out value="${val.getPaymentId()}" /> </td>-->
                                        <td> <c:out value="${val.getRecipient()}" /> </td>
                                        <td> <c:out value="${val.getAmount()}" /> </td>
                                        <td> <c:out value="${val.getCurrency()}" /> </td>
                                        <td><c:out value="${val.getStatus()}" /> </td>
                                        <td><c:out value="${val.getPaymentDate()}" /> </td>
                                    </tr>
                                </c:forEach>
                          </table>
                <br>

            </div>
            <button type="submit">Check Status</button>
            </form>


            </div>
          </div>
        </div>
      </div>


    <!-- End Middle Column -->
    </div>

    <!-- Right Column -->
    <div class="w3-col m2">
      <div class="w3-card w3-round w3-white w3-center">
        <div class="w3-container">
          <p>Upcoming Events:</p>
          <img src="https://www.w3schools.com/w3images/forest.jpg" alt="Forest" style="width:100%;">
          <p><strong>Holiday</strong></p>
          <p>Friday 15:00</p>
          <p><button class="w3-button w3-block w3-theme-l4">Info</button></p>
        </div>
      </div>
      <br>

      <div class="w3-card w3-round w3-white w3-center">
        <div class="w3-container">
          <p>Friend Request</p>
          <img src="https://www.w3schools.com/w3images/avatar6.png" alt="Avatar" style="width:50%"><br>
          <span>Jane Doe</span>
          <div class="w3-row w3-opacity">
            <div class="w3-half">
              <button class="w3-button w3-block w3-green w3-section" title="Accept"><i class="fa fa-check"></i></button>
            </div>
            <div class="w3-half">
              <button class="w3-button w3-block w3-red w3-section" title="Decline"><i class="fa fa-remove"></i></button>
            </div>
          </div>
        </div>
      </div>
      <br>


    <!-- End Right Column -->
    </div>

  <!-- End Grid -->
  </div>

<!-- End Page Container -->
</div>
<br>

<!-- Footer -->
<!--
<footer class="w3-container w3-theme-d3 w3-padding-16">
  <h5>Footer</h5>
</footer>
-->

<footer class="w3-container w3-theme-d5">
  <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
</footer>

<script type="text/javascript" src="functions.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/js/bootstrap.min.js"></script>
<script src="${contextPath}/js/w3.js"></script>
<script src="https://www.w3schools.com/lib/w3data.js"></script>

</c:if>

</body>
</html>