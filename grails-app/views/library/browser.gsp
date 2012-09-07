<%@ page import="org.nanocan.savanah.library.Library" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'library.label', default: 'Library')}" />
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
                    "url":  "<g:createLink controller="library" action="librariesAsJSON"/>",
                    "data" : function(n) { return { id: n.attr? n.attr("id") : 0, nodeType: n.attr? n.attr("nodeType") : "library" }; }
                }
            },
            "themes" : {
                "theme" : "default",
                "icons" : true
            },
            "types" : {
                "valid_children" : ["library"],
                "types" : {
                    "library" : {
                        "hover_node": false,
                        "valid_children": ["libPlates"]
                    },
                    "libPlates" : {
                         "icon": {
                             "image": "<g:resource dir="images" file="plate_nano.png"/>"
                         }
                    },
                    "default": {
                        "valid_children": ["default"]
                    }
                }
            },
            "plugins": ["themes", "ui", "json_data", "types"]
        });

        myTree.bind("select_node.jstree", function(event, data){
            ${remoteFunction(action: 'getDataOrigins', controller: 'plate', update: 'plateDiv', params: '\'id=\'+data.rslt.obj.attr("id")')}
        });
</r:script>

</body>
</html>