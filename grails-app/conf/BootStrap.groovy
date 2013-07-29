import grails.util.GrailsUtil
import org.nanocan.security.Role
import org.nanocan.security.Person
import org.nanocan.security.PersonRole

class BootStrap {

    def init = { servletContext ->

        switch (GrailsUtil.environment) {
            case "development":
                initUserbase()
                //initSampleData()
                break

            case "test":
                initUserbase()
                initSampleData()
                break

            case "migrate":
                initUserbase()
                break

            case "standalone":
                initUserbase()
                break
        }
    }
    def destroy = {
    }

    private void initUserbase(){

        def adminRole = Role.findByAuthority("ROLE_ADMIN")
        if(!adminRole)  adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        def userRole = Role.findByAuthority("ROLE_USER")
        if(!userRole) userRole= new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)

        if(!Person.findByUsername("user")){
            def testUser = new Person(username: 'user', enabled: true, password: 'password')
            testUser.save(flush: true, failOnError: true)
            PersonRole.create testUser, userRole, true
        }

        if(!Person.findByUsername("mlist")){
            def adminUser = new Person(username: 'mlist', enabled: true, password: 'password')
            adminUser.save(flush: true, failOnError: true)
            PersonRole.create adminUser, adminRole, true
            PersonRole.create adminUser, userRole, true
        }
    }

    private void initSampleData(){

    }
}
