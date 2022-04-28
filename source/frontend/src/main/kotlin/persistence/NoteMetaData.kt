package persistence

import java.time.LocalDateTime

data class NoteMetaData(
    var id: Int,
    var title: String,
    var dateCreated: LocalDateTime,
    var dateModified: LocalDateTime,
    var file: String?,
    var onlineId: String?,
    var tags: ArrayList<Tag>?,
    var online: Boolean? = true,
    )