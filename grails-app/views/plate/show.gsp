
<%@ page import="org.nanocan.savanah.plates.Plate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'plate.label', default: 'Plate')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-plate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <g:render template="/templates/navmenu"></g:render>
                        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </div>
        </div>
		<div id="show-plate" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list plate">
			
				<g:if test="${plateInstance?.barcode}">
				<li class="fieldcontain">
					<span id="barcode-label" class="property-label"><g:message code="plate.barcode.label" default="Barcode" /></span>
					
						<span class="property-value" aria-labelledby="barcode-label"><g:fieldValue bean="${plateInstance}" field="barcode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="plate.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${plateInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.plateType}">
				<li class="fieldcontain">
					<span id="plateType-label" class="property-label"><g:message code="plate.plateType.label" default="Plate Type" /></span>
					
						<span class="property-value" aria-labelledby="plateType-label"><g:fieldValue bean="${plateInstance}" field="plateType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.parentPlate}">
				<li class="fieldcontain">
					<span id="parentPlate-label" class="property-label"><g:message code="plate.parentPlate.label" default="Parent Plate" /></span>
					
						<span class="property-value" aria-labelledby="parentPlate-label"><g:link controller="plate" action="show" id="${plateInstance?.parentPlate?.id}">${plateInstance?.parentPlate?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.libraryPlate}">
				<li class="fieldcontain">
					<span id="libraryPlate-label" class="property-label"><g:message code="plate.libraryPlate.label" default="Library Plate" /></span>
					
						<span class="property-value" aria-labelledby="libraryPlate-label"><g:link controller="plateLayout" action="show" id="${plateInstance?.libraryPlate?.id}">${plateInstance?.libraryPlate?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.library}">
				<li class="fieldcontain">
					<span id="library-label" class="property-label"><g:message code="plate.library.label" default="Library" /></span>
					
						<span class="property-value" aria-labelledby="library-label"><g:link controller="library" action="show" id="${plateInstance?.library?.id}">${plateInstance?.library?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.family}">
				<li class="fieldcontain">
					<span id="family-label" class="property-label"><g:message code="plate.family.label" default="Family" /></span>
					
						<span class="property-value" aria-labelledby="family-label"><g:fieldValue bean="${plateInstance}" field="family"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.format}">
				<li class="fieldcontain">
					<span id="format-label" class="property-label"><g:message code="plate.format.label" default="Format" /></span>
					
						<span class="property-value" aria-labelledby="format-label"><g:fieldValue bean="${plateInstance}" field="format"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.cols}">
				<li class="fieldcontain">
					<span id="cols-label" class="property-label"><g:message code="plate.cols.label" default="Cols" /></span>
					
						<span class="property-value" aria-labelledby="cols-label"><g:fieldValue bean="${plateInstance}" field="cols"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.layout}">
				<li class="fieldcontain">
					<span id="layout-label" class="property-label"><g:message code="plate.layout.label" default="Layout" /></span>
					
						<span class="property-value" aria-labelledby="layout-label"><g:link controller="plateLayout" action="show" id="${plateInstance?.layout?.id}">${plateInstance?.layout?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.readouts}">
				<li class="fieldcontain">
					<span id="readouts-label" class="property-label"><g:message code="plate.readouts.label" default="Readouts" /></span>
					
						<g:each in="${plateInstance.readouts}" var="r">
						<span class="property-value" aria-labelledby="readouts-label"><g:link controller="readout" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${plateInstance?.rows}">
				<li class="fieldcontain">
					<span id="rows-label" class="property-label"><g:message code="plate.rows.label" default="Rows" /></span>
					
						<span class="property-value" aria-labelledby="rows-label"><g:fieldValue bean="${plateInstance}" field="rows"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${plateInstance?.id}" />
					<g:link class="edit" action="edit" id="${plateInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
