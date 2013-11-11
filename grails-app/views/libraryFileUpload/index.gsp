<%@ page import="org.nanocan.savanah.library.Library" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'library.label', default: 'Library')}" />
    <title>Library File Upload</title>
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li>
                    <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
                </li>
            </ul>
        </div>
    </div>
</div>

<div style="padding-left:10px;">
<h3>Library File Upload</h3>
    <br/>
    <g:uploadForm action="upload">
        <input type="file" name="dataFile" style="width:400px;" />   <br/><br/>
        <input type="submit" />
        <br/>
        <i style="color:red;"><g:message code="${flash.message}" /></i>
        <i style="color:green;"><g:message code="${flash.okay}" /></i>
    </g:uploadForm>
</div>
</body>
</html>