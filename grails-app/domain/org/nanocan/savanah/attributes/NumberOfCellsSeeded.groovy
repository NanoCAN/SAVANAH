package org.nanocan.savanah.attributes

class NumberOfCellsSeeded {

    String name
    int number
    String comments
    String color

    static constraints = {
        name nullable: true
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
    }

    String toString()
    {
        (number)
    }

    def beforeInsert = {
        name = number as String
    }

    def beforeUpdate = {
        beforeInsert()
    }
}
