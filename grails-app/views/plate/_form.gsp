<%@ page import="org.nanocan.savanah.plates.Plate" %>



<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'barcode', 'error')} ">
	<label for="barcode">
		<g:message code="plate.barcode.label" default="Barcode" />
		
	</label>
	<g:textField name="barcode" value="${plateInstance?.barcode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="plate.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${plateInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'plateType', 'error')} ">
	<label for="plateType">
		<g:message code="plate.plateType.label" default="Plate Type" />
		
	</label>
	<g:select id="plateType" name="plateType.id" from="${org.nanocan.savanah.plates.PlateType.list()}" optionKey="id" value="${plateInstance?.plateType?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'family', 'error')} ">
	<label for="family">
		<g:message code="plate.family.label" default="Family" />
		
	</label>
	<g:select name="family" from="${plateInstance.constraints.family.inList}" value="${plateInstance?.family?:'daughter'}" valueMessagePrefix="plate.family" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'format', 'error')} required">
	<label for="format">
		<g:message code="plate.format.label" default="Format" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="format" from="${plateInstance.constraints.format.inList}" required="" value="${plateInstance?.format}" valueMessagePrefix="plate.format"/>
</div>


<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'layout', 'error')} required">
	<label for="layout">
		<g:message code="plate.layout.label" default="Layout" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="layout" name="layout.id" from="${org.nanocan.savanah.plates.PlateLayout.list()}" optionKey="id" required="" value="${plateInstance?.layout?.id}" class="many-to-one"/>
</div>


