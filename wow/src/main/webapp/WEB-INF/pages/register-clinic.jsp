<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>WoW</title>

<!-- Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha256-MfvZlkHCEqatNoGiOXveE8FIwMzZg4W85qfrfIFBfYc= sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
	crossorigin="anonymous">
<!-- <link href="${pageContext.request.contextPath}/resources/custom/style.css" rel="stylesheet"> --!>
<!-- <link href="${pageContext.request.contextPath}/resources/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" >  -->


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">
		<div id="feedbackbox" style="margin-top: 50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<c:if test="${errors.size() > 0}">
				<div class="alert alert-danger alert-dismissible" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<c:forEach items="${errors}" var="error">
						<ul>
							<li>
								${error} 
							</li>
						</ul>
	    			</c:forEach>
 				</div>
			</c:if>
		</div>
		<div id="signupbox" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">Sign Up</div>
					<div style="float: right; font-size: 85%; position: relative; top: -10px">
						<a id="signinlink" href="login" >Sign In</a>
					</div>
				</div>
				<div class="panel-body">
					<form id="signupform" class="form-horizontal" role="form" method="post">
						<div class="form-group">
							<label for="email" class="col-md-3 control-label">Email</label>
							<div class="col-md-9">
								<input type="email" class="form-control" name="email" required placeholder="Email Address" value="${model.email}">
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-md-3 control-label">Password</label>
							<div class="col-md-9">
								<input type="password" class="form-control" name="passwd" required placeholder="Password"
								pattern=".{10,50}" title="Password Min 10 characters">
							</div>
						</div>
						
						<div class="form-group">
							<label for="clinicName" class="col-md-3 control-label">Clinic Name</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicName" required placeholder="Clinic Name"
								pattern=".{2,50}" title="Min 2 characters, Max50 characters" value="${model.clinicName}" >
							</div>
						</div>
						<div class="form-group">
							<label for="clinicPhone1" class="col-md-3 control-label">Phone</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicPhone1" required placeholder="Phone"
								pattern=".{10}" title="Phone" value="${model.clinicPhone1}">
							</div>
						</div>
						<div class="form-group">
							<label for="clinicDesc" class="col-md-3 control-label">Clinic Description</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicDesc" required placeholder="Clinic Description"
								pattern=".{2,50}" title="Min 2 characters, Max50 characters" value="${model.clinicDesc}">
							</div>
						</div>

						<div class="form-group">
							<label for="clinicAddrLine1" class="col-md-3 control-label">Address Line1</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicAddrLine1" required placeholder="Address Line1"
								pattern=".{2,50}" title="Min 2 characters, Max50 characters" value="${model.clinicAddrLine1}">
							</div>
						</div>
						<div class="form-group">
							<label for="clinicAddrLine2" class="col-md-3 control-label">Address Line12</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicAddrLine2" required placeholder="Address Line2"
								pattern=".{2,50}" title="Min 2 characters, Max50 characters" value="${model.clinicAddrLine2}">
							</div>
						</div>
						
						<div class="form-group">
							<label for="clinicCity" class="col-md-3 control-label">City</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicCity" required placeholder="City"
								pattern=".{2,50}" title="Min 2 characters, Max50 characters" value="${model.clinicCity}">
							</div>
						</div>

						<div class="form-group">
							<label for="clinicState" class="col-md-3 control-label">State</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicState" required placeholder="State"
								pattern=".{2,50}" title="Min 2 characters, Max50 characters" value="${model.clinicState}">
							</div>
						</div>
						<div class="form-group">
							<label for="clinicCountry" class="col-md-3 control-label">Country</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicCountry" required placeholder="Country"
								pattern=".{2,50}" title="Min 2 characters, Max50 characters" value="${model.clinicCountry}">
							</div>
						</div>
						<div class="form-group">
							<label for="clinicZipCode" class="col-md-3 control-label">Postal Code</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="clinicZipCode" required placeholder="Postal Code"
								pattern=".{2,10}" title="Min 2 characters, Max10 characters" value="${model.clinicZipCode}">
							</div>
						</div>						
						<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
						<div class="form-group">
							<!-- Button -->
							<div class="col-sm-12 controls">
								<button id="btn-signup" class="btn btn-success"><i class="glyphicon glyphicon-send"></i> Sign Up </a>
								<button id="btn-reset1" type="reset" class="btn btn-danger">Reset</button>
							</div>
						</div>
					</form>f
				</div>
			</div>
		</div>
	</div>
		

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<!-- Bootstrap -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"
		integrity="sha256-Sk3nkD6mLTMOF0EOpNtsIry+s1CsaqQC1rVLTAy+0yc= sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ=="
		crossorigin="anonymous"></script>
		
</body>
</html>