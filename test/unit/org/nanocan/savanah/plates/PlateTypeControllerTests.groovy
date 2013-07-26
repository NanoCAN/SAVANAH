package org.nanocan.savanah.plates

import grails.test.mixin.*

@TestFor(PlateTypeController)
@Mock(PlateType)
class PlateTypeControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/plateType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.plateTypeInstanceList.size() == 0
        assert model.plateTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.plateTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.plateTypeInstance != null
        assert view == '/plateType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/plateType/show/1'
        assert controller.flash.message != null
        assert PlateType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/plateType/list'


        populateValidParams(params)
        def plateType = new PlateType(params)

        assert plateType.save() != null

        params.id = plateType.id

        def model = controller.show()

        assert model.plateTypeInstance == plateType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/plateType/list'


        populateValidParams(params)
        def plateType = new PlateType(params)

        assert plateType.save() != null

        params.id = plateType.id

        def model = controller.edit()

        assert model.plateTypeInstance == plateType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/plateType/list'

        response.reset()


        populateValidParams(params)
        def plateType = new PlateType(params)

        assert plateType.save() != null

        // test invalid parameters in update
        params.id = plateType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/plateType/edit"
        assert model.plateTypeInstance != null

        plateType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/plateType/show/$plateType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        plateType.clearErrors()

        populateValidParams(params)
        params.id = plateType.id
        params.version = -1
        controller.update()

        assert view == "/plateType/edit"
        assert model.plateTypeInstance != null
        assert model.plateTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/plateType/list'

        response.reset()

        populateValidParams(params)
        def plateType = new PlateType(params)

        assert plateType.save() != null
        assert PlateType.count() == 1

        params.id = plateType.id

        controller.delete()

        assert PlateType.count() == 0
        assert PlateType.get(plateType.id) == null
        assert response.redirectedUrl == '/plateType/list'
    }
}
