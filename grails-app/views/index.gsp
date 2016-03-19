<%@ page import="org.nanocan.plates.WellReadout; org.nanocan.project.Project; org.nanocan.plates.Readout; org.nanocan.plates.Plate; org.nanocan.layout.PlateLayout; org.nanocan.savanah.library.DilutedLibrary; org.nanocan.savanah.library.Library" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to SAVANAH</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}
            
			#status li {
				line-height: 1.3;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
	</head>
	<body>
		<sec:ifLoggedIn>
        <a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <g:render template="/templates/navmenu"></g:render>
                    </ul>
                </div>
            </div>
        </div>
		<div id="status" role="complementary">
			<h1>Application Status</h1>
			<ul>
				<li>App version: <g:meta name="app.version"/></li>
				<li>Grails version: <g:meta name="app.grails.version"/></li>
				<li>JVM version: ${System.getProperty('java.version')}</li>
				<li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
				<li>Projects: ${Project.count()}</li>
				<li>Experiments: ${Project.count()}</li>
				<li>Libraries: ${Library.count()}</li>
				<li>Dilutions: ${DilutedLibrary.count()}</li>
				<li>Plates: ${PlateLayout.count()}</li>
				<li>Replicate Plates: ${Plate.count()}</li>
				<li>Plate Readouts: ${Readout.count()}</li>
				<li>Well Readouts: ${WellReadout.count()}</li>
			</ul>
		</div>
		<div id="page-body" role="main">
			<h1>Welcome to SAVANAH</h1>
			<blockquote>
			<p>SAVANAH is an acronym for <b>S</b>ample management <b>A</b>nd <b>V</b>isual <b>ANA</b>lysis of <b>H</b>igh-Troughput Screening Data. This tool
            allows you to manage individual plates (96, 384, and 1536 well format) and to organize them in projects, experiments,
            and libraries.</p></br><br>

			<p>The strength of SAVANAH is its support for the user-friendly creation of library dilution schemes,
			in which library plates are serially diluted into master, mother and daughter plates.
			SAVANAH allows users to upload library sample information once and to subsequently
			keep track of this information automatically across all dilutions and also across
			replicate plates.</p></br><br>

			<p>Experimental readout data can be uploaded for individual plates or as a batch.
			Readouts are visualized as interactive heatmaps and scatterplots and can be imported in the R statistical environment for
			further analysis.</p>
			</blockquote>

			<br/><br/>
			<h1>Reference:</h1>
			<p>
			SAVANAH is freely available under the GNU public license (v3) at <u><a href="https://github.com/NanoCAN/SAVANAH/issues" target="_blank">github</a></u>. If you find SAVANAH useful please consider citing us:</br></br>

			<b>List et al., Efficient management of high-throughput screening libraries with SAVANAH</br> (manuscript in preparation)</b>
			</p>

			<br/><br/>
			<h1>10 simple steps to using SAVANAH for your HTS screen:</h1>
			<br/>
			<i>Note: After completing each step you can get back to this page by clicking on the SAVANAH banner.</i>
		<ol class="rounded-list">
			<li>
				<g:link controller="PlateType" action="create">Add a plate type</g:link>
			</li>
			<li> <a href="#">Add sample information, such as</a>
				<ol>
					<li>
						<g:link controller="CellLine" action="create">A cell line used in your screen</g:link>
					</li>
					<li>
						<g:link controller="Sample" action="create">A (control) sample used to fill empty wells in the library</g:link>
					</li>
					<li>
						<g:link controller="Inducer" action="create">An inducer if applicable to your experiment</g:link>
					</li>
					<li>
							<g:link controller="Treatment" action="create">A treatment your cells have been exposed to</g:link>
					</li>
					<li>
							<g:link controller="NumberOfCellsSeeded" action="create">The amount of cell material or the number of cells seeded in your experiment</g:link>
					</li>
				</ol>
			</li>
			<li><g:link controller="libraryFileUpload" action="index">Upload a screening library file</g:link></li>
			<li><g:link controller="dilutedLibrary" action="browser">Generate library dilutions and daughter plates that can be used as assay plates</g:link></li>
			<li><g:link controller="project" action="create">Create a new project for the following experiment</g:link></li>
			<li><g:link controller="libraryToExperiment" action="index">Create a new experiment using an existing library and a set of (replicate) daughter plates</g:link></li>
			<li><g:link controller="readout" action="createFromZipFile">Upload a ZIP file with readouts in CSV or XLSX format.</g:link><i>Note that the file name (without the extension) needs to be identical with the barcode of the target plate to allow for automatic processing.</i></li>
			<li><g:link controller="plate" action="index">Browse through the assay (replicate) plates you just created and visualize their readout.</g:link></li>
			<li><g:link controller="analysis" action="start">Export the sample and readout data to R for further analysis.</g:link></li>
			<li><a href="https://github.com/NanoCAN/SAVANAH/issues" target="_blank">If you find any bugs in SAVANAH please report them on github</a></li>
		</ol>

		</div>
        </sec:ifLoggedIn>

        <sec:ifNotLoggedIn>
            <div><g:include controller="login" action="auth"></g:include></div>
        </sec:ifNotLoggedIn>
	</body>
</html>
