
<%@ page import="org.nanocan.savanah.library.Entry" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="body">
		<g:set var="entityName" value="${message(code: 'entry.label', default: 'Entry')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-entry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-entry" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="col" title="${message(code: 'entry.col.label', default: 'Col')}" />
					
						<g:sortableColumn property="comment" title="${message(code: 'entry.comment.label', default: 'Comment')}" />
					
						<g:sortableColumn property="controlWell" title="${message(code: 'entry.controlWell.label', default: 'Control Well')}" />
					
						<g:sortableColumn property="deprecated" title="${message(code: 'entry.deprecated.label', default: 'Deprecated')}" />
					
						<g:sortableColumn property="probeId" title="${message(code: 'entry.probeId.label', default: 'Probe Id')}" />
					
						<g:sortableColumn property="productNumber" title="${message(code: 'entry.productNumber.label', default: 'Product Number')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${entryInstanceList}" status="i" var="entryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${entryInstance.id}">${fieldValue(bean: entryInstance, field: "col")}</g:link></td>
					
						<td>${fieldValue(bean: entryInstance, field: "comment")}</td>
					
						<td><g:formatBoolean boolean="${entryInstance.controlWell}" /></td>
					
						<td><g:formatBoolean boolean="${entryInstance.deprecated}" /></td>
					
						<td>${fieldValue(bean: entryInstance, field: "probeId")}</td>
					
						<td>${fieldValue(bean: entryInstance, field: "productNumber")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${entryInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
