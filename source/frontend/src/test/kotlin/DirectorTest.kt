import logic.Director
import logic.NoteDiskAdapter
import logic.NoteProfileAdapter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import persistence.NoteTypeEnum
import java.io.File

internal class DirectorTest {
    private val director = Director()
    private val profileAdapter = NoteProfileAdapter()

    @Test
    fun createsNewNote() {
        director.createNote("Test 1")
        val note = director.getNoteObject()
        assertEquals("Test 1", note?.getTitle())
    }

    @Test
    fun writesNoteToFile() {
        director.createNote("Test 3")
        var note = director.getNoteObject()
        var noteId = note?.getNoteId()
        assertEquals("Test 3", note?.getTitle())
        director.saveNote(noteId!!)
        assertTrue(File("${System.getProperty("user.dir")}/$noteId.json").exists())
    }

    @Test
    fun deletesFileFromDisk() {
        val noteName = "Test 4"
        director.createNote(noteName)
        val note = director.getNote()
        director.saveNote(note!!.id)
        director.deleteNoteFile(noteName)
        assertFalse(File("${System.getProperty("user.dir")}/${noteName.replace(" ", "")}.json").exists())
        director.writeProfile()
        val adapter = NoteDiskAdapter()
        val profile = adapter.loadProfile()
        val notes = profile?.notes
        assertFalse(notes!!.contains(noteName))
    }

    @Test
    fun createModuleAddToNote() {
        var testName = "Test 5"
        director.createNote(testName)
        director.createModuleAddToNote("Text", testName)
        //retrieve module from a note
        var module = director.getModulesFromNote()?.last()
        assertNotNull(module)
        assertEquals(NoteTypeEnum.BODY, module!!.getType())
        assertEquals(testName, module.getContent())
    }

    @Test
    fun deleteModuleFromNote() {
       /* var testName = "Test 6"
        director.createNote(testName)
        director.createModuleAddToNote("Text", testName)
        director.deleteModuleFromNote(0)
        assertEquals(0, director.getModulesFromNote()?.size)*/
    }

    @Test
    fun getModulesFromNote() {
        /*var testName = "Test 7"
        director.createNote(testName)
        director.createModuleAddToNote("Text", testName)
        var modules = director.getModulesFromNote()
        assertNotNull(modules)
        assertEquals(1, modules?.size)
        assertEquals(NoteTypeEnum.BODY, modules?.get(0)?.getType())
        assertEquals(testName, modules?.get(0)?.getContent())*/
    }

    @Test
    fun createsNewProfile() {
        director.updateName()
        assertTrue(File("${System.getProperty("user.dir")}/profile.json").exists())
        assertEquals("Untitled User", director.getProfileName())
    }

    @Test
    fun writesToProfileAndLoad() {
        director.updateName("Khalid Talakshi")
        assertEquals("Khalid Talakshi", director.getProfileName())
        director.writeProfile()
        val adapter = NoteDiskAdapter()
        val newProfile = adapter.loadProfile()
        assertEquals("Khalid Talakshi", newProfile?.name)
    }

    @Test
    fun savesNote() {
        /*val noteName = "Test 4"
        director.createNote(noteName)
        var note = director.getNoteObject()
        assertEquals(noteName, note?.getTitle())
        director.saveNote(note!!.id)
        val id = director.getNote()?.getNoteId()
        assertTrue(File("${System.getProperty("user.dir")}/${id.toString().replace(" ", "")}.json").exists())
        director.writeProfile()
        val adapter = NoteDiskAdapter()
        val newProfile = adapter.loadProfile()
        assertEquals(arrayListOf(noteName), newProfile?.notes)*/
    }

    @Test
    fun loadsNote() {
        profileAdapter.dropTable("notes")
        profileAdapter.createNotesTable()
        director.createNote("Test 6")
        val result = director.getNote()
        assertNotNull(result)
        val id = result!!.getNoteId()
        director.resetNoteBuilder()
        assertNull(director.getNote())
        director.loadNote(id)
        val loadResult = director.getNote()
        assertNotNull(loadResult)
        assertEquals(id, loadResult?.getNoteId())
        assertEquals("Test 6", loadResult?.getTitle())
    }

    @Test
    fun updatesTitle() {
        profileAdapter.dropTable("notes")
        profileAdapter.createNotesTable()
        director.createNote("Test 8")
        val result = director.getNote()
        assertNotNull(result)
        assertEquals("Test 8", director.getNote()?.getTitle())
        director.updateNote(result!!.getNoteId(), "Test 9")
        val id = director.getNote()?.getNoteId()
        if (id != null) {
            val diskAdapter = NoteDiskAdapter()
            val result = diskAdapter.readFromFile("${System.getProperty("user.dir")}/${id.toString().replace(" ", "")}.json")
            assertEquals("Test 9", result.getTitle())
        }
    }

    @Test
    fun deletesNote() {
        profileAdapter.dropTable("notes")
        profileAdapter.createNotesTable()
        director.createNote("Test 8")
        assertNotNull(director.getNote())
        val id = director.getNote()!!.id
        val entry = profileAdapter.queryNoteById(id)
        assertNotNull(entry)
        assertTrue(File("${System.getProperty("user.dir")}/$id.json").exists())
        director.deleteNote(id)
        assertFalse(File("${System.getProperty("user.dir")}/$id.json").exists())
        val entry2 = profileAdapter.queryNoteById(id)
        assertNull(entry2)
        assertNull(director.getNote())
    }

    @Test
    fun createsTag() {
        val tag = director.createTag("Test Tag")
        assertNotNull(tag)
        assertEquals("Test Tag", tag?.name)
    }

    @Test
    fun deletesTag() {
        val tag = director.createTag("Test Tag 2")
        assertNotNull(tag)
        assertNotNull(tag?.id)
        if (tag?.id != null) {
            val result = director.deleteTag(tag.id)
            assertTrue(result)
        }
    }

//    @Test
//    fun updatesTag() {
//        val tag = director.createTag("Test Tag 3")
//        assertNotNull(tag)
//        assertNotNull(tag?.id)
//        val updateRes = director.updateTag(tag!!.id, "Updated Test Tag 3")
//        assertTrue(updateRes)
//    }

    @Test
    fun addsTagToNote() {
        /*director.createNote("Test 9")
        val noteId = director.getNote()?.getNoteId()
        assertNotNull(noteId)
        val tag = director.createTag("Test Tag 4")
        assertNotNull(tag)
        director.addTagToNote(tag!!)
        assertFalse(director.getNote()!!.getTags().isEmpty())
        val tagFromArray = director.getNote()?.getTags()?.find {
            it.id == tag.id
        }
        assertNotNull(tagFromArray)*/
    }

    @Test
    fun getsAllTags() {
        profileAdapter.dropTable("tags")
        director.createTag("Test 1")
        director.createTag("Test 2")
        val tags = director.getAllTags()
        assertFalse(tags.isEmpty())
        assertEquals(2, tags.size)
        val tag1 = tags[0]
        val tag2 = tags[1]
        assertEquals("Test 1", tag1.name)
        assertEquals("Test 2", tag2.name)
    }
}