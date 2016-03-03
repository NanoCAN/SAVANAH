<%@ page import="org.nanocan.savanah.library.Library; org.nanocan.project.Experiment" %>
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
                <label for="daughterPlateSets">
                    Select daughter plate sets from this library to be used as replicates:
                    <span class="required-indicator">*</span>
                </label>
                <g:select id="dilutedLibraries" name="dilutedLibraries.id" from="${dilutedLibraries}" optionKey="id" multiple="true" required="" style="width:400px;height:400px;"/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="continue" class="save" value="${message(code: 'default.button.create.label', default: 'Continue with screen settings')}" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
