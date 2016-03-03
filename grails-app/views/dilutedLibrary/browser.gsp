<%@ page import="org.nanocan.savanah.library.DilutedLibrary" %>
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

<h1 style="padding-left:20px;"><g:message code="default.browse.label" args="[entityName]" /> Dilutions</h1>

<div style="min-height:700px;">
<div id="plateTreeDiv" style="float: left; padding-left:20px; width:300px; height:700px; overflow:auto;"></div>
<div id="plateDiv" style="float:right;width:500px; padding-right:20px;"></div>
</div>

<r:script>
        var myTree = $("#plateTreeDiv").jstree({
            "core": {
                "data": {
                    "url":  "<g:createLink controller="dilutedLibrary" action="librariesAsJSON"/>",
                    "data" : function(node) { return { 'id': node.id? node.id : 0, "type": node.type ? node.type : "#" }; }
                }
            },
            "types" : {
                "#" : {
                    "valid_children" : ["library"]
                },
                "library" : {
                    "valid_children": ["master", "mother"]
                },
                 "master" : {
                    "valid_children": ["mother"],
                    "icon": "<g:resource dir="images" file="plate_small_stack.png"/>"

                },
                "mother" : {
                    "valid_children": ["daughter"],
                    "icon": "<g:resource dir="images" file="plate_small_stack.png"/>"

                },
                "daughter" : {
                    "valid_children": ["well"],
                    "icon": "<g:resource dir="images" file="plate_small_stack.png"/>"

                },
                "dltdPlate" : {
                    "valid_children": ["well"],
                    "icon": "<g:resource dir="images" file="plate_small.png"/>"

                },
                "well" : {
                     "icon": "<g:resource dir="images" file="well.png"/>"
                }
            },
            "plugins" : ["types"]
        });

        myTree.bind("select_node.jstree", function(event, data){
            var node = data.instance.get_node(data.selected[0]);
            if(node.type == "library"){
                ${remoteFunction(action: 'addMaster', controller: 'dilutedLibrary', update: 'plateDiv', params: '\'id=\'+node.id')}
            }
            if(node.type == "master"){
                ${remoteFunction(action: 'addMother', controller: 'dilutedLibrary', update: 'plateDiv', params: '\'id=\'+node.id.substring(4)')}
            }
            if(node.type == "mother"){
                ${remoteFunction(action: 'addDaughter', controller: 'dilutedLibrary', update: 'plateDiv', params: '\'id=\'+node.id.substring(4)')}
            }
            if(node.type == "daughter"){
                ${remoteFunction(action: 'showDaughter', controller: 'dilutedLibrary', update: 'plateDiv', params: '\'id=\'+node.id.substring(4)')}
            }
            if(node.type == "dltdPlate"){
                ${remoteFunction(action: 'showPlate', controller: 'dilutedLibrary', update: 'plateDiv', params: '\'id=\'+node.id.substring(4)')}
            }
        });
</r:script>

</body>
</html>