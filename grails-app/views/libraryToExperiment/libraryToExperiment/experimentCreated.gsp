<%@ page import="org.nanocan.savanah.library.Library; org.nanocan.project.Experiment" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">

    <title>Experiment created</title>
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
    <h1>Experiment</h1>

    <div class="message" role="status">Experiment created successfully</div>
    <fieldset class="buttons">
        <g:submitButton name="showExperiment" class="show" controller="experiment" action="show" id="experimentInstance.id">Show experiment</g:submitButton>
        <g:submitButton name="createFromZip" class="save" controller="readout" action="createFromZippedFile">Bulk upload of readout data</g:submitButton>
    </fieldset>
</div>
</body>
</html>
