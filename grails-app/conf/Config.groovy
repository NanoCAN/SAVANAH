import org.apache.log4j.ConsoleAppender
import org.apache.log4j.DailyRollingFileAppender
import org.apache.log4j.Priority

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

/* default config */
grails.serverURL = "http://localhost:8080/SAVANAH"
grails.plugins.springsecurity.cas.active = false

/* Search for external config files */
def ENV_NAME = "SAVANAH_CONFIG"
if (!grails.config.locations || !(grails.config.locations instanceof List)) {
    grails.config.locations = []
}
if (System.getenv(ENV_NAME)) {
    println "Including configuration file specified in environment: " + System.getenv(ENV_NAME);
    grails.config.locations << "file:" + System.getenv(ENV_NAME).replace('\\', '/')
}
else if (System.getProperty(ENV_NAME)) {
    println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
    grails.config.locations << "file:" + System.getProperty(ENV_NAME)
}
else{
    println "No config file found. Using defaults config."
}


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration

def savanahLogLevel = "DEBUG"
def savanahLogPattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} [%t] %x %-5p %c{2} - %m%n"
def log4jFileName = System.properties.getProperty('catalina.base', '.') + "/logs/savanah.log"

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
        //}
        //debug  'org.hibernate.SQL'
        /*'org.hibernate.transaction',
        'org.hibernate.jdbc' */

        appender new DailyRollingFileAppender(name: "savanahLog",
                threshold: Priority.toPriority(savanahLogLevel),
                file: log4jFileName,
                datePattern: "'.'yyyy-MM-dd",   //Rollover at midnight each day.
                layout: pattern(conversionPattern: savanahLogPattern)
        )
        if (grails.util.Environment.current == grails.util.Environment.DEVELOPMENT || grails.util.Environment.current == grails.util.Environment.TEST) {
            appender new ConsoleAppender(name: "console",
                    threshold: Priority.toPriority(savanahLogLevel),
                    layout: pattern(conversionPattern: savanahLogPattern)
            )
        }
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate',
            'org.nanocan'
    warn   'org.nanocan'
    info   'org.nanocan'
    /*debug   'grails.plugins.springsecurity'
    debug   'org.codehaus.groovy.grails.plugins.springsecurity'
    debug   'org.springframework.security'
    debug   'org.jasig.cas.client'*/
    debug   'org.nanocan',
            'grails.plugins.springsecurity',
            'grails.plugin.springcache',
            'org.codehaus.groovy.grails.plugins.springsecurity',
            'org.apache.http.headers',
            'grails.app.services',
            'grails.app.domain',
            'grails.app.controllers',
            'grails.plugin.databasemigration',
            'liquibase'

    List<String> loggers = []
    loggers.add('savanahLog')
    if (grails.util.Environment.current.name == "development" ||
            grails.util.Environment.current.name == "test") {
        loggers.add('console')
    }
    root {
        error loggers as String[]
        additivity = true
    }
}
// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'org.nanocan.security.Person'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'org.nanocan.security.PersonRole'
grails.plugins.springsecurity.authority.className = 'org.nanocan.security.Role'

grails.views.javascript.library="jquery"

jdbc.batchSize = 150
jdbc.groovySql = true
upload.directory = "/upload/"


grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ["changelog.groovy"]