<%@ page import="org.nanocan.savanah.library.Entry" %>



<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'col', 'error')} required">
	<label for="col">
		<g:message code="entry.col.label" default="Col" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="col" required="" value="${entryInstance.col}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="entry.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${entryInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'controlWell', 'error')} ">
	<label for="controlWell">
		<g:message code="entry.controlWell.label" default="Control Well" />
		
	</label>
	<g:checkBox name="controlWell" value="${entryInstance?.controlWell}" />
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'deprecated', 'error')} ">
	<label for="deprecated">
		<g:message code="entry.deprecated.label" default="Deprecated" />
		
	</label>
	<g:checkBox name="deprecated" value="${entryInstance?.deprecated}" />
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'probeId', 'error')} ">
	<label for="probeId">
		<g:message code="entry.probeId.label" default="Probe Id" />
		
	</label>
	<g:textField name="probeId" value="${entryInstance?.probeId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'productNumber', 'error')} ">
	<label for="productNumber">
		<g:message code="entry.productNumber.label" default="Product Number" />
		
	</label>
	<g:textField name="productNumber" value="${entryInstance?.productNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'row', 'error')} required">
	<label for="row">
		<g:message code="entry.row.label" default="Row" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="row" required="" value="${entryInstance.row}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'sample', 'error')} required">
	<label for="sample">
		<g:message code="entry.sample.label" default="Sample" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sample" name="sample.id" from="${org.nanocan.layout.Sample.list()}" optionKey="id" required="" value="${entryInstance?.sample?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'wellPosition', 'error')} ">
	<label for="wellPosition">
		<g:message code="entry.wellPosition.label" default="Well Position" />
		
	</label>
	<g:textField name="wellPosition" value="${entryInstance?.wellPosition}"/>
</div>

