
<%@ page import="org.nanocan.savanah.library.LibraryPlate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'libraryPlate.label', default: 'LibraryPlate')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-libraryPlate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-libraryPlate" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="format" title="${message(code: 'libraryPlate.format.label', default: 'Format')}" />
					
						<g:sortableColumn property="plateIndex" title="${message(code: 'libraryPlate.plateIndex.label', default: 'Plate Index')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${libraryPlateInstanceList}" status="i" var="libraryPlateInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${libraryPlateInstance.id}">${fieldValue(bean: libraryPlateInstance, field: "format")}</g:link></td>
					
						<td>${fieldValue(bean: libraryPlateInstance, field: "plateIndex")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${libraryPlateInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
