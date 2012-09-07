package org.nanocan.savanah.library



import org.junit.*
import grails.test.mixin.*

@TestFor(LibraryController)
@Mock(Library)
class LibraryControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/library/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.libraryInstanceList.size() == 0
        assert model.libraryInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.libraryInstance != null
    }

    void testSave() {
        controller.save()

        assert model.libraryInstance != null
        assert view == '/library/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/library/show/1'
        assert controller.flash.message != null
        assert Library.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/library/list'


        populateValidParams(params)
        def library = new Library(params)

        assert library.save() != null

        params.id = library.id

        def model = controller.show()

        assert model.libraryInstance == library
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/library/list'


        populateValidParams(params)
        def library = new Library(params)

        assert library.save() != null

        params.id = library.id

        def model = controller.edit()

        assert model.libraryInstance == library
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/library/list'

        response.reset()


        populateValidParams(params)
        def library = new Library(params)

        assert library.save() != null

        // test invalid parameters in update
        params.id = library.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/library/edit"
        assert model.libraryInstance != null

        library.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/library/show/$library.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        library.clearErrors()

        populateValidParams(params)
        params.id = library.id
        params.version = -1
        controller.update()

        assert view == "/library/edit"
        assert model.libraryInstance != null
        assert model.libraryInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/library/list'

        response.reset()

        populateValidParams(params)
        def library = new Library(params)

        assert library.save() != null
        assert Library.count() == 1

        params.id = library.id

        controller.delete()

        assert Library.count() == 0
        assert Library.get(library.id) == null
        assert response.redirectedUrl == '/library/list'
    }
}
