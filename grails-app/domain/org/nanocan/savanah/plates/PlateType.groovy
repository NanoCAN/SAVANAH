package org.nanocan.savanah.plates

class PlateType {

    boolean ultraLowAdhesion
    String wellShape
    String name
    String vendor

    static constraints = {
        wellShape inList: ["round-bottom", "v-bottom"]
    }
}
