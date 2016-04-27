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

    <div class="errors">Experiment creation failed due to the following reason:</br>${error}</div>

</div>
</body>
</html>
