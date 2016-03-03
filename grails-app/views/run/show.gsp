
<%@ page import="org.nanocan.dart.Run" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'run.label', default: 'Run')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-run" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-run" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list run">
			
				<g:if test="${runInstance?.startTime}">
				<li class="fieldcontain">
					<span id="startTime-label" class="property-label"><g:message code="run.startTime.label" default="Start Time" /></span>
					
						<span class="property-value" aria-labelledby="startTime-label"><g:formatDate date="${runInstance?.startTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${runInstance?.method}">
				<li class="fieldcontain">
					<span id="method-label" class="property-label"><g:message code="run.method.label" default="Method" /></span>
					
						<span class="property-value" aria-labelledby="method-label"><g:fieldValue bean="${runInstance}" field="method"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${runInstance?.completed}">
				<li class="fieldcontain">
					<span id="completed-label" class="property-label"><g:message code="run.completed.label" default="Completed" /></span>
					
						<span class="property-value" aria-labelledby="completed-label"><g:formatBoolean boolean="${runInstance?.completed}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${runInstance?.application}">
				<li class="fieldcontain">
					<span id="application-label" class="property-label"><g:message code="run.application.label" default="Application" /></span>
					
						<span class="property-value" aria-labelledby="application-label"><g:fieldValue bean="${runInstance}" field="application"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${runInstance?.instrumentName}">
				<li class="fieldcontain">
					<span id="instrumentName-label" class="property-label"><g:message code="run.instrumentName.label" default="Instrument Name" /></span>
					
						<span class="property-value" aria-labelledby="instrumentName-label"><g:fieldValue bean="${runInstance}" field="instrumentName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${runInstance?.preparations}">
				<li class="fieldcontain">
					<span id="preparations-label" class="property-label"><g:message code="run.preparations.label" default="Preparations" /></span>
					
						<span class="property-value" aria-labelledby="preparations-label"><g:fieldValue bean="${runInstance}" field="preparations"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${runInstance?.project}">
				<li class="fieldcontain">
					<span id="project-label" class="property-label"><g:message code="run.project.label" default="Project" /></span>
					
						<span class="property-value" aria-labelledby="project-label"><g:fieldValue bean="${runInstance}" field="project"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${runInstance?.techniques}">
				<li class="fieldcontain">
					<span id="techniques-label" class="property-label"><g:message code="run.techniques.label" default="Techniques" /></span>
					
						<span class="property-value" aria-labelledby="techniques-label"><g:fieldValue bean="${runInstance}" field="techniques"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${runInstance?.id}" />
					<g:link class="edit" action="edit" id="${runInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
