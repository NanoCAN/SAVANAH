<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<body>

    <div id="edit-entry" class="content scaffold-edit" role="main">
        <g:if test="${dilutedLibraryInstance?.type in ['Master', 'Mother', 'Daughter']}">
            <g:form method="post" >
                <g:hiddenField name="id" value="${dilutedLibraryInstance?.id}" />
                <g:hiddenField name="parentType" value="${dilutedLibraryInstance?.type}" />
                <g:hiddenField name="type" value="${type}" />
                <fieldset class="buttons">
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </g:if>
        <h1>Library dilution: Add ${type} plate sets</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <div>Note: To define a barcode pattern you have to include the following variables <br/><br/>
            %L% the index of the library plate<br/><br/>
            %MA% the index of the master plate set<br/><br/>
            <g:if test="${type == "Master"}"><g:set var="patternExample" value="${examplePattern}"/></g:if>
            <g:if test="${type == "Mother" || type == "Daughter"}">
                %MO% the index of the mother plate set<br/><br/>
                <g:set var="patternExample" value="${examplePattern}"/>
            </g:if>
            <g:if test="${type == "Daughter"}">
                %DA% the index of the daughter plate set<br/><br/>
                <g:set var="patternExample" value="${examplePattern}"/>
            </g:if>
        </div>
        <g:form method="post" >
            <g:hiddenField name="id" value="${dilutedLibraryInstance?.id}" />
            <g:hiddenField name="parentType" value="${dilutedLibraryInstance?.type}" />
            <g:hiddenField name="type" value="${type}" />
            <fieldset class="form">
                <div class="fieldcontain required">
                    <label>
                        Number of additional plate sets:
                        <span class="required-indicator">*</span>
                    </label>
                    <g:field type="number" name="numberOfSets" required="" value="1"/>
                </div>
                <div class="fieldcontain required">
                    <label>
                        Define the barcode pattern:
                        <span class="required-indicator">*</span>
                    </label>
                    <g:textField name="barcodePattern" value="${patternExample}"/>
                </div>
            </fieldset>

            <fieldset class="buttons">
                <g:actionSubmit class="save" action="addDilutedLibraries" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>