<g:set var="minValue" value="${java.util.Collections.min(rawData.collect{it.measuredValue})}"/>
<g:set var="maxValue" value="${java.util.Collections.max(rawData.collect{it.measuredValue})}"/>
<g:set var="legendStepSize" value="${(maxValue-minValue) /10}"/>
<g:set var="currentLegendValue" value="${minValue}"/>

<script type="text/javascript">
    var rainbow = new Rainbow();
    rainbow.setNumberRange(${minValue}, ${maxValue})
</script>

<h1>Heatmap:</h1>
<table>
    <g:set var="well" value="${0}"/>
    <g:each var="row" in="${1..12}">
        <tr>
            <g:each var="col" in="${1..8}">
                <g:set var="wellInstance" value="${rawData.get(well)}"/>

                <td style="width: 40px; height: 10px;" id="well${well}">${wellInstance.sampleName}</td>

                <script type="text/javascript">
                    $("#well${well}").css('background-color', '#'+rainbow.colorAt(${wellInstance.measuredValue}))
                </script>
                <g:set var="well" value="${++well}"/>
            </g:each>
        </tr></g:each>
</table>

<h1>Color Legend:</h1>
<table>
    <g:each in="${1..10}" var="legendIt">
        <tr>
            <td><g:formatNumber number="${currentLegendValue}"/></td>
            <td><div id="legend${legendIt}" style="width:10px;height:10px;"/></td>
        </tr>
        <script type="text/javascript">
            $("#legend${legendIt}").css('background-color', '#'+rainbow.colorAt(${currentLegendValue}));
        </script>
        <g:set var="currentLegendValue" value="${currentLegendValue + legendStepSize}"/>
    </g:each>
</table>
