package persistence

import java.time.LocalDateTime

class Note {
    private var createdAtDate: LocalDateTime

    internal var title: String
    internal var modules: ArrayList<Module>
    internal var lastModifiedDate: LocalDateTime
    internal var id: Int
    internal var onlineId: String?
    internal var tags: ArrayList<Tag> = arrayListOf()

    constructor(
        title: String = "",
        modules: ArrayList<Module> = arrayListOf(),
        createdAtDate: LocalDateTime = LocalDateTime.now(),
        lastModifiedDate: LocalDateTime = LocalDateTime.now(),
        id:Int =  -1,
        onlineId:String? = null,
        tags: ArrayList<Tag> = arrayListOf()
    ) {
        this.title = title
        this.modules = modules
        this.createdAtDate = createdAtDate
        this.lastModifiedDate = lastModifiedDate
        this.id = id
        this.onlineId = onlineId
        this.tags = tags
    }

    constructor(noteData: NoteData) {
        this.title = noteData.title
        this.createdAtDate = LocalDateTime.parse(noteData.dateCreated)
        this.lastModifiedDate = LocalDateTime.parse(noteData.dateLastModified)
        this.id = noteData.id
        this.onlineId = noteData.onlineId
        this.modules = noteData.modules
        this.tags = noteData.tags
    }

    constructor(noteServerData: NoteServerData) {
        this.title = noteServerData.title!!
        this.createdAtDate = LocalDateTime.parse(noteServerData.dateCreated)
        this.lastModifiedDate = LocalDateTime.parse(noteServerData.dateLastModified)
        this.id = -1
        this.onlineId = noteServerData._id
        this.modules = arrayListOf()
        noteServerData.modules?.forEach {
            var mod: Module? = null
            if (it.type != null && it.content != null) {
                mod = Module(it.type, it.content)
            }
            if (mod != null) {
                this.modules.add(mod)
            }
        }
    }

    fun getTitle(): String {
        return this.title
    }

    fun getModules(): ArrayList<Module> {
        return this.modules
    }

    fun getCreatedAtDate(): LocalDateTime {
        return this.createdAtDate
    }

    fun getLastModifiedDate(): LocalDateTime {
        return this.lastModifiedDate
    }

    fun getNoteId(): Int {
        return id
    }

    fun getOnlineId(): String? {
        return onlineId
    }
    
    fun getTags(): ArrayList<Tag> {
        return tags
    }

    fun getTagStrings(): List<String> {
        return tags.map { it.name }
    }
}