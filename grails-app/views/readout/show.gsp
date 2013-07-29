
<%@ page import="org.nanocan.savanah.plates.Readout" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'readout.label', default: 'Readout')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-readout" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-readout" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list readout">
			
				<g:if test="${readoutInstance?.typeOfReadout}">
				<li class="fieldcontain">
					<span id="typeOfReadout-label" class="property-label"><g:message code="readout.typeOfReadout.label" default="Type Of Readout" /></span>
					
						<span class="property-value" aria-labelledby="typeOfReadout-label"><g:fieldValue bean="${readoutInstance}" field="typeOfReadout"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${readoutInstance?.assayType}">
				<li class="fieldcontain">
					<span id="assayType-label" class="property-label"><g:message code="readout.assayType.label" default="Assay Type" /></span>
					
						<span class="property-value" aria-labelledby="assayType-label"><g:fieldValue bean="${readoutInstance}" field="assayType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${readoutInstance?.plate}">
				<li class="fieldcontain">
					<span id="plate-label" class="property-label"><g:message code="readout.plate.label" default="Plate" /></span>
					
						<span class="property-value" aria-labelledby="plate-label"><g:link controller="plate" action="show" id="${readoutInstance?.plate?.id}">${readoutInstance?.plate?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${readoutInstance?.wavelength}">
				<li class="fieldcontain">
					<span id="wavelength-label" class="property-label"><g:message code="readout.wavelength.label" default="Wavelength" /></span>
					
						<span class="property-value" aria-labelledby="wavelength-label"><g:fieldValue bean="${readoutInstance}" field="wavelength"/></span>
					
				</li>
				</g:if>

                <g:if test="${readoutInstance?.resultFile}">
                    <li class="fieldcontain">
                        <span id="resultFile-label" class="property-label"><g:message code="slide.resultFile.label" default="Result File" /></span>

                        <span class="property-value" aria-labelledby="resultFile-label"><g:link controller="resultFile" action="show" id="${readoutInstance?.resultFile?.id}">${readoutInstance?.resultFile?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${readoutInstance?.resultImage}">
                    <li class="fieldcontain">
                        <span id="resultImage-label" class="property-label"><g:message code="slide.resultImage.label" default="Result Image" /></span>

                        <span class="property-value" aria-labelledby="resultImage-label"><g:link controller="resultFile" action="show" id="${readoutInstance?.resultImage?.id}">${readoutInstance?.resultImage?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${readoutInstance?.protocol}">
                    <li class="fieldcontain">
                        <span id="protocol-label" class="property-label"><g:message code="slide.protocol.label" default="Protocol" /></span>

                        <span class="property-value" aria-labelledby="protocol-label"><g:link controller="resultFile" action="show" id="${readoutInstance?.protocol?.id}">${readoutInstance?.protocol?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${readoutInstance?.id}" />
					<g:link class="edit" action="edit" id="${readoutInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
