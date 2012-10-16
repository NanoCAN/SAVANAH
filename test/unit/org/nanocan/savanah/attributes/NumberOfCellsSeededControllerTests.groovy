package org.nanocan.savanah.attributes



import org.junit.*
import grails.test.mixin.*

@TestFor(NumberOfCellsSeededController)
@Mock(NumberOfCellsSeeded)
class NumberOfCellsSeededControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/numberOfCellsSeeded/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.numberOfCellsSeededInstanceList.size() == 0
        assert model.numberOfCellsSeededInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.numberOfCellsSeededInstance != null
    }

    void testSave() {
        controller.save()

        assert model.numberOfCellsSeededInstance != null
        assert view == '/numberOfCellsSeeded/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/numberOfCellsSeeded/show/1'
        assert controller.flash.message != null
        assert NumberOfCellsSeeded.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/numberOfCellsSeeded/list'


        populateValidParams(params)
        def numberOfCellsSeeded = new NumberOfCellsSeeded(params)

        assert numberOfCellsSeeded.save() != null

        params.id = numberOfCellsSeeded.id

        def model = controller.show()

        assert model.numberOfCellsSeededInstance == numberOfCellsSeeded
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/numberOfCellsSeeded/list'


        populateValidParams(params)
        def numberOfCellsSeeded = new NumberOfCellsSeeded(params)

        assert numberOfCellsSeeded.save() != null

        params.id = numberOfCellsSeeded.id

        def model = controller.edit()

        assert model.numberOfCellsSeededInstance == numberOfCellsSeeded
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/numberOfCellsSeeded/list'

        response.reset()


        populateValidParams(params)
        def numberOfCellsSeeded = new NumberOfCellsSeeded(params)

        assert numberOfCellsSeeded.save() != null

        // test invalid parameters in update
        params.id = numberOfCellsSeeded.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/numberOfCellsSeeded/edit"
        assert model.numberOfCellsSeededInstance != null

        numberOfCellsSeeded.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/numberOfCellsSeeded/show/$numberOfCellsSeeded.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        numberOfCellsSeeded.clearErrors()

        populateValidParams(params)
        params.id = numberOfCellsSeeded.id
        params.version = -1
        controller.update()

        assert view == "/numberOfCellsSeeded/edit"
        assert model.numberOfCellsSeededInstance != null
        assert model.numberOfCellsSeededInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/numberOfCellsSeeded/list'

        response.reset()

        populateValidParams(params)
        def numberOfCellsSeeded = new NumberOfCellsSeeded(params)

        assert numberOfCellsSeeded.save() != null
        assert NumberOfCellsSeeded.count() == 1

        params.id = numberOfCellsSeeded.id

        controller.delete()

        assert NumberOfCellsSeeded.count() == 0
        assert NumberOfCellsSeeded.get(numberOfCellsSeeded.id) == null
        assert response.redirectedUrl == '/numberOfCellsSeeded/list'
    }
}
