<%@ page import="org.nanocan.plates.PlateType; org.nanocan.layout.CellLine; org.nanocan.layout.Inducer; org.nanocan.layout.NumberOfCellsSeeded; org.nanocan.layout.Treatment; org.nanocan.savanah.library.Library; org.nanocan.project.Experiment" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'experiment.label', default: 'Experiment')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<a href="#create-experiment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
    </div>
</div>
<div id="create-experiment" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:form>
        <fieldset class="form">
            <div class="fieldcontain required">
                <label for="plateType">
                    Plate Type
                    <span class="required-indicator">*</span>
                </label>
                <g:select name="plateType.id" optionKey="id" from="${org.nanocan.plates.PlateType.list()}"/>
            </div>
            <div class="fieldcontain required">
                <label for="cellLine">
                    Cell Line
                    <span class="required-indicator">*</span>
                </label>
                <g:select name="cellLine.id" optionKey="id" from="${org.nanocan.layout.CellLine.list()}"/>
            </div>
            <div class="fieldcontain required">
                <label for="inducer">
                    Inducer
                    <span class="required-indicator">*</span>
                </label>
                <g:select name="inducer.id" optionKey="id" from="${org.nanocan.layout.Inducer.list()}"/>
            </div>
            <div class="fieldcontain required">
                <label for="Amount of cells / Number of cells seeded">
                    Amount of cells / Number of cells seeded
                    <span class="required-indicator">*</span>
                </label>
                <g:select name="numberOfCellsSeeded.id" optionKey="id" from="${org.nanocan.layout.NumberOfCellsSeeded.list()}"/>
            </div>
            <div class="fieldcontain required">
                <label for="treatment">
                    Treatment
                    <span class="required-indicator">*</span>
                </label>
                <g:select name="treatment.id" optionKey="id" from="${org.nanocan.layout.Treatment.list()}"/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="back" value="Back" />
            <g:submitButton name="continue" class="save" value="Continue with selecting controls" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
