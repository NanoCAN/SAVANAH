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

<div class="fieldcontain ${hasErrors(bean: libraryInstance, field: 'plates', 'error')} ">
	<label for="plates">
		<g:message code="library.plates.label" default="Plates" />
		
	</label>
	<g:select name="plates" from="${org.nanocan.savanah.library.LibraryPlate.list()}" multiple="multiple" optionKey="id" size="5" value="${libraryInstance?.plates*.id}" class="many-to-many"/>
</div>

