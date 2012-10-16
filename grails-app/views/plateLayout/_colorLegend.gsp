<%@ page import="org.nanocan.savanah.attributes.NumberOfCellsSeeded; org.nanocan.savanah.attributes.Inducer; org.nanocan.savanah.attributes.CellLine; org.nanocan.savanah.attributes.Treatment;" %>
<head>
    <r:require module="colorPicker"/>
    <style>
        #draggableLegend { width:250px; padding: 0.5em; background-color: #ffffff; position: fixed;
            z-index: 10000; opacity: 0.95; top: 250px; left: 50px;
            border-color: #e6e6e6; border-bottom-width: 1px; border-style: solid;}
    </style>
    <r:script>
        $(function(){
            $( "#draggableLegend").draggable();
        });
    </r:script>
</head>
<body>
    <div id="draggableLegend">

    <table>
    <g:if test="${sampleProperty == 'cellLine'}"><g:set var="samplePropertyList" value="${CellLine.list()}"/></g:if>
    <g:elseif test="${sampleProperty == 'inducer'}"><g:set var="samplePropertyList" value="${Inducer.list()}"/></g:elseif>
    <g:elseif test="${sampleProperty == 'treatment'}"><g:set var="samplePropertyList" value="${Treatment.list()}"/></g:elseif>
    <g:elseif test="${sampleProperty == 'numberOfCellsSeeded'}"><g:set var="samplePropertyList" value="${NumberOfCellsSeeded.list()}"/></g:elseif>

    <thead>
    <tr><th>${sampleProperty.toString().capitalize()}</th><th>Color</th></tr>
    </thead>

    <tr>
        <td><input type="radio" onchange="changeColor('#ffffff', '', '-1')" name="radioSampleTypes" value="none"/> None
        </td>
        <td>
            <div id="colorPickNull" style="background-color: #fff; border: 1px solid; width:25px; height:25px;"></div>
        </td>
    </tr>

    <g:each in="${samplePropertyList}">
        <tr>
            <td>
                <input type="radio" onchange="changeColor('${it.color}', '${it}', ${it.id})" name="radioSampleTypes" value="${it.id}"/>     ${it}
            </td>
            <td>
                <div id="colorPick${it.id}" style="background-color: ${it.color}; border: 1px solid; width:25px; height:25px;"></div>
            </td>
            <r:script>
                $('#colorPick${it.id}').ColorPicker({
                    color: '${it.color}',
                    onShow: function (colpkr) {
                        $(colpkr).fadeIn(500);
                        return false;
                    },
                    onHide: function (colpkr) {
                        $(colpkr).fadeOut(500);
                        return false;
                    },
                    onChange: function (hsb, hex, rgb) {
                        $('#colorPick${it.id} div').css('backgroundColor', '#' + hex);
                    }
                });
            </r:script>

        </tr>
    </g:each>
    </table>
    </div>

    <r:script>

    function changeColor(color, name, id){
        selColor = color
        selName = name
        selId = id
    }
    </r:script>

</body>