<%@ page import="org.nanocan.savanah.experiment.Experiment" %>



<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="experiment.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${experimentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'experimenter', 'error')} required">
	<label for="experimenter">
		<g:message code="experiment.experimenter.label" default="Experimenter" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="experimenter" name="experimenter.id" from="${org.nanocan.savanah.security.Person.list()}" optionKey="id" required="" value="${experimentInstance?.experimenter?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'dateOfExperiment', 'error')} required">
	<label for="dateOfExperiment">
		<g:message code="experiment.dateOfExperiment.label" default="Date Of Experiment" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateOfExperiment" precision="day"  value="${experimentInstance?.dateOfExperiment}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'createdBy', 'error')} required">
	<label for="createdBy">
		<g:message code="experiment.createdBy.label" default="Created By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="createdBy" name="createdBy.id" from="${org.nanocan.savanah.security.Person.list()}" optionKey="id" required="" value="${experimentInstance?.createdBy?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'lastUpdatedBy', 'error')} required">
	<label for="lastUpdatedBy">
		<g:message code="experiment.lastUpdatedBy.label" default="Last Updated By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="lastUpdatedBy" name="lastUpdatedBy.id" from="${org.nanocan.savanah.security.Person.list()}" optionKey="id" required="" value="${experimentInstance?.lastUpdatedBy?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'plates', 'error')} ">
	<label for="plates">
		<g:message code="experiment.plates.label" default="Plates" />
		
	</label>
	<g:select name="plates" from="${org.nanocan.savanah.plates.Plate.list()}" multiple="multiple" optionKey="id" size="5" value="${experimentInstance?.plates*.id}" class="many-to-many"/>
</div>

