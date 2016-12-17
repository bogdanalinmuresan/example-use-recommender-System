<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="java.util.ArrayList"%>
    


    
 
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="http://www.w3schools.com/lib/w3.css" />" rel="stylesheet" type="text/css"/>
	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="w3-container"> 
	
	<!-- ********************************************************************************** -->
	
		<h2>Recomendaciones para ti</h2>
		<div class="w3-row">
			<c:forEach items="${PeliculasRecomendadasUrl}" var="PelisRecomendadas">
				<!--Esto se va a repetir para las peliculas recomendadas -->
				<div class="w3-col m2 w3-green w3-center w3-margin">
					<img src=" ${PelisRecomendadas}" style="width:100%">
				</div>
	
			</c:forEach>
		</div>
			
	<!-- ********************************************************************************** -->
	
		<h2>Peliculas Vistas</h2>
		<div class="w3-row">
			<c:forEach items="${peliculasVistasUrl}" var="PelisVistas">
				<!--Esto se va a repetir para las peliculas vistas -->
				<div class="w3-col m2 w3-green w3-center w3-margin">
						<img src="${PelisVistas}" style="width:100%">
				</div>
			</c:forEach>
		</div>
			
		
		
	<!-- ********************************************************************************** -->
	</div>
	
	 
</body>
</html>