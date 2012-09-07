
<%@ page import="org.nanocan.dart.Run" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'run.label', default: 'Run')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-run" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-run" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="startTime" title="${message(code: 'run.startTime.label', default: 'Start Time')}" />
					
						<g:sortableColumn property="method" title="${message(code: 'run.method.label', default: 'Method')}" />
					
						<g:sortableColumn property="completed" title="${message(code: 'run.completed.label', default: 'Completed')}" />
					
						<g:sortableColumn property="application" title="${message(code: 'run.application.label', default: 'Application')}" />
					
						<g:sortableColumn property="instrumentName" title="${message(code: 'run.instrumentName.label', default: 'Instrument Name')}" />
					
						<g:sortableColumn property="preparations" title="${message(code: 'run.preparations.label', default: 'Preparations')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${runInstanceList}" status="i" var="runInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${runInstance.id}">${fieldValue(bean: runInstance, field: "startTime")}</g:link></td>
					
						<td>${fieldValue(bean: runInstance, field: "method")}</td>
					
						<td><g:formatBoolean boolean="${runInstance.completed}" /></td>
					
						<td>${fieldValue(bean: runInstance, field: "application")}</td>
					
						<td>${fieldValue(bean: runInstance, field: "instrumentName")}</td>
					
						<td>${fieldValue(bean: runInstance, field: "preparations")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${runInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
