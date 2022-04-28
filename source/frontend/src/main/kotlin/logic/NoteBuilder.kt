package logic

import persistence.*
import java.time.LocalDateTime

class NoteBuilder: Builder {
    private var note: Note?

    constructor(title: String = "") {
        if (title == "") {
            this.note = null
        } else {
            this.note = Note(title)
        }
    }

    fun createNote(title: String = "") {
        this.note = Note(title)
        note!!.modules.add(Module(NoteTypeEnum.TITLE, title))
        note!!.modules.add(Module(NoteTypeEnum.BODY, "Write to your heart's content..."));
    }

    override fun reset() {
        this.note = null
    }

    fun setTitle(title: String) {
        note?.title = title
    }

    fun setTimeModified(time: LocalDateTime) {
        note?.lastModifiedDate = time
    }

    fun addModule(module: Module) {
        note?.modules?.add(module)
    }

    fun removeModule(int: Int){
        note?.modules?.removeAt(int)
    }

    fun getResult(): Note? {
        return note
    }

    fun attachNote(note: Note) {
        this.note = note
    }

    fun setId(id: Int) {
        note?.id = id
    }

    fun clearModules() {
        note?.modules?.clear()
    }
    
    fun addTag(tag: Tag) {
        note?.tags?.add(tag)
    }

    fun removeTag(tag: Tag) {
        note?.tags?.remove(tag)
    }
    
    fun setOnlineId(id: String){
        note?.onlineId = id
    }

    fun getOnlineId(): String? {
        return note?.onlineId
    }
}