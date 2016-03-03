modules = {
    application {
        resource url:'js/application.js'
    }

    jstree {
        dependsOn 'jquery'
        resource url: 'js/jstree/jstree.min.js'
        resource url: 'js/jstree/themes/default/40px.png'
        resource url: 'js/jstree/themes/default/32px.png'
        resource url: 'js/jstree/themes/default/style.min.css'
        resource url: 'js/jstree/themes/default/throbber.gif'
    }

    colorPicker {
        dependsOn 'jquery'
        resource url: 'js/colorpicker.js'
        resource url: 'css/colorpicker.css'
    }

    rainbowVis {
        resource url: 'js/rainbowvis.js'  , disposition: 'head'
    }

    bootstrap {
        resource url: 'js/bootstrap-dropdown.js', disposition: 'head'
        resource url: 'js/bootstrap.js'
        resource url: 'css/bootstrap.css'
    }

    polychart2 {
        resource url: 'js/polychart2.standalone.js'
    }

    select2{
        dependsOn 'jquery'
        resource url: 'js/select2-3.4.5/select2.min.js'
        resource url: 'js/select2-3.4.5/select2.css'
    }

    syntaxhighlighter{
        resource url: '/js/shCore.js'
        resource url: '/js/shBrushXml.js'
        resource url: '/js/shBrushR.js'
        resource url: '/css/shCore.css'
        resource url: '/css/shCoreDefault.css'
        resource url: '/css/shThemeDefault.css'
    }
}