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


<p><spring:message code="comment.jornada" /> ${num}</p>





<display:table  keepStatus="true" name="comment"
	requestURI="${requestURI}" id="row" class="table table-over">
	
	<spring:message code="comment.user" var="user"/>
	<display:column property="user.name" title="${user}"
		sortable="false" />
	
	<spring:message code="comment.comment" var="comment"/>
	<display:column property="comment" title="${comment}"
		sortable="false" />
	
	<spring:message code="comment.commentprocess" var="commentprocess"/>		
	<display:column property="commentProcess.commentProcess" title="${commentprocess}"
		sortable="false" />

</display:table>




