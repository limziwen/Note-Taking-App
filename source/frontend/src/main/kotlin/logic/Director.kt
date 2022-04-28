package logic

import com.google.gson.Gson
import frontend.Composites.TagMenu
import frontend.Views
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import persistence.*
import java.time.LocalDateTime

class Director() {
    private val noteBuilder = NoteBuilder()
    private val diskAdapter = NoteDiskAdapter()
    private val moduleBuilder = ModuleBuilder()
    private val profileAdapter = NoteProfileAdapter()
    private val profile: ProfileData
    private val client: HttpClient
    private val gson = Gson()
    private var selectedTags: ArrayList<Tag> = ArrayList()
    public var view: Views? = null
    public var tagMenu: TagMenu? = null

    init {
        if (!diskAdapter.checkIfProfileFileExists()) {
            diskAdapter.createNewProfile("Untitled User")
        }
        profile = diskAdapter.loadProfile()!!
        client = HttpClient(CIO)

    }

    fun updateSelectedTags(tags: ArrayList<Tag>) {
        selectedTags = tags
    }

    private fun postNoteRequest(note: NoteData?){
        if (note != null) {
            runBlocking {
                val response: String = client.post("http://localhost:8080/note") {
                    contentType(ContentType.Application.Json)
                    body = gson.toJson(note, NoteData::class.java)
                }
                note.onlineId = response.toString()
                noteBuilder.setOnlineId(response.toString())
            }
        }
    }

    private fun updateNoteRequest(note: NoteData?){
        if (note != null) {
            runBlocking {
                val response : String = client.put("http://localhost:8080/note") {
                    contentType(ContentType.Application.Json)
                    body = gson.toJson(note, NoteData::class.java)
                }
            }
        }

    }


    private fun checkNoteExists(title: String): Boolean {
        return  profile.notes.contains(title)
    }

    fun setTitle(title: String) {
        if (noteBuilder.getResult() == null) {
            createNote(title)
        } else {
            noteBuilder.setTitle(title)
        }
    }

    private fun convertToNoteData(note: Note): NoteData {
        var noteData = NoteData(
            id = note.id,
            onlineId = note.onlineId,
            title = note.title,
            dateCreated = note.getCreatedAtDate().toString(),
            dateLastModified = note.lastModifiedDate.toString(),
            modules = note.modules,
            tags = note.tags
            )
        return noteData
    }

    fun createNote(title: String = "Untitled Note") {
        try {
            noteBuilder.createNote(title)
            val note = noteBuilder.getResult()
            if (note != null) {
                val noteId = profileAdapter.createNoteRecordFromNote(note)
                if (noteId != null) {
                    noteBuilder.setId(noteId)
                    this.postNoteRequest(convertToNoteData(note))
                    val path = diskAdapter.writeToFile(note)

                    //post note into mongodb and returns onlineId into noteBuilder


                    profileAdapter.updateNote(noteId, path = path, onlineId = noteBuilder.getOnlineId())

                } else {
                    throw Error("Couldn't get noteId")
                }
            } else {
                throw Error("Couldn't create note in builder")
            }
        } catch (e: Error) {
            println(e.message)
        }
    }

    fun getNote(): Note? {
        return noteBuilder.getResult()
    }


    fun loadNote(id: Int) {
        runBlocking {
            try {
                // load metadata from disk
                val offlineData = profileAdapter.queryNoteById(id)
                    ?: throw  Error("failed to find note based on id: id provided was $id")
                // load note from server
                var onlineNote: Note? = null

                if (offlineData.onlineId != null) {

                    val data: String = client.get("http://localhost:8080/note/${offlineData.onlineId}") {
                        accept(ContentType.Application.Json)
                    }
                    val noteServerData = gson.fromJson(data, NoteServerData::class.java)
                    onlineNote = Note(noteServerData)
                }
                // check date on server
                if (onlineNote != null && onlineNote.getLastModifiedDate() > offlineData.dateModified) {
                    noteBuilder.attachNote(onlineNote)
                } else {
                    val offlineNote = diskAdapter.readFromFile(offlineData.file!!)
                    noteBuilder.attachNote(offlineNote)
                }
            } catch (e: Error) {
                println(e.message)
            }
        }
    }

    //Do we need to keep this function?
    fun updateNote(id: Int, title: String? = noteBuilder.getResult()?.getTitle(), modules: ArrayList<Module>? = null) {
        if (title != null) {
            noteBuilder.setTitle(title)
        }
        modules?.forEach {
            noteBuilder.addModule(it)
        }
        if (modules != null || title != null) {
            val dateModified = LocalDateTime.now()
            noteBuilder.setTimeModified(dateModified)
            val result = noteBuilder.getResult()
            if (result != null) {
                //updates json file
                val path = diskAdapter.writeToFile(result)
                //updates sqlite data
                profileAdapter.updateNote(id, result.getTitle(), dateModified, path)
            }
        }
    }

    fun deleteNote(id: Int) {
        try {
            val path = profileAdapter.queryNoteById(id)?.file
            if (path != null) {
                val res = diskAdapter.deleteNoteFileFromPath(path)
                if (res) {
                    val res2 = profileAdapter.deleteNoteById(id)
                    if (res2) {
                        if (noteBuilder.getResult()?.getNoteId() == id) {
                            noteBuilder.reset()
                        }
                    } else {
                        throw Error("Failed to remove note from DB")
                    }
                } else {
                    throw Error("Failed to delete note from disk")
                }
            } else {
                throw Error("Failed to find note with id $id")
            }
        } catch (e: Error) {
            println(e.message)
        }
    }

    fun resetNoteBuilder() {
        noteBuilder.reset()
    }

    fun deleteNote() {
        noteBuilder.reset()
    }

    fun deleteNoteFile(title: String) {
        diskAdapter.deleteNoteFromTitle(title)
        profile.notes.remove(title)
    }

    fun getNoteObject(): Note? {
        return noteBuilder.getResult()
    }

    //creates a module and adds to existing note
    fun createModuleAddToNote(type: String, content: String){
        moduleBuilder.createModule(type, content)
        val newModule = moduleBuilder.getResult()
        if (newModule != null && noteBuilder.getResult() != null){
            noteBuilder.addModule(newModule)
        }
    }

    fun addModuleToNote(module: Module) {
        noteBuilder.addModule(module)
    }

    //Deletes a module from existing note using index
    fun deleteModuleFromNote(int: Int) {
        noteBuilder.removeModule(int)
    }

    fun getProfileName(): String {
        return profile.name
    }

    fun updateName(name: String = "Untitled User") {
        profile.name = name
    }

    fun saveNote(id: Int, online: Boolean = true) {
        noteBuilder.setTimeModified(LocalDateTime.now())
        val note = noteBuilder.getResult()

        if (note != null && note.getTitle() != "" && !checkNoteExists(note.getTitle())) {
            val path = diskAdapter.writeToFile(note)
            profileAdapter.updateNote(id, note.title, note.lastModifiedDate, path, online = online)
            //updates mongodb
            if (online) {
                this.updateNoteRequest(convertToNoteData(note))
            }
        }
    }

    fun writeProfile() {
        diskAdapter.writeProfile(profile)
    }

    fun getModulesFromNote(): ArrayList<Module>? {
        return noteBuilder.getResult()?.modules
    }

    fun createTag(title: String): Tag? {
        val tagId = profileAdapter.addTag(title)
        if (tagId != null) {
            return profileAdapter.getTagById(tagId)
        }
        return null
    }

    fun deleteTag(id: Int): Boolean {
        return profileAdapter.deleteTag(id)
    }

    fun updateTag(id: Int, newTitle: String): Boolean {
        return profileAdapter.updateTag(id, newTitle)
    }

    fun addTagToNote(title: String): Tag? {
        val tag: Tag? = profileAdapter.getTagByTitle(title) ?: createTag(title)
        if (tag != null) {
            noteBuilder.addTag(tag)
            val now = LocalDateTime.now()
            noteBuilder.setTimeModified(now)
            val note = noteBuilder.getResult()
            if (note != null) {
                diskAdapter.writeToFile(note)
                profileAdapter.updateNote(note.id, dateModified = now)
                profileAdapter.addTagNoteRelation(tag.id, note.id)
            }
        }
        return tag
    }

    fun clearModules() {
        noteBuilder.clearModules()
    }

    fun getAllNotes(): ArrayList<NoteMetaData> {
        val arrayParam = if (selectedTags.isEmpty()) {
            null
        } else {
            selectedTags
        }
        return profileAdapter.getAllNotes(arrayParam)
    }

    fun getAllTags(): ArrayList<Tag> {
        return profileAdapter.getAllTags()
    }
}
