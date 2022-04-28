package persistence


data class NoteData(
    var id: Int,
    var onlineId: String?,
    var title: String,
    var dateCreated: String,
    var dateLastModified: String,
    var modules: ArrayList<Module>,
    var tags: ArrayList<Tag>
    ) {}
