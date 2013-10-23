
<%@ page import="org.nanocan.savanah.library.Library" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'library.label', default: 'Library')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-library" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-library" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'library.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="type" title="${message(code: 'library.type.label', default: 'Type')}" />
					
						<th><g:message code="library.createdBy.label" default="Created By" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'library.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'library.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="library.lastUpdatedBy.label" default="Last Updated By" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${libraryInstanceList}" status="i" var="libraryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${libraryInstance.id}">${fieldValue(bean: libraryInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: libraryInstance, field: "type")}</td>
					
						<td>${fieldValue(bean: libraryInstance, field: "createdBy")}</td>
					
						<td><g:formatDate date="${libraryInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${libraryInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: libraryInstance, field: "lastUpdatedBy")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${libraryInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
