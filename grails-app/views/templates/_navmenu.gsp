<%@ page import="org.nanocan.project.Project; org.nanocan.project.Experiment" %>
<li class="dropdown" id="main.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#main.menu">
        <g:message code="default.menu.label"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </li>
    </ul>
</li>
<li class="dropdown" id="browse.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#browse.menu">
        <g:message code="default.browse.label" args="['...']"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="plate" controller="library" action="browser">Libraries</g:link>
        </li>
        <li>
            <g:link class="plate" controller="run" action="browser">DART</g:link>
        </li>
    </ul>
</li>
<li class="dropdown" id="organize.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#organize.menu">
        <g:message code="default.organize.label"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="project" action="list">Projects</g:link>
        </li>
        <li>
            <g:link class="list" controller="experiment" action="list">Experiments</g:link>
        </li>
    </ul>
</li>
<li class="dropdown" id="plate.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#plate.menu">
        <g:message code="default.plate.label" args="['...']" default="Plate"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="plate" action="list">List Plates</g:link>
        </li>
        <li>
            <g:link class="create" controller="plate" action="create">Create New Plate</g:link>
        </li>
        <li class="divider"></li>
        <li>
            <g:link class="create" controller="plateType" action="list">Plate Types</g:link>
        </li>
        <li>
            <g:link class="create" controller="plate" action="import">Import Plate Data</g:link>
        </li>
    </ul>
</li>
<li class="dropdown" id="layout.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#layout.menu">
        <g:message code="default.layout.label" args="['...']" default="Layout"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="plateLayout" action="list">List PlateLayouts</g:link>
        </li>
        <li>
            <g:link class="create" controller="plateLayout" action="create">Create New PlateLayout</g:link>
        </li>
        <li class="divider"></li>
        <li>
            <g:link class="edit" controller="cellLine" action="list">CellLines</g:link>
        </li>
        <li>
            <g:link class="edit" controller="inducer" action="list">Inducers</g:link>
        </li>
        <li>
            <g:link class="edit" controller="numberOfCellsSeeded" action="list">Numbers of Cells Seeded</g:link>
        </li>
        <li>
            <g:link class="edit" controller="treatment" action="list">Treatments</g:link>
        </li>
        <li>
            <g:link class="edit" controller="sample" action="list">Samples</g:link>
        </li>
    </ul>
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

