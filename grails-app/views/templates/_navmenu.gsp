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

