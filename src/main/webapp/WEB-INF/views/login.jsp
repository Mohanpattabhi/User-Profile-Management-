<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-united.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.8.3.js" />
<script
	src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.js" />
<script src="http://code.jquery.com/jquery-latest.min.js"
	type="text/javascript"></script>


<style>
.error {
	color: #ff0000;
	font-size: 0.9em;
	font-weight: bold;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>

<script>


function register(){
		document.forms[0].action="register";
		document.forms[0].submit();
				
	}
	
	function login(){
		document.forms[0].action="loginSubmit";
		document.forms[0].submit();
				
	}
	function generateOTP(){
		document.getElementById("errors").innerHTML ="";
	

	var loginId = document.forms[0].loginId.value;
	
	if(null == loginId || '' == loginId){
		document.getElementById("errors").innerHTML ="Please enter loginId";
		return false;
	}
	
	 var url = "generateOTP?loginId=" + loginId;
	 window.open(url, "OTP", "width=600,height=500");

				
	}
	


</script>

<title>UPM Login</title>
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<div class="row.col-md-4 col-lg-offset-4">Welcome to User
				Profile Management Login</div>
		</div>
	</div>

	<div></div>
	</div>

	<div class="col-lg-6 col-lg-offset-3">
		<div class="well">
			<div class="container">
				<div class="row">
					<div class="col-lg-6">
						<form:form cssClass="form-horizontal" action="loginSubmit"
							commandName="loginForm">
							<div>
								<div class="error" id="errors">
									<c:if test="${not empty errorMessage}">
										<c:out value="${errorMessage}" />
									</c:if>
								</div>
							</div>
							<fieldset>
								<legend>User Profile Login Form</legend>

								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="loginId">
										<spring:message text="Login Id" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="loginId" />
										<form:errors path="loginId" id="errors" cssClass="error" />
									</div>

								</div>

								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="otp">
										<spring:message text="OTP" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="otp" />
										<form:errors path="otp" cssClass="error" />
									</div>

								</div>


								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="Password">
										<spring:message text="Password" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="password"
											type="password" />
										<form:errors path="password" cssClass="error" />
									</div>

								</div>

								<div class="col-lg-9 col-lg-offset-3">

									<a class="btn btn-default" id="register"
										onClick="js:register();" href="#">Register</a> <a
										style="margin-left: 10px" class="btn btn-default"
										onClick="js:login();" id="login" href="#">Login</a> <a
										href="#" style="margin-left: 10px" onClick="js:generateOTP()">Generate
										OTP</a>
								</div>

							</fieldset>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>