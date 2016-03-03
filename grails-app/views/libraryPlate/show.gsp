
<%@ page import="org.nanocan.savanah.library.LibraryPlate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'libraryPlate.label', default: 'LibraryPlate')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-libraryPlate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <g:render template="/templates/navmenu"></g:render>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </div>
        </div>

		<div id="show-libraryPlate" class="content scaffold-show" role="main">
			<div id="entryDiv" style="padding-top:100px;width:400px;height:400px;float:right;">
			</div>
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list libraryPlate">
			
				<g:if test="${libraryPlateInstance?.format}">
				<li class="fieldcontain">
					<span id="format-label" class="property-label"><g:message code="libraryPlate.format.label" default="Format" /></span>
					
						<span class="property-value" aria-labelledby="format-label"><g:fieldValue bean="${libraryPlateInstance}" field="format"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${libraryPlateInstance?.entries}">
				<li class="fieldcontain">
					<span id="entries-label" class="property-label"><g:message code="libraryPlate.entries.label" default="Entries" /></span>

						<div id="entryList" style="width:400px;height:400px;overflow:scroll;">
							<g:each in="${libraryPlateInstance.entries.sort()}" var="e">
								<span class="property-value" aria-labelledby="entries-label">
								${e?.wellPosition}: <g:remoteLink update="entryDiv" controller="entry" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:remoteLink>
								</span>
							</g:each>
						</div>
				</li>
				</g:if>
			
				<g:if test="${libraryPlateInstance?.plateIndex}">
				<li class="fieldcontain">
					<span id="plateIndex-label" class="property-label"><g:message code="libraryPlate.plateIndex.label" default="Plate Index" /></span>
					
						<span class="property-value" aria-labelledby="plateIndex-label"><g:fieldValue bean="${libraryPlateInstance}" field="plateIndex"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${libraryPlateInstance?.id}" />
					<g:link class="edit" action="edit" id="${libraryPlateInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
