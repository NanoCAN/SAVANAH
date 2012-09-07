package org.nanocan.dart



import org.junit.*
import grails.test.mixin.*

@TestFor(RunController)
@Mock(Run)
class RunControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/run/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.runInstanceList.size() == 0
        assert model.runInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.runInstance != null
    }

    void testSave() {
        controller.save()

        assert model.runInstance != null
        assert view == '/run/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/run/show/1'
        assert controller.flash.message != null
        assert Run.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/run/list'


        populateValidParams(params)
        def run = new Run(params)

        assert run.save() != null

        params.id = run.id

        def model = controller.show()

        assert model.runInstance == run
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/run/list'


        populateValidParams(params)
        def run = new Run(params)

        assert run.save() != null

        params.id = run.id

        def model = controller.edit()

        assert model.runInstance == run
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/run/list'

        response.reset()


        populateValidParams(params)
        def run = new Run(params)

        assert run.save() != null

        // test invalid parameters in update
        params.id = run.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/run/edit"
        assert model.runInstance != null

        run.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/run/show/$run.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        run.clearErrors()

        populateValidParams(params)
        params.id = run.id
        params.version = -1
        controller.update()

        assert view == "/run/edit"
        assert model.runInstance != null
        assert model.runInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/run/list'

        response.reset()

        populateValidParams(params)
        def run = new Run(params)

        assert run.save() != null
        assert Run.count() == 1

        params.id = run.id

        controller.delete()

        assert Run.count() == 0
        assert Run.get(run.id) == null
        assert response.redirectedUrl == '/run/list'
    }
}
