<%@ page import="org.nanocan.dart.Run" %>



<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'startTime', 'error')} required">
	<label for="startTime">
		<g:message code="run.startTime.label" default="Start Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startTime" precision="day"  value="${runInstance?.startTime}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'method', 'error')} ">
	<label for="method">
		<g:message code="run.method.label" default="Method" />
		
	</label>
	<g:textField name="method" value="${runInstance?.method}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'completed', 'error')} ">
	<label for="completed">
		<g:message code="run.completed.label" default="Completed" />
		
	</label>
	<g:checkBox name="completed" value="${runInstance?.completed}" />
</div>

<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'application', 'error')} ">
	<label for="application">
		<g:message code="run.application.label" default="Application" />
		
	</label>
	<g:textField name="application" value="${runInstance?.application}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'instrumentName', 'error')} ">
	<label for="instrumentName">
		<g:message code="run.instrumentName.label" default="Instrument Name" />
		
	</label>
	<g:textField name="instrumentName" value="${runInstance?.instrumentName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'preparations', 'error')} ">
	<label for="preparations">
		<g:message code="run.preparations.label" default="Preparations" />
		
	</label>
	<g:textField name="preparations" value="${runInstance?.preparations}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'project', 'error')} ">
	<label for="project">
		<g:message code="run.project.label" default="Project" />
		
	</label>
	<g:textField name="project" value="${runInstance?.project}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: runInstance, field: 'techniques', 'error')} ">
	<label for="techniques">
		<g:message code="run.techniques.label" default="Techniques" />
		
	</label>
	<g:textField name="techniques" value="${runInstance?.techniques}"/>
</div>

