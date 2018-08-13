<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>





	<form:form action="welcome/procesar/save.do" >
		
		<div class="form-group" style="width: 20%;">
		<form:errors path="*" class="has-error" />

			
			
			<label> <spring:message code="weka.text" />
			</label><br /> <input class="form-control"
				type="text" name="ruta"  />
			</div>
			
	  <spring:message code="actor.procces" var="actorSaveHeader"/>
		<spring:message code="actor.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.location='welcome/index.do'" class="btn btn-danger" type="button" name="cancel" value="${actorCancelHeader}"/>

	</form:form>

