<%@ page import="org.nanocan.savanah.attributes.NumberOfCellsSeeded" %>

<head>
    <r:require module="colorPicker"/>
</head>
<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${numberOfCellsSeededInstance?.color}',
                    onShow: function (colpkr) {
                        $(colpkr).fadeIn(500);
                        return false;
                    },
                    onHide: function (colpkr) {
                        $(colpkr).fadeOut(500);
                        return false;
                    },
                    onChange: function (hsb, hex, rgb) {
                        $('#colorPickDiv').css('backgroundColor', '#' + hex);
                        $('#colorInput').attr("value", "#" + hex);
                    }
                });
</r:script>

<div class="fieldcontain ${hasErrors(bean: numberOfCellsSeededInstance, field: 'number', 'error')} required">
    <label for="number">
        <g:message code="numberOfCellsSeeded.number.label" default="Number" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="number" name="number" required="" value="${numberOfCellsSeededInstance.number}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: numberOfCellsSeededInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="numberOfCellsSeeded.comments.label" default="Comments" />
		
	</label>
	<g:textField name="comments" value="${numberOfCellsSeededInstance?.comments}"/>
</div>

<div style="width:75px; padding-left:175px;" class="fieldcontain ${hasErrors(bean: numberOfCellsSeededInstance, field: 'color', 'error')} required">
    <label for="color">
        <g:message code="numberOfCellsSeeded.color.label" default="Color" />
        <span class="required-indicator">*</span>
    </label>
    <div id="colorPickDiv" style="float:right; background-color: ${numberOfCellsSeededInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${numberOfCellsSeededInstance?.color}">
    </div>
</div>

