package models

data class ClientNoteData(
    var onlineId: String?= null,
    var title: String ?= null,
    var dateCreated: String ?= null,
    var dateLastModified: String ?= null,
    var modules: ArrayList<ModuleData> ?= null,
)
