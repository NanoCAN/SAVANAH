<%@ page import="org.nanocan.layout.Sample; org.nanocan.savanah.library.Library" %>
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
                    <g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
                </li>
            </ul>
        </div>
    </div>
</div>

<div style="padding-left:10px;">
<h3>Library File Upload</h3>
    <br/>
    <div id="updateDiv" class="message">Provide a library file following the specifications of the manual.</div>

    <g:hasErrors bean="${libraryInstance}">
        <ul class="errors" role="alert">
            <div class ="errors" role="alert">
                <g:renderErrors bean="${libraryInstance}" as="list" />
            </div>
        </ul>
    </g:hasErrors>
    <g:if test="${flash.error}">
        <div class="errors" role="alert">&nbsp;${flash.error}</div>
    </g:if>
    <g:if test="${invalidLibPlate}">
        <div class ="errors" role="alert">
            <g:renderErrors bean="${invalidLibPlate}" as="list" />
        </div>
    </g:if>

    <g:if test="${flash.okay}">
        <div class="message" role="status">${flash.okay}</div>
    </g:if>
    <g:uploadForm action="create">
    <br/>
        <table>
            <tr>
                <td>Name:</td>
                <td><g:textField name="name" value="${libraryInstance?.name?:''}"/></td>
            </tr>
            <tr>
                <td>Plate format:</td>
                <td>
                    <g:select from="${org.nanocan.savanah.library.LibraryPlate.constraints.format.inList}"
                              value="${libraryInstance?.plateFormat}"
                              name="plateFormat"/>
                </td>
            </tr>
            <tr>
                <td>Library type:</td>
                <td>
                    <g:select from="${Library.constraints.type.inList}"
                              value="${libraryInstance?.type}"
                              name="type"/>
                </td>
            </tr>
            <tr>
                <td>Library vendor:</td>
                <td>
                    <g:textField name="vendor" value="${libraryInstance?.vendor}"/>
                </td>
            </tr>
            <tr>
                <td>Library catalog number:</td>
                <td>
                    <g:textField name="catalogNr" value="${libraryInstance?.catalogNr}"/>
                </td>
            </tr>
            <tr>
                <td>Sample type:</td>
                <td>
                    <g:select from="${org.nanocan.layout.Sample.constraints.type.inList}"
                              value="${libraryInstance?.sampleType}" name="sampleType"/>
                </td>
            </tr>
            <tr>
                <td>Accession type:</td>
                <td>
                    <g:select from="${org.nanocan.layout.Identifier.constraints.type.inList}"
                              value="${libraryInstance?.accessionType}"
                              name="accessionType"/>
                </td>
            </tr>
            <tr>
                <td>Library file:</td>
                <td><input type="file" name="dataFile" style="width:400px;" /> </td>

            </tr>
        </table>
    <fieldset class="buttons">
        <input id="uploadLibraryFile" type="submit" class="save" name="Upload" />
    </fieldset>
    </g:uploadForm>
</div>
</div>

<script type="text/javascript">jQuery(function(){
    $("#uploadLibraryFile").click(function(){
        $('#spinner').show();
        })
});
</script>
</body>
</html>