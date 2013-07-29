package org.nanocan.savanah.plates



import org.junit.*
import grails.test.mixin.*

@TestFor(ReadoutController)
@Mock(Readout)
class ReadoutControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/readout/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.readoutInstanceList.size() == 0
        assert model.readoutInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.readoutInstance != null
    }

    void testSave() {
        controller.save()

        assert model.readoutInstance != null
        assert view == '/readout/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/readout/show/1'
        assert controller.flash.message != null
        assert Readout.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/readout/list'


        populateValidParams(params)
        def readout = new Readout(params)

        assert readout.save() != null

        params.id = readout.id

        def model = controller.show()

        assert model.readoutInstance == readout
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/readout/list'


        populateValidParams(params)
        def readout = new Readout(params)

        assert readout.save() != null

        params.id = readout.id

        def model = controller.edit()

        assert model.readoutInstance == readout
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/readout/list'

        response.reset()


        populateValidParams(params)
        def readout = new Readout(params)

        assert readout.save() != null

        // test invalid parameters in update
        params.id = readout.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/readout/edit"
        assert model.readoutInstance != null

        readout.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/readout/show/$readout.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        readout.clearErrors()

        populateValidParams(params)
        params.id = readout.id
        params.version = -1
        controller.update()

        assert view == "/readout/edit"
        assert model.readoutInstance != null
        assert model.readoutInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/readout/list'

        response.reset()

        populateValidParams(params)
        def readout = new Readout(params)

        assert readout.save() != null
        assert Readout.count() == 1

        params.id = readout.id

        controller.delete()

        assert Readout.count() == 0
        assert Readout.get(readout.id) == null
        assert response.redirectedUrl == '/readout/list'
    }
}
