<%@ page import="org.nanocan.savanah.experiment.Project" %>



<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="project.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${projectInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'createdBy', 'error')} required">
	<label for="createdBy">
		<g:message code="project.createdBy.label" default="Created By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="createdBy" name="createdBy.id" from="${org.nanocan.savanah.security.Person.list()}" optionKey="id" required="" value="${projectInstance?.createdBy?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'experiments', 'error')} ">
	<label for="experiments">
		<g:message code="project.experiments.label" default="Experiments" />
		
	</label>
	<g:select name="experiments" from="${org.nanocan.savanah.experiment.Experiment.list()}" multiple="multiple" optionKey="id" size="5" value="${projectInstance?.experiments*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'lastUpdatedBy', 'error')} required">
	<label for="lastUpdatedBy">
		<g:message code="project.lastUpdatedBy.label" default="Last Updated By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="lastUpdatedBy" name="lastUpdatedBy.id" from="${org.nanocan.savanah.security.Person.list()}" optionKey="id" required="" value="${projectInstance?.lastUpdatedBy?.id}" class="many-to-one"/>
</div>

