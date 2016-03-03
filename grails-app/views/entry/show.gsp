
<%@ page import="org.nanocan.savanah.library.Entry" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="body">
		<g:set var="entityName" value="${message(code: 'entry.label', default: 'Entry')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-entry" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list entry">
			
				<g:if test="${entryInstance?.col}">
				<li class="fieldcontain">
					<span id="col-label" class="property-label"><g:message code="entry.col.label" default="Col" /></span>
					
						<span class="property-value" aria-labelledby="col-label"><g:fieldValue bean="${entryInstance}" field="col"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${entryInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="entry.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${entryInstance}" field="comment"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${entryInstance?.controlWell}">
				<li class="fieldcontain">
					<span id="controlWell-label" class="property-label"><g:message code="entry.controlWell.label" default="Control Well" /></span>
					
						<span class="property-value" aria-labelledby="controlWell-label"><g:formatBoolean boolean="${entryInstance?.controlWell}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${entryInstance?.deprecated}">
				<li class="fieldcontain">
					<span id="deprecated-label" class="property-label"><g:message code="entry.deprecated.label" default="Deprecated" /></span>
					
						<span class="property-value" aria-labelledby="deprecated-label"><g:formatBoolean boolean="${entryInstance?.deprecated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${entryInstance?.probeId}">
				<li class="fieldcontain">
					<span id="probeId-label" class="property-label"><g:message code="entry.probeId.label" default="Probe Id" /></span>
					
						<span class="property-value" aria-labelledby="probeId-label"><g:fieldValue bean="${entryInstance}" field="probeId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${entryInstance?.productNumber}">
				<li class="fieldcontain">
					<span id="productNumber-label" class="property-label"><g:message code="entry.productNumber.label" default="Product Number" /></span>
					
						<span class="property-value" aria-labelledby="productNumber-label"><g:fieldValue bean="${entryInstance}" field="productNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${entryInstance?.row}">
				<li class="fieldcontain">
					<span id="row-label" class="property-label"><g:message code="entry.row.label" default="Row" /></span>
					
						<span class="property-value" aria-labelledby="row-label"><g:fieldValue bean="${entryInstance}" field="row"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${entryInstance?.sample}">
				<li class="fieldcontain">
					<span id="sample-label" class="property-label"><g:message code="entry.sample.label" default="Sample" /></span>
					
						<span class="property-value" aria-labelledby="sample-label"><g:link controller="sample" action="show" id="${entryInstance?.sample?.id}">${entryInstance?.sample?.encodeAsHTML()}</g:link></span>
					
				</li>

				<g:each in="${entryInstance?.sample?.identifiers}" var="identifier">
				<li class="fieldcontain">
					<span id="sample-label" class="property-label">${identifier?.type}</span>

					<span class="property-value" aria-labelledby="sample-label"><g:accessionFormat accession="${identifier?.accessionNumber}" type="${identifier?.type}"/></span>

				</li>
				</g:each>
				</g:if>
			
				<g:if test="${entryInstance?.wellPosition}">
				<li class="fieldcontain">
					<span id="wellPosition-label" class="property-label"><g:message code="entry.wellPosition.label" default="Well Position" /></span>
					
						<span class="property-value" aria-labelledby="wellPosition-label"><g:fieldValue bean="${entryInstance}" field="wellPosition"/></span>
					
				</li>
				</g:if>
			
			</ol>
		</div>
	</body>
</html>
