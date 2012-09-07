<%@ page import="org.nanocan.dart.Run" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'run.label', default: 'Run')}" />
    <title><g:message code="default.browse.label" args="[entityName]" /></title>
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li>
                    <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
                </li>
            </ul>
        </div>
    </div>
</div>

<h1><g:message code="default.browse.label" args="[entityName]" /></h1>

<div style="min-height:700px;">
<div id="plateTreeDiv" style="float: left; width:300px; height:700px; overflow:auto;"></div>
<div id="plateDiv" style="float:right;width:500px;"></div>
</div>

<r:script>
        var myTree = $("#plateTreeDiv").jstree({
            "json_data": {
                "ajax": {
                    "url":  "<g:createLink controller="run" action="runsAsJSON"/>",
                    "data" : function(n) { return { id: n.attr? n.attr("id") : 0, nodeType: n.attr? n.attr("nodeType") : "run" }; }
                }
            },
            "themes" : {
                "theme" : "default",
                "icons" : true
            },
            "types" : {
                "max_depth" : -2,
                "max_children": -2,
                "valid_children" : ["run"],
                "types" : {
                    "run" : {
                        "valid_children": ["plate"]
                    },
                    "plate" : {
                         "valid_children": "none",
                         "icon": {
                             "image": "<g:resource dir="images" file="plate_nano.png"/>"
                         }
                    }
                }
            },
            "plugins": ["themes", "ui", "json_data", "types"]
        });

        myTree.bind("select_node.jstree", function(event, data){
            ${remoteFunction(action: 'getRawValues', controller: 'run', update: 'plateDiv', params: '\'id=\'+data.rslt.obj.attr("id")+\'&run=\'+data.rslt.obj.attr("run")')}
        });
</r:script>

</body>
</html>