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
                    <g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
                </li>
            </ul>
        </div>
    </div>
</div>

<h1 style="padding-left:20px;"><g:message code="default.browse.label" args="[entityName]" /> Plates</h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div style="min-height:700px;">
<div id="plateTreeDiv" style="padding-left:20px; float: left; width:300px; height:700px; overflow:auto;"></div>
<div id="plateDiv" style="padding-right:20px; float:right;width:500px;"></div>
</div>

<r:script>
        var myTree = $("#plateTreeDiv").jstree({
            "core": {
                "data": {
                    "url":  "<g:createLink controller="library" action="librariesAsJSON"/>",
                    "data" : function(node) { return { 'id': node.id? node.id : 0, "type": node.type ? node.type : "#" }; }
                }
            },
            "types" : {
                "#" : {
                    "valid_children" : ["library"]
                },
                "library" : {
                    "valid_children": ["libPlate"]
                },
                "libPlate" : {
                     "valid_children": ["libWell"],
                     "icon": "<g:resource dir="images" file="plate_small.png"/>"
                },
                "libWell" : {
                     "icon": "<g:resource dir="images" file="well.png"/>"
                }
            },
            "plugins" : ["types"]
        });

        myTree.bind("select_node.jstree", function(event, data){
            var node = data.instance.get_node(data.selected[0]);
            if(node.type == "libWell"){
                ${remoteFunction(action: 'show', controller: 'entry', update: 'plateDiv', params: '\'id=\'+node.id.substring(2)')}
            }
            else if(node.type == "library"){
                ${remoteFunction(action: 'showInBrowser', controller: 'library', update: 'plateDiv', params: '\'id=\'+node.id')}
            }
        });
</r:script>

</body>
</html>