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
	function register() {
		document.forms[0].action = "registerSubmit";
		document.forms[0].submit();

	}

	function uploadPhoto() {
		var loginId = document.forms[0].loginId.value;
		if (null == loginId || '' == loginId) {
			document.getElementById("errors").innerHTML = "Please enter loginId before Uploading";
		} else {
			document.getElementById("errors").innerHTML = "";
			var url = "uploadPhoto?loginId=" + loginId;
			window.open(url, "Upload Photo", "width=600,height=500");
		}

	}
</script>

<title>Register Page</title>
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
						<form:form cssClass="form-horizontal" action="/registerSubmit"
							commandName="loginForm">
							<div>
								<div class="error" id="errors">
									<c:if test="${not empty errorMessage}">
										<c:out value="${errorMessage}" />
									</c:if>
								</div>
							</div>
							<fieldset>
								<legend>User Registeration Form</legend>

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

									<form:label cssClass="col-lg-3  control-label" path="password">
										<spring:message text="Password" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:password cssClass="form-control" path="password" />
										<form:errors path="password" cssClass="error" />
									</div>

								</div>


								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="userName">
										<spring:message text="UserName" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="userName" />
										<form:errors path="userName" cssClass="error" />
									</div>

								</div>


								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="email">
										<spring:message text="email" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="email" />
										<form:errors path="email" cssClass="error" />
									</div>

								</div>

								<legend>Address</legend>




								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label"
										path="houseNumber">
										<spring:message text="House No" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="houseNumber" />
										<form:errors path="houseNumber" cssClass="error" />
									</div>

								</div>



								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="street">
										<spring:message text="Street" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="street" />
										<form:errors path="street" cssClass="error" />
									</div>

								</div>



								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="city">
										<spring:message text="city" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="city" />
										<form:errors path="city" cssClass="error" />
									</div>

								</div>


								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="state">
										<spring:message text="State" />
									</form:label>



									<div class="col-lg-9 controls">
										<c:set var="selectedState" value="${loginForm.state}" />
										<form:select cssClass="form-control" path="state">
											<form:option value="0" label="--- Select ---" />
											<c:forEach var="item" items="${loginForm.states}">

												<option value="${item.stateId}"
													${item.stateId == selectedState ? 'selected="selected"' : ''}>${item.stateName}</option>
											</c:forEach>
										</form:select>
										<form:errors path="state" cssClass="error" />
									</div>

								</div>


								<div class="form-group">

									<form:label cssClass="col-lg-3  control-label" path="country">
										<spring:message text="Country" />
									</form:label>


									<div class="col-lg-9 controls">
										<form:input cssClass="form-control" path="country" />
										<form:errors path="country" cssClass="error" />
									</div>

								</div>

								<div class="col-lg-9 col-lg-offset-3">

									<a class="btn btn-default" id="register"
										onClick="js:register();" href="#">Submit</a> <a href="#"
										style="margin-left: 10px" onClick="js:uploadPhoto()">Upload
										Photo</a>
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