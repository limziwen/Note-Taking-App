import logic.NoteBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import persistence.Module
import persistence.Note
import persistence.NoteTypeEnum
import persistence.Tag
import persistence.TextModule
import java.time.LocalDateTime

internal class NoteBuilderTest {
    private var builder = NoteBuilder()

    @Test
    fun checkBuilderCreate() {
        val noteBuilder = NoteBuilder("Test 1")
        val note = noteBuilder.getResult()
        assertEquals(true, note?.getTitle() == "Test 1")
    }

    @Test
    fun builderCreatesNullNote() {
        val note = builder.getResult()
        assertNull(note)
    }

    @Test
    fun checkBuilderCreatesNoteWithTitle() {
        builder = NoteBuilder("Test")
        val note = builder.getResult()
        assertEquals(true, note?.getTitle() == "Test")
    }

    @Test
    fun checkNoteResets() {
        builder.createNote("Test 6")
        var note = builder.getResult()
        assertEquals(true, note?.getTitle() == "Test 6")
        builder.reset()
        note = builder.getResult()
        assertNull(note)
    }

    @Test
    fun checkLastModifiedUpdates() {
        var note = builder.getResult()
        val prevDate = note?.getLastModifiedDate()
        builder.setTimeModified(LocalDateTime.now())
        note = builder.getResult()
        val currentDate = note?.lastModifiedDate
        if (currentDate != null) {
            assertEquals(true, currentDate > prevDate)
        }
    }

    @Test
    fun createsNote() {
        builder.createNote("Test 4")
        val note = builder.getResult()
        assertEquals("Test 4", note?.getTitle())
    }

    @Test
    fun updatesModules() {
        builder.createNote("Test 5")
        var note = builder.getResult()
        var modules = note?.modules
        assertNotNull(modules)
        assertEquals(NoteTypeEnum.TITLE, modules?.get(0)?.getType())
        assertEquals("Test 5", modules?.get(0)?.getContent())
        val testModule = TextModule(NoteTypeEnum.BODY)
        builder.addModule(testModule)
        note = builder.getResult()
        modules = note?.modules
        assertTrue(modules?.contains(testModule) == true)
    }

    @Test
    fun attachesNote() {
        var newNote = Note("New Note")
        builder.createNote("Old Note")
        var oldNote = builder.getResult()
        assertEquals("Old Note", oldNote?.getTitle())
        builder.attachNote(newNote)
        assertEquals("New Note", builder.getResult()?.getTitle())
    }

    @Test
    fun addAndRemoveTag() {
        builder.createNote("Test 6")
        val tag = Tag(1, "Test Tag")
        builder.addTag(tag)
        assertEquals(tag, builder.getResult()?.tags?.get(0))
        builder.removeTag(tag)
        assertTrue(builder.getResult()?.tags?.isEmpty()!!)
    }
}