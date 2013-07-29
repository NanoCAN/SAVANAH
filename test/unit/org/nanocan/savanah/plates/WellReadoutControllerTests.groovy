package org.nanocan.savanah.plates



import org.junit.*
import grails.test.mixin.*

@TestFor(WellReadoutController)
@Mock(WellReadout)
class WellReadoutControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/wellReadout/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.wellReadoutInstanceList.size() == 0
        assert model.wellReadoutInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.wellReadoutInstance != null
    }

    void testSave() {
        controller.save()

        assert model.wellReadoutInstance != null
        assert view == '/wellReadout/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/wellReadout/show/1'
        assert controller.flash.message != null
        assert WellReadout.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/wellReadout/list'


        populateValidParams(params)
        def wellReadout = new WellReadout(params)

        assert wellReadout.save() != null

        params.id = wellReadout.id

        def model = controller.show()

        assert model.wellReadoutInstance == wellReadout
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/wellReadout/list'


        populateValidParams(params)
        def wellReadout = new WellReadout(params)

        assert wellReadout.save() != null

        params.id = wellReadout.id

        def model = controller.edit()

        assert model.wellReadoutInstance == wellReadout
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/wellReadout/list'

        response.reset()


        populateValidParams(params)
        def wellReadout = new WellReadout(params)

        assert wellReadout.save() != null

        // test invalid parameters in update
        params.id = wellReadout.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/wellReadout/edit"
        assert model.wellReadoutInstance != null

        wellReadout.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/wellReadout/show/$wellReadout.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        wellReadout.clearErrors()

        populateValidParams(params)
        params.id = wellReadout.id
        params.version = -1
        controller.update()

        assert view == "/wellReadout/edit"
        assert model.wellReadoutInstance != null
        assert model.wellReadoutInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/wellReadout/list'

        response.reset()

        populateValidParams(params)
        def wellReadout = new WellReadout(params)

        assert wellReadout.save() != null
        assert WellReadout.count() == 1

        params.id = wellReadout.id

        controller.delete()

        assert WellReadout.count() == 0
        assert WellReadout.get(wellReadout.id) == null
        assert response.redirectedUrl == '/wellReadout/list'
    }
}
