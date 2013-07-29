<%@ page import="org.nanocan.savanah.plates.ResultFileConfig" %>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="resultFileConfig.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${resultFileConfigInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'wellPositionCol', 'error')} ">
    <label for="blockCol">
        <g:message code="resultFileConfig.wellPositionCol.label" default="Well Position Col"/>

    </label>
    <g:textField name="wellPositionCol" value="${resultFileConfigInstance?.wellPositionCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'columnCol', 'error')} ">
    <label for="columnCol">
        <g:message code="resultFileConfig.columnCol.label" default="Column Col"/>

    </label>
    <g:textField name="columnCol" value="${resultFileConfigInstance?.columnCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'rowCol', 'error')} ">
    <label for="rowCol">
        <g:message code="resultFileConfig.rowCol.label" default="Row Col"/>

    </label>
    <g:textField name="rowCol" value="${resultFileConfigInstance?.rowCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'measuredValueCol', 'error')} ">
    <label for="measuredValueCol">
        <g:message code="resultFileConfig.measuredValueCol.label" default="Measured Value Col"/>

    </label>
    <g:textField name="measuredValueCol" value="${resultFileConfigInstance?.measuredValueCol}"/>
</div>

