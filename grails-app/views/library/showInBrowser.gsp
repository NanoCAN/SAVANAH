
<%@ page import="org.nanocan.savanah.library.Library" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="body">
		<g:set var="entityName" value="${message(code: 'library.label', default: 'Library')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-library" class="content scaffold-show" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list library">
			
				<g:if test="${libraryInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="library.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${libraryInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${libraryInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="library.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${libraryInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${libraryInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="library.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:link controller="person" action="show" id="${libraryInstance?.createdBy?.id}">${libraryInstance?.createdBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${libraryInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="library.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${libraryInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${libraryInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="library.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${libraryInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${libraryInstance?.lastUpdatedBy}">
				<li class="fieldcontain">
					<span id="lastUpdatedBy-label" class="property-label"><g:message code="library.lastUpdatedBy.label" default="Last Updated By" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdatedBy-label"><g:link controller="person" action="show" id="${libraryInstance?.lastUpdatedBy?.id}">${libraryInstance?.lastUpdatedBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
				<br/><br/>
				<fieldset class="buttons">
					<g:link class="save" id="${libraryInstance?.id}" controller="dilutedLibrary" action="browser">Add library dilution series</g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</ol>

		</div>
	</body>
</html>
