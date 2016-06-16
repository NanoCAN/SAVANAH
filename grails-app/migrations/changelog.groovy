
databaseChangeLog = {
    changeSet(id: "modifyDescriptionColumnInExperiment", author: "mlist"){
        modifyDataType(tableName: "Experiment", columnName: "description", newDataType: "text")
    }
    changeSet(id: "modifyDescriptionColumnInProject", author: "mlist"){
        modifyDataType(tableName: "Project", columnName: "project_description", newDataType: "text")
    }
    changeSet(id: "modifyCommentColumnInLibraryEntry", author: "mlist"){
        modifyDataType(tableName: "Entry", columnName: "comment", newDataType: "text")
    }
    changeSet(id: "modifyAccessionTypeColumnInIdentifier", author: "mlist"){
        modifyDataType(tableName: "Identifier", columnName: "type", newDataType: "varchar(20)")
    }
/*    changeSet(id: "addDefaultAssayType", author: "mlist"){
        insert(tableName: "AssayType"){
            column(name: "name", value: "Cell Titer Blue")
            column(name: "type", value: "Fluorescence")
        }
    }*/
    changeSet(id: "dropColumnAssayType", author: "mlist"){
        dropColumn tableName: "Readout", columnName: "assay_type"
    }
    changeSet(id: "dropColumnTypeOfReadout", author: "mlist"){
        dropColumn tableName: "Readout", columnName: "type_of_readout"
    }
    changeSet(id: "dropColumnWavelength", author: "mlist"){
        dropColumn tableName: "Readout", columnName: "wavelength"
    }

}


