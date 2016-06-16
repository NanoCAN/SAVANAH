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
    <g:jprogressDialog message="Library import in progress..." progressId="${libraryInstance.name}" trigger="uploadLibraryFile"/>
    <div id="summary">
    <div id="updateDiv" class="message">Ready to process library file. Press start to begin.</div>

    <g:form>
    <br/>
        <table>
            <tr>
                <td>Name:</td>
                <td><g:textField readonly="readonly" name="name" value="${libraryInstance?.name?:''}"/></td>
            </tr>
            <tr>
                <td>Plate format:</td>
                <td>
                    <g:textField value="${libraryInstance?.plateFormat}"
                                 name="plateFormat"
                                 readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>Library type:</td>
                <td>
                    <g:textField value="${libraryInstance?.type}"
                                 name="type"
                                 readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>Library vendor:</td>
                <td>
                    <g:textField name="vendor"
                                 value="${libraryInstance?.vendor}"
                                 readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>Library catalog number:</td>
                <td>
                    <g:textField name="catalogNr"
                                 value="${libraryInstance?.catalogNr}"
                                 readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>Sample type:</td>
                <td>
                    <g:textField value="${libraryInstance?.sampleType}"
                                 name="sampleType"
                                 readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>Accession type:</td>
                <td>
                    <g:textField value="${libraryInstance?.accessionType}"
                                 name="accessionType"
                                 readonly="readonly"/>
                </td>
            </tr>
        </table>
    <fieldset class="buttons">
        <g:submitToRemote action="upload"
                          id="uploadLibraryFile"
                          name="uploadLibraryFile"
                          onSuccess="jQuery('#spinner').hide();jQuery('#summary').hide();jQuery('#linkToLibBrowser').show();"
                          value="Start..."/>
    </fieldset>
    </g:form>
    </div>
    <div id="linkToLibBrowser" style="display:none;">
    <g:form controller="library" action="browser">
        <g:submitButton value="Show library browser" name="toLibBrowser"/>
    </g:form>
    </div>
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