
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
}


