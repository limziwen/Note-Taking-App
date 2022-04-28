import logic.ModuleBuilder
import logic.NoteBuilder
import logic.NoteDiskAdapter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import persistence.Tag
import java.io.File
import java.time.LocalDateTime

internal class NoteDiskAdapterTest {
    private val adapter = NoteDiskAdapter()
    private val builder = NoteBuilder()
    private val moduleBuilder = ModuleBuilder()

    var dateCreatedString: String = ""
    var dateLastModifiedString: String = ""

    @Test
    fun readAndWriteToFile() {
        // Create Note
        builder.createNote("This is a test note")
        builder.setTimeModified(LocalDateTime.now())
        var note = builder.getResult()

        // Store date strings
        dateCreatedString = note?.getCreatedAtDate().toString()
        dateLastModifiedString = note?.getLastModifiedDate().toString()

        // Write to file
        adapter.writeToFile(note!!)
        assertTrue(File("${System.getProperty("user.dir")}/-1.json").exists())

        // Read From File
        note = adapter.readFromFile("${System.getProperty("user.dir")}/-1.json")
        assertEquals("This is a test note", note.getTitle())
        assertEquals(LocalDateTime.parse(dateCreatedString), note.getCreatedAtDate())
        assertEquals(LocalDateTime.parse(dateLastModifiedString), note.getLastModifiedDate())
        assertTrue(note.getModules().isNotEmpty())
    }

    @Test
    fun deletesFile() {
        // Create Note
        builder.createNote("This is a test note")
        builder.setTimeModified(LocalDateTime.now())
        var note = builder.getResult()

        // Store date strings
        dateCreatedString = note?.getCreatedAtDate().toString()
        dateLastModifiedString = note?.getLastModifiedDate().toString()

        // Write to file
        adapter.writeToFile(note!!)
        assertTrue(File("${System.getProperty("user.dir")}/-1.json").exists())

        // delete file
        adapter.deleteNoteFile(note)
        assertFalse(File("${System.getProperty("user.dir")}/-1.json").exists())
    }

    @Test
    fun writeFileWithModule() {
        // create note
        builder.createNote("Module Note")
        moduleBuilder.createModule("Text", "This is a note with a module")
        builder.addModule(moduleBuilder.getResult()!!)
        builder.setTimeModified(LocalDateTime.now())

        // write note to file
        adapter.writeToFile(builder.getResult()!!)
        val noteId = builder.getResult()?.getNoteId()
        assertNotNull(noteId)
        assertTrue(File("${System.getProperty("user.dir")}/$noteId.json").exists())

    }

    @Test
    fun writeFileWithTags() {
        builder.createNote("Tagged Note")
        val tag = Tag(1, "Test Tag")
        builder.addTag(tag)

        adapter.writeToFile(builder.getResult()!!)
        val noteId = builder.getResult()?.getNoteId()
        assertNotNull(noteId)
        println(noteId)
        assertTrue(File("${System.getProperty("user.dir")}/$noteId.json").exists())
    }
}