<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'analysis.label', default: 'Analysis')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<a href="#edit-slide" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
            </ul>
        </div>
    </div>
</div>

<div class="body">

    <div style="padding-left:20px;">
        <g:form class="form" name="analysisForm" controller="analysis" action="start">
            <g:select id="project" name="project" optionKey="id" value="${selectedProject}" noSelection="['':'select a project']" from="${projects}" onchange="\$('#analysisForm').submit();"/>

            <g:select id="experiment" optionKey="id" value="${selectedExperiment}" noSelection="['':'select an experiment']" name="experiment" from="${experiments}" onchange="\$('#analysisForm').submit();"/>

            <g:select id="plateLayout" optionKey="id" name="plateLayout" value="${selectedPlateLayout}" noSelection="['':'select a plate layout']" from="${plateLayouts}" onchange="\$('#analysisForm').submit();"/>

        </g:form>
    </div>

    <div style="padding: 30px;">
        <g:form name="selectedSlidesAndPlates" target="_blank">

        <g:each in="${plates}" var="plate">
            <g:hiddenField name="allPlates" value="${plate.id}"/>
        </g:each>

        <g:checkBox name="processAll" value="${false}"/> Simply select everything (can cause long processing time)

        <br/><br/>
        <h3>Plates</h3>
        <g:select name="selectedPlates" style="width:100%; height:500px;" optionKey="id" multiple="true" from="${plates}"/>

         <br/><br/>

        <h3>Screen Type (for HiTSeekR)</h3>
            <div class="message">Note: In order to use HiTSeekR, SAVANAH needs to be reachable. If you use a local instance of SAVANAH you also need to set up a local instance of HiTSeekR and configure SAVANAH to use it.</div>
        <g:select name="screenType" optionKey="key" optionValue="value"
                  from="${['siRNA':'Gene silencing', 'miRNA':'miRNA inhibitor/mimics',
                           'compound':'small compounds']}"/>

        <br/><br/>
        <fieldset class="buttons">
            <g:actionSubmitImage action="hitseekrAnalysis" value="Analyze with HiTSeekR"
                                 src="${resource(dir: 'images', file: 'HiTSeekR.png')}"/>
            <g:actionSubmitImage action="exportToR" value="Export selected plate information and associated readout data to R"
                                 src="${resource(dir: 'images', file: 'Rlogo.png')}"/>
            <!--<g:actionSubmitImage action="download" value="Download"
                                 src="${resource(dir: 'images', file: 'download.png')}"/>-->
        </fieldset>
        </g:form>
    </div>

        <r:script>
            $(document).ready(function() {
                $("#selectedPlates").select2();
            });

        </r:script>

</div>

<r:script>
    $(document).ready(function() {
                $("#project").select2();
                $("#experiment").select2();
                $("#plateLayout").select2();
        }
    );
</r:script>

</body>
</html>