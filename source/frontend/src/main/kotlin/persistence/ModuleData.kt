package persistence

data class ModuleData(
    val type: NoteTypeEnum ?=null,
    val content: String? = null
)
