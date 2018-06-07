<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<p><spring:message code="day.num" /> ${num}</p>


<spring:message code="actor.save" var="actorSaveHeader"/>
<spring:message code="actor.generar" var="generar"/>



<spring:message code="message.matchForecast"/>
<display:table  keepStatus="true" name="matchForecast"
	requestURI="${requestURI}" id="row" class="table table-over">
	
	<spring:message code="matchForecast.local" var="local"/>
	<display:column property="local" title="${local}"
		sortable="false" />
	
	<spring:message code="matchForecast.resultLocal" var="resultLocal"/>		
	<display:column property="resultLocal" title="${resultLocal}"
		sortable="false" />
	
	<spring:message code="matchForecast.resultVisit" var="resultVisit"/>		
	<display:column property="resultVisit" title="${resultVisit}"
		sortable="false" />
		
	<spring:message code="matchForecast.visit" var="visit"/>
	<display:column property="visit" title="${visit}"
		sortable="false" />
	
	<spring:message code="matchForecast.actualization" var="actualization"/>
	<display:column property="actualization" title="${actualization}"
		sortable="false" />
		
	

</display:table>

<spring:message code="message.matchFinal"/>
<display:table  keepStatus="true" name="matchFinal"
	requestURI="${requestURI}" id="row" class="table table-over">
	
	<spring:message code="matchForecast.local" var="local"/>
	<display:column property="local" title="${local}"
		sortable="false" />
	
	<spring:message code="matchForecast.resultLocal" var="resultLocal"/>		
	<display:column property="resultLocal" title="${resultLocal}"
		sortable="false" />
	
	<spring:message code="matchForecast.resultVisit" var="resultVisit"/>		
	<display:column property="resultVisit" title="${resultVisit}"
		sortable="false" />
		
	<spring:message code="matchForecast.visit" var="visit"/>
	<display:column property="visit" title="${visit}"
		sortable="false" />
	
		
	

</display:table>




<input onclick="window.location='welcome/cargar.do'" class="btn btn-warning" type="button" name="save" value="${actorSaveHeader}"/> 
<input onclick="window.location='welcome/procesar.do'" class="btn btn-warning" type="button" name="Generar archivo Weka" value="${generar}"/>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 




