<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
<Script>
	function closeWindow() {
		window.close();
	}
</Script>


<title>Generate OTP</title>
</head>
<body>



	<div class="container">
		<div class="jumbotron">
			<div class="row.col-md-4 col-lg-offset-4">Welcome to User
				Profile Management Login</div>
		</div>

		<div></div>
	</div>

	<div class="col-lg-6 col-lg-offset-3">
		<div class="well">
			<div class="container">
				<div class="row">
					<div class="col-lg-6">
						<fieldset>
							<legend>Generate OTP</legend>


							<c:choose>
								<c:when test="${not empty errorMessage}">
									<c:out value="${errorMessage}" />
								</c:when>

								<c:otherwise>
									<form:form cssClass="form-horizontal" commandName="loginForm">
							Pls copy the generated OTP and use it in the login page


							<div class="form-group">
											<form:label cssClass="col-lg-3  control-label" path="loginId">
												<spring:message text="Login Id" />
											</form:label>
											<div class="class=" col-lg-9controls">
												<form:input path="loginId" />
											</div>
										</div>






										<div class="form-group">
											<form:label cssClass="col-lg-3  control-label" path="otp">
												<spring:message text="otp" />
											</form:label>
											<div class="class=" col-lg-9controls">
												<form:input readonly="true" path="otp" />
											</div>
										</div>





									</form:form>
								</c:otherwise>
							</c:choose>
							<div class="col-lg-9 col-lg-offset-3">

								<a href="#" onclick="js:closeWindow()">go Back</a>
								</td>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>