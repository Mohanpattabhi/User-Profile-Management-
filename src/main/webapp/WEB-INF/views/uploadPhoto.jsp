<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>

<title>Upload Photo</title>
<script>
function uploadPhoto(){

	document.forms[0].action="uploadFile";
	document.forms[0].submit();
				
}


</script>
 <c:set var="fromPage"  value="${fromPage}"/>
</head>
<body>
<b>Profile Picture</b><br/><br /> <br />
<c:choose>
<c:when test='${fromPage  eq "upload"}'>
          ${photo_status_message}

  </c:when>
  <c:otherwise>
        <form method="POST" action="uploadFile" enctype="multipart/form-data">
 
     
        <input type="file" name="file"><br /> <br />
       <input type="hidden" name="loginId" id="loginId" value="${loginId}"><br /> <br /> 
        <input type="button" value="Upload Photo" onClick="uploadPhoto()"> 
    </form>
    </c:otherwise>
    </c:choose>  
</body>
</html>