<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'create.url.for.R.title', default: 'Create URL for R')}" />
    <r:require module="syntaxhighlighter"/>
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
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


<div class="content">
    <h1>Export plate data to R</h1>

    <div style="margin: 20px;">First of all you need to load the necessary code into R:</div>

    <pre class="brush: r">
        #Loads devtools package. Installs it if necessary
        if(!require(devtools)){
            install.packages("devtools")
            library(devtools)
        }
        #Loads R code for importing data from SAVANAH
        source_url("https://raw.githubusercontent.com/NanoCAN/SAVANAH/master/R/import.R")

        #command for importing the selected plate data
        plate.data <- batch.import.readouts(plateSecurityTokens=
        ${plateSecurityTokens},
        baseUrl = "${baseUrl}")
    </pre>

    <script type="text/javascript">
        $(document).ready(function() { SyntaxHighlighter.all() });
    </script>

</div>
</body>
</html>