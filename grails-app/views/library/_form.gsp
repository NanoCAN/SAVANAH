<%@ page import="org.nanocan.savanah.library.Library" %>



<div class="fieldcontain ${hasErrors(bean: libraryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="library.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${libraryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: libraryInstance, field: 'type', 'error')} ">
	<label for="type">
		<g:message code="library.type.label" default="Type" />
		
	</label>
	<g:select name="type" from="${libraryInstance.constraints.type.inList}" value="${libraryInstance?.type}" valueMessagePrefix="library.type" noSelection="['': '']"/>
</div>

	<g:hiddenField name="plates" value="${libraryInstance?.plates*.id}" class="many-to-many"/>


