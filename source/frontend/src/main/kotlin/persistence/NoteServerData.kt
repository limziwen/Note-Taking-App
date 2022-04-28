package persistence

data class NoteServerData(
    var _id: String?= null,
    var title: String ?= null,
    var dateCreated: String ?= null,
    var dateLastModified: String ?= null,
    var modules: ArrayList<ModuleData> ?= null,
) {}