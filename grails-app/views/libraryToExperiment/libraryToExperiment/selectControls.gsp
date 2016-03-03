<%@ page import="org.nanocan.layout.NumberOfCellsSeeded; org.nanocan.layout.Inducer; org.nanocan.layout.Treatment; org.nanocan.layout.CellLine" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'plateLayout.label', default: 'PlateLayout')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
<g:render template="colorLegend" model="${[sampleProperty: "controlSamples"]}"></g:render>

<g:set var="well" value="${1}"/>
<g:set var="wellList" value="${[].withDefault{null}}"/>

<script type="text/javascript">
    $(document).ready(function() {
        registerHandlers("plateLayoutTable");
    });
</script>


<a href="#show-plateLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
            </ul>
        </div>
    </div>
</div>

<h1 style="padding-left:20px;">Create Experiment from Library: Select Controls</h1>

<div class="message" id="message" role="status">${flash.message?:"Please mark control wells. Note: if a well is already occupied by a sample this will be ignored."}</div>

<g:form>
    <input name="wellProperty" type="hidden" value="Sample"/>

    <div id = "plateLayout" style="overflow: auto; padding: 20px;">
        <table id="plateLayoutTable" style="border: 1px solid;">
            <thead>
            <tr align="center">
                <th style="width:25px;"/>
                <g:each in="${1..numOfCols}" var="col">
                    <th style="width:25px;">${col}</th>
                </g:each>
            </tr>
            </thead>

            <tbody>
            <g:each in="${1..numOfRows}" var="row">
                <tr id="r${row}">
                    <td>${row}</td>
                    <g:each in="${1..numOfCols}">
                        <td style="border: 1px solid; background-color:${wellList.get(well)?wellList.get(well).color?:'#e0e0e0':''};"><input name="${well}" type="hidden" value=""></td>
                        <g:set var="well" value="${++well}"/>
                    </g:each>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <fieldset class="buttons">
    <g:submitButton name="continue" class="save" value="Create Experiment" onclick="return confirm('All steps are completed and the experiment will now be created. Are you sure you want to continue?');"/>
    </fieldset>

</g:form>


<r:script>

    var allTDs
    var selColor
    var selName
    var selId
    var buttondown = -1
    var cellstartr, cellstartc, cellendr, cellendc
    var tableName

    function registerHandlers(tN) {
        tableName = tN
        allTDs = document.getElementById(tableName).getElementsByTagName("td")
        document.getElementById(tableName).onmousedown = mouseDownHandler
        document.getElementById(tableName).onmouseup = mouseUpHandler
        document.getElementById(tableName).onmouseover = mouseOverHandler
    }

    function mouseOverHandler(e) {

        if (buttondown != -1) {
            if (window.getSelection) window.getSelection().removeAllRanges()
            if (document.selection) document.selection.empty()
        }
    }

    function mouseDownHandler(e) {
        if (!e) e = window.event
        var daTarget
        if (document.all)
            daTarget = e.srcElement
        else
            daTarget = e.target
        cellstartr = daTarget.parentNode.id.substring(1)
        cellstartc = daTarget.cellIndex
        buttondown = e.button
    }
    function mouseUpHandler(e) {

        if (!selColor) {
            alert('Please select a control first!')
        }

        else {
            if (!e) e = window.event
            var daTarget
            if (document.all)
                daTarget = e.srcElement
            else
                daTarget = e.target
            cellendr = daTarget.parentNode.id.substring(1)
            cellendc = daTarget.cellIndex
            var rowstart = cellstartr
            var rowend = cellendr
            if (parseInt(cellendr) < parseInt(cellstartr)) {
                rowstart = cellendr
                rowend = cellstartr
            }
            var colstart = cellstartc
            var colend = cellendc
            if (parseInt(cellendc) < parseInt(cellstartc)) {
                colstart = cellendc
                colend = cellstartc
            }
            for (var i = rowstart; i <= rowend; i++) {
                for (var j = colstart; j <= colend; j++) {
                    if(j != 0) //protect row names from color changes
                    {
                        var cell = document.getElementById(tableName).rows[i].cells[j];
                        cell.style.backgroundColor = selColor;
                        cell.firstChild.setAttribute("value", selId);
                    }
                }
            }
            buttondown = -1
        }
    }

</r:script>

</body>
</html>