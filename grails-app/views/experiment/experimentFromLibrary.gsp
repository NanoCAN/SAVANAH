<%@ page import="org.nanocan.layout.CellLine; org.nanocan.project.Experiment" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Experiment from library</title>
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
            </ul>
        </div>
    </div>
</div>

<div style="padding-left:10px;">
    <h3>Experiment from library</h3>
    <br/>
    <g:form action="experimentFromLibrary" method="POST">
        <table style="width:500px;">
            <tr>
                <td>Experiment name:</td>
                <td><g:textField name="title" id="title"/></td>
            </tr>
        <tr>
            <td>Project:</td>
            <td><g:select name="selectedProject" from="${projectList}" value="" noSelection="['':'Please select...']"/></td>
        </tr>
        <tr>
            <td>From library:</td>
            <td><g:select name="selectedLibrary" from="${libraryList}" value="" noSelection="['':'Please select...']"/></td>
        </tr>
            <tr>
                <td>Number of replicates:</td>
                <td><g:select name="nrReplicates" from="${1..9}"/></td>
            </tr>
        </tr>
        <tr>
            <td>Lowest replicate nr.:</td>
            <td><g:select name="lowReplicateNr" from="${1..9}"/></td>
        </tr>
        <tr>
            <td>Default cell-line:</td>
            <td><g:select from="${CellLine.list()}" name="defaultCellLine" id="defaultCellLine"/></td>
        </tr>
        <tr>
            <td>
                Barcode pattern:
                <br/>
                <div style="color:#333333;font-size:10px;">
                    \\R = replicate number
                    <br/>
                    \\P = two digit plate number
                </div>
            </td>
            <td><g:textField name="defaultCellLine" id="defaultCellLine"/></td>
        </tr>
        <tr>
            <td>Default cell-line:</td>
            <td><g:textField name="defaultCellLine" id="defaultCellLine"/></td>
        </tr>

        <tr>
            <td colspan="2"><g:submitButton name="Create experiment"/></td>
        </tr>
        </table>
    </g:form>
</div>
</body>
</html>