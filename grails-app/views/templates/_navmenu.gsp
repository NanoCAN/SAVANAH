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
