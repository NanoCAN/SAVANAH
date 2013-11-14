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


    <g:if test="${flash.error}">
        <div class="errors" role="alert">&nbsp; - ${flash.error}</div>
    </g:if>
    <g:if test="${flash.okay}">
        <div class="message" role="status">${flash.okay}</div>
    </g:if>

    <br/>
    <g:uploadForm action="upload">
        <table>
            <tr>
                <td>Title:</td>
                <td><g:textField name="libraryName" value=""/></td>
            </tr>
            <tr>
                <td>Library file:</td>
                <td><input type="file" name="dataFile" style="width:400px;" /> </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" /></td>
            </tr>

        </table>
    </g:uploadForm>
</div>
</body>
</html>