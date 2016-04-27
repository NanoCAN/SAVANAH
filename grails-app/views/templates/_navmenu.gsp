<%@ page import="org.nanocan.project.Project; org.nanocan.project.Experiment" %>
<li class="dropdown" id="organize.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#organize.menu">
        <g:message code="default.organize.label"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="project" action="index">List Projects</g:link>
        </li>
        <li>
            <g:link class="create" controller="project" action="create">Create New Project</g:link>
        </li>
        <li>
            <g:link class="list" controller="experiment" action="index">List Experiments</g:link>
        </li>
        <li>
            <g:link class="create" controller="experiment" action="create">Create New Experiment</g:link>
        </li>
        <li>
            <g:link class="create" controller="libraryToExperiment">Create Experiment from Library</g:link>
        </li>

    </ul>
</li>
<li class="dropdown" id="library.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#library.menu">
        <g:message code="default.library.label" args="['...']" default="Library"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="library" action="index">List Libraries</g:link>
        </li>
        <li>
            <g:link class="plate" controller="library" action="browser">Browse Library Plates</g:link>
        </li>
        <li>
            <g:link class="create" controller="LibraryFileUpload">Create New library</g:link>
        </li>
        <li>
            <g:link class="plate" controller="dilutedLibrary" action="browser">Library Dilutions</g:link>
        </li>
    </ul>
</li>
<li class="dropdown" id="layout.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#layout.menu">
        <g:message code="default.layout.label" args="['...']" default="Plate Layout"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="plateLayout" action="index">List PlateLayouts</g:link>
        </li>
        <li>
            <g:link class="create" controller="plateLayout" action="create">Create New PlateLayout</g:link>
        </li>
        <li class="divider"></li>
        <li>
            <g:link class="edit" controller="cellLine" action="index">CellLines</g:link>
        </li>
        <li>
            <g:link class="edit" controller="inducer" action="index">Inducers</g:link>
        </li>
        <li>
            <g:link class="edit" controller="numberOfCellsSeeded" action="index">Numbers of Cells Seeded</g:link>
        </li>
        <li>
            <g:link class="edit" controller="treatment" action="index">Treatments</g:link>
        </li>
        <li>
            <g:link class="edit" controller="sample" action="index">Samples</g:link>
        </li>
    </ul>
</li>

<li class="dropdown" id="plate.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#plate.menu">
        <g:message code="default.plate.label" args="['...']" default="Assay Plates"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="plate" action="index">List Plates</g:link>
        </li>
        <li>
            <g:link class="create" controller="plate" action="create">Create New Plate</g:link>
        </li>
        <!--<li>
            <g:link class="plate" controller="run" action="browser">Plates in DART</g:link>
        </li>-->
        <li class="divider"></li>
        <li>
            <g:link class="zip" controller="Readout" action="createFromZipFile">Batch Import Of Readouts</g:link>
        </li>
        <li class="divider"></li>
        <li>
            <g:link class="create" controller="plateType" action="index">Plate Types</g:link>
        </li>
    </ul>
</li>
<li class="divider-vertical"></li>
<li>
    <g:form class="navbar-search" url='[controller: "search", action: "index"]' id="searchableForm" name="searchableForm" method="get">
        <g:textField class="search-query" placeholder="Search" name="query" value="${params.q}"/>
    </g:form>

</li>
<li class="divider-vertical"></li>
<li class="dropdown" id="filter.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#filter.menu">
        <g:message code="default.slide.menu.label" default="Filter"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li style="padding:20px;">Filter project: <g:form class="navbar-form" name="projectForm" controller="project" action="updateSelectedProject">
            <g:hiddenField name="returnPage" value="${createLink(action:actionName, params:params, absolute: true)}"/>
            <g:select from="${Project.list()}" value="${session.projectSelected?:""}" optionKey="id" noSelection="['':'All projects']" name="projectSelect" onchange="\$('#projectForm').submit();"/>
        </g:form></li>
        <li style="padding:20px;">Filter experiment: <g:form class="navbar-form" name="experimentForm" controller="experiment" action="updateSelectedExperiment">
            <g:hiddenField name="returnPage" value="${createLink(action:actionName, params:params, absolute: true)}"/>
            <g:select from="${session.projectSelected?Experiment.findAllByProject(Project.get(session.projectSelected)):Experiment.list()}" value="${session.experimentSelected?:""}" optionKey="id" noSelection="['':'All experiments']" name="experimentSelect" onchange="\$('#experimentForm').submit();"/>
        </g:form></li>
    </ul>
</li>
<li class="divider-vertical"></li>
<li>
    <g:if test="${controllerName == 'experiment' && actionName == 'show'}">
        <g:link class="plot" controller="analysis" action="start" params="${['experiment': experimentInstance.id]}">Data Analysis</g:link>
    </g:if>

    <g:else>
        <g:link class="plot" controller="analysis" action="start">Data Analysis</g:link>
    </g:else>

</li>
<li class="divider-vertical"></li>


