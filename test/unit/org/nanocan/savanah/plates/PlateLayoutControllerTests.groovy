package org.nanocan.savanah.plates

import grails.test.mixin.*

@TestFor(PlateLayoutController)
@Mock(PlateLayout)
class PlateLayoutControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/plateLayout/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.plateLayoutInstanceList.size() == 0
        assert model.plateLayoutInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.plateLayoutInstance != null
    }

    void testSave() {
        controller.save()

        assert model.plateLayoutInstance != null
        assert view == '/plateLayout/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/plateLayout/show/1'
        assert controller.flash.message != null
        assert PlateLayout.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/plateLayout/list'


        populateValidParams(params)
        def plateLayout = new PlateLayout(params)

        assert plateLayout.save() != null

        params.id = plateLayout.id

        def model = controller.show()

        assert model.plateLayoutInstance == plateLayout
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/plateLayout/list'


        populateValidParams(params)
        def plateLayout = new PlateLayout(params)

        assert plateLayout.save() != null

        params.id = plateLayout.id

        def model = controller.edit()

        assert model.plateLayoutInstance == plateLayout
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/plateLayout/list'

        response.reset()


        populateValidParams(params)
        def plateLayout = new PlateLayout(params)

        assert plateLayout.save() != null

        // test invalid parameters in update
        params.id = plateLayout.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/plateLayout/edit"
        assert model.plateLayoutInstance != null

        plateLayout.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/plateLayout/show/$plateLayout.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        plateLayout.clearErrors()

        populateValidParams(params)
        params.id = plateLayout.id
        params.version = -1
        controller.update()

        assert view == "/plateLayout/edit"
        assert model.plateLayoutInstance != null
        assert model.plateLayoutInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/plateLayout/list'

        response.reset()

        populateValidParams(params)
        def plateLayout = new PlateLayout(params)

        assert plateLayout.save() != null
        assert PlateLayout.count() == 1

        params.id = plateLayout.id

        controller.delete()

        assert PlateLayout.count() == 0
        assert PlateLayout.get(plateLayout.id) == null
        assert response.redirectedUrl == '/plateLayout/list'
    }
}
