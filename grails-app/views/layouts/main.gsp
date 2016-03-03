<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="SAVANAH"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- modules defined in ApplicationResources.groovy -->
        <r:require module="jstree"/>
        <r:require module="rainbowVis"/>
        <r:require module="bootstrap"/>
        <r:require module="jquery-ui"/>

		<g:layoutHead/>
        <r:layoutResources />

        <g:external dir="images" file="well_icon.png"/>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
	</head>
	<body>
		<div id="savanahBanner" role="banner">
            <img id="savanahLogo" src="${resource(dir: 'images', file: 'savanah_banner.png')}" alt="SAVANAH"/>
            <sec:ifLoggedIn>
                <div id="logout" style="float:right; padding-right:10px; padding-top:10px;">
                    You are logged in as<br/><b><sec:username/></b><br/><br/>
                    <g:link controller="logout">Logout</g:link>
                    <sec:ifAllGranted roles="ROLE_ADMIN"> | <g:link controller="person">Manage</g:link></sec:ifAllGranted>
                </div>
            </sec:ifLoggedIn>
        </div>
        <g:layoutBody/>

    <div id="updateDiv" name="updateDiv"></div>


    <div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
        <r:script>
            $('.dropdown-toggle').dropdown();
        </r:script>
        <r:layoutResources />
	</body>
</html>