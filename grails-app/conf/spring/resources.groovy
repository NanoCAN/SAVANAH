// Place your Spring DSL code here
beans = {
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        java.util.Locale.setDefault(Locale.ENGLISH)
    }
}
