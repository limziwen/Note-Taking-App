package logic

import com.google.gson.Gson
import persistence.Note
import persistence.NoteData
import persistence.ProfileData
import java.io.File
import java.time.LocalDateTime

class NoteDiskAdapter {
    private val gson = Gson()
    private val profileName = "profile"

    private fun generatePathFromTitle(title: String): String {
        return "${System.getProperty("user.dir")}/${title.replace(" ", "")}.json"
    }

    fun readFromFile(path: String): Note {
        val noteJSON = File(path).readText()
        val note = gson.fromJson<NoteData>(noteJSON, NoteData::class.java)
        return Note(
            note.title,
            note.modules,
            LocalDateTime.parse(note.dateCreated),
            LocalDateTime.parse(note.dateLastModified),
            note.id,
            note.onlineId,
            note.tags
        )
    }

    fun writeToFile(note: Note): String {
        val notePath = generatePathFromTitle(note.getNoteId().toString())
        val noteData = NoteData(
            note.getNoteId(),
            note.getOnlineId(),
            note.getTitle(),
            note.getCreatedAtDate().toString(),
            note.getLastModifiedDate().toString(),
            note.getModules(),
            note.getTags()
        )
        val jsonString = gson.toJson(noteData, NoteData::class.java)
        File(notePath).writeText(jsonString)
        return notePath
    }

    fun deleteNoteFile(note: Note): Boolean {
        val notePath = generatePathFromTitle(note.getNoteId().toString())
        return File(notePath).delete()
    }

    fun deleteNoteFromTitle(title: String): Boolean {
        val notePath = generatePathFromTitle(title)
        val file = File(notePath)
        if (file.exists()) {
            return file.delete()
        }
        return false
    }

    fun checkIfProfileFileExists(): Boolean {
        val profilePath = generatePathFromTitle(profileName)
        val profileFile = File(profilePath)
        return profileFile.exists()
    }

    fun createNewProfile(name: String) {
        val profileData = ProfileData(name, arrayListOf(), mapOf())
        val jsonString = gson.toJson(profileData, ProfileData::class.java)
        val profilePath = generatePathFromTitle(profileName)
        File(profilePath).writeText(jsonString)
    }

    fun loadProfile(): ProfileData? {
        val profilePath = generatePathFromTitle(profileName)
        val profileFile = File(profilePath)
        val jsonString = profileFile.readText()
        return gson.fromJson(jsonString, ProfileData::class.java)
    }

    fun writeProfile(profile: ProfileData) {
        val profilePath = generatePathFromTitle(profileName)
        val profileFile = File(profilePath)
        val jsonString = gson.toJson(profile, ProfileData::class.java)
        profileFile.writeText(jsonString)
    }

    fun deleteNoteFileFromPath(path: String): Boolean {
        return File(path).delete()
    }
}