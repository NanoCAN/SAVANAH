<%@ page import="org.nanocan.savanah.library.DilutedLibrary" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="body">
    <g:set var="entityName" value="${message(code: 'library.label', default: 'Library')}" />
    <title><g:message code="default.browse.label" args="[entityName]" /></title>
</head>
<body>
<g:form method="post" >
    <g:hiddenField name="id" value="${dilutedLibraryInstance?.id}" />
    <g:hiddenField name="parentType" value="${dilutedLibraryInstance?.type}" />
    <fieldset class="buttons">
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>
</html>