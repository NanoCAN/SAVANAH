<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Create readout from zipped file</title>
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
    <h3>Create readout from zipped file</h3>
    <br/>
    <g:if test="${flash.error}">
        <div class="errors" role="alert">&nbsp;${flash.error}</div>
        ${flash.error = ''}
    </g:if>
    <g:if test="${flash.okay}">
        <div class="message" role="status">${flash.okay}</div>
        ${flash.okay = ''}
    </g:if>

    <g:uploadForm action="createFromZippedFile">
        <table>
            <tr>
                <td>Zipped file:</td>
                <td><input type="file" name="zippedFile" style="width:400px;" /> </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" /></td>
            </tr>

        </table>
    </g:uploadForm>
</div>
</body>
</html>