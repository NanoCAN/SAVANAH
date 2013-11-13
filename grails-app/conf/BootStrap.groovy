import grails.util.GrailsUtil
import org.nanocan.io.ColorService
import org.nanocan.layout.CellLine
import org.nanocan.security.Role
import org.nanocan.security.Person
import org.nanocan.security.PersonRole
import org.nanocan.layout.PlateLayout
import org.nanocan.layout.WellLayout

class BootStrap {

    def init = { servletContext ->

        switch (GrailsUtil.environment) {
            case "development":
                initUserbase()
                initSampleData()
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

        if(!Person.findByUsername("mdissing")){
            def adminUser = new Person(username: 'mdissing', enabled: true, password: 'password')
            adminUser.save(flush: true, failOnError: true)
            PersonRole.create adminUser, adminRole, true
            PersonRole.create adminUser, userRole, true
        }

        if(!CellLine.findByName("MCF7")){
            def mcf7 = new CellLine()
            mcf7.name = "MCF7"
            mcf7.color = ColorService.randomColor()
            mcf7.save(failOnError: true)
        }

        if(!CellLine.findByName("MCF12A")){
            def mcf12a = new CellLine()
            mcf12a.name = "MCF12A"
            mcf12a.color = ColorService.randomColor()
            mcf12a.save(failOnError: true)
        }
    }

    private void initSampleData(){

    }
}
