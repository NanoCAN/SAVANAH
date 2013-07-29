
<%@ page import="org.nanocan.savanah.plates.Readout" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'readout.label', default: 'Readout')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-readout" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
            <div class="navbar">
                <div class="navbar-inner">
                    <div class="container">
                        <ul class="nav">
                            <g:render template="/templates/navmenu"></g:render>
                            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                        </ul>
                    </div>
                </div>
            </div>
		<div id="list-readout" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="typeOfReadout" title="${message(code: 'readout.typeOfReadout.label', default: 'Type Of Readout')}" />
					
						<g:sortableColumn property="assayType" title="${message(code: 'readout.assayType.label', default: 'Assay Type')}" />
					
						<th><g:message code="readout.plate.label" default="Plate" /></th>
					
						<g:sortableColumn property="wavelength" title="${message(code: 'readout.wavelength.label', default: 'Wavelength')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${readoutInstanceList}" status="i" var="readoutInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${readoutInstance.id}">${fieldValue(bean: readoutInstance, field: "typeOfReadout")}</g:link></td>
					
						<td>${fieldValue(bean: readoutInstance, field: "assayType")}</td>
					
						<td>${fieldValue(bean: readoutInstance, field: "plate")}</td>
					
						<td>${fieldValue(bean: readoutInstance, field: "wavelength")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${readoutInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
