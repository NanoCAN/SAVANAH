<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'sheet.label', default: 'Sheet')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>

    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav">
                    <g:render template="/templates/navmenu"></g:render>
            <li><g:link class="list" action="show" id="${readoutInstance.id}">Back to Readout</g:link></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="content">
    <h1>Import Readout Data</h1>

        <div id="updateDiv" class="message">Select a sheet and configuration</div>

        <div id="formDiv">
        <g:form>

            <g:hiddenField name="id" value="${readoutInstance.id}"></g:hiddenField>

            <ol class="property-list">

                <li class="fieldcontain">
                    <span class="property-label">Select a suitable mapping of column names to properties (optional):</span>
                    <span class="property-value"><g:select name="config" noSelection="['':'']" value="${readoutInstance?.lastConfig?.id}" from="${configs}" optionKey="id"></g:select></span>
                </li>

                <li class="fieldcontain">
                    <span class="property-label">Skip lines (overrules config if checked):</span>

                    <span class="property-value">
                        <g:checkBox name="skipLines" value="${false}"/>
                        <g:field name="howManyLines" value="${0}" type="number"/>
                    </span>
                </li>

                <li class="fieldcontain">
                    <span class="property-label">Minimum number of columns to read:</span>

                    <span class="property-value">
                        <g:field name="minColRead" value="${1}" type="number"/>
                    </span>
                </li>

                <g:if test="${fileEnding == "xlsx" || fileEnding == "xls"}">
                    <li class="fieldcontain">
                        <span class="property-label">Select the sheet with the results of the readout: </span>
                        <span class="property-value"><g:select name="sheet" from="${sheets}" optionKey="index" optionValue="name"></g:select>
                        </span>
                    </li>
                </g:if>

                <g:else>
                    <li class="fieldcontain">
                        <span class="property-label">Select between CSV (comma-separated) and CSV2 (semicolon-separated): </span>
                        <span class="property-value"><g:select name="csvType" from="${['CSV', 'CSV2', 'custom']}"
                                                               onchange="${g.remoteFunction(update: 'custom_CSV_div', controller: 'slide', action: 'customCSV', params: '\'selectedType=\'+this.value')}"></g:select>
                        </span>
                    </li>
                    <div id="custom_CSV_div"/>
                </g:else>
            </ol>

            <fieldset class="buttons">
                <g:submitToRemote update="headerSelection" action="readInputFile" name="buttonReadInputFile" value="Read selected sheet"/>
            </fieldset>

            <div id="headerSelection"/>
        </g:form>
        </div>
    </div>
    </body>
</html>