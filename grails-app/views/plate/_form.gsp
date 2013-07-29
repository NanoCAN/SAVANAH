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
	<g:textField name="plateType" value="${plateInstance?.plateType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'parentPlate', 'error')} ">
	<label for="parentPlate">
		<g:message code="plate.parentPlate.label" default="Parent Plate" />
		
	</label>
	<g:select id="parentPlate" name="parentPlate.id" from="${org.nanocan.savanah.plates.Plate.list()}" optionKey="id" value="${plateInstance?.parentPlate?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'libraryPlate', 'error')} ">
	<label for="libraryPlate">
		<g:message code="plate.libraryPlate.label" default="Library Plate" />
		
	</label>
	<g:select id="libraryPlate" name="libraryPlate.id" from="${org.nanocan.layout.PlateLayout.list()}" optionKey="id" value="${plateInstance?.libraryPlate?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'library', 'error')} ">
	<label for="library">
		<g:message code="plate.library.label" default="Library" />
		
	</label>
	<g:select id="library" name="library.id" from="${org.nanocan.savanah.library.Library.list()}" optionKey="id" value="${plateInstance?.library?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'family', 'error')} ">
	<label for="family">
		<g:message code="plate.family.label" default="Family" />
		
	</label>
	<g:select name="family" from="${plateInstance.constraints.family.inList}" value="${plateInstance?.family}" valueMessagePrefix="plate.family" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'format', 'error')} required">
	<label for="format">
		<g:message code="plate.format.label" default="Format" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="format" from="${plateInstance.constraints.format.inList}" required="" value="${plateInstance?.format}" valueMessagePrefix="plate.format"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'cols', 'error')} required">
	<label for="cols">
		<g:message code="plate.cols.label" default="Cols" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="cols" required="" value="${plateInstance.cols}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'layout', 'error')} required">
	<label for="layout">
		<g:message code="plate.layout.label" default="Layout" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="layout" name="layout.id" from="${org.nanocan.layout.PlateLayout.list()}" optionKey="id" required="" value="${plateInstance?.layout?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'readouts', 'error')} ">
	<label for="readouts">
		<g:message code="plate.readouts.label" default="Readouts" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${plateInstance?.readouts?}" var="r">
    <li><g:link controller="readout" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="readout" action="create" params="['plate.id': plateInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'readout.label', default: 'Readout')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: plateInstance, field: 'rows', 'error')} required">
	<label for="rows">
		<g:message code="plate.rows.label" default="Rows" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="rows" required="" value="${plateInstance.rows}"/>
</div>

