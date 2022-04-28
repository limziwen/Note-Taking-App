import logic.NoteProfileAdapter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDateTime

internal class NoteProfileAdapterTest {
    private val adapter = NoteProfileAdapter()
    private var testNoteId = 1
    private var testTagId = 0
    private var dateModified = LocalDateTime.now()

    @Test
    fun createsDB() {
        adapter.connectToDb()
        val dbFile = File("${System.getProperty("user.dir")}/src/main/resources/profile.db")
        assertTrue(dbFile.exists())
        assertNotNull(adapter.getDBConnection())
    }

    @Test
    fun createsNotesTable() {
        val exists = adapter.checkIfTableExists("notes")
        if (exists) {
            adapter.dropTable("notes")
            assertFalse(adapter.checkIfTableExists("notes"))
        }
        adapter.createNotesTable()
        assertTrue(adapter.checkIfTableExists("notes"))
    }

    @Test
    fun createsTagsTable() {
        adapter.createTagsTable()
        assertTrue(adapter.checkIfTableExists("tags"))
    }

    @Test
    fun createsTagNotesTable() {
        adapter.createTagNoteRelation()
        assert(adapter.checkIfTableExists("tagnotes"))
    }

    @Test
    fun crudsNoteEntry() {
        adapter.dropTable("notes")
        adapter.createNotesTable()
        val noteId = adapter.createNoteRecord("Test Note")
        assertNotNull(noteId)
        if (noteId != null) {
            assertTrue(noteId > 0)
            val noteResult = adapter.queryNoteById(noteId)
            testNoteId = noteResult!!.id
            assertNotNull(noteResult)
            assertEquals("Test Note", noteResult?.title)

            adapter.updateNote(noteId, "Updated Title", dateModified)
            val result = adapter.queryNoteById(testNoteId)
            assertEquals(dateModified, result!!.dateModified)
            assertEquals("Updated Title", result.title)

            val deleteResult = adapter.deleteNoteById(noteId)
            assertTrue(deleteResult)
        }
    }

    @Test
    fun crudsTagEntry() {
        var tagName = "test tag"
        adapter.dropTable("tags")
        adapter.createTagsTable()
        val tagId = adapter.addTag(tagName)
        assertNotNull(tagId)
        if (tagId != null) {
            val tag = adapter.getTagById(tagId)
            assertNotNull(tag)
            if (tag != null) {
                testTagId = tag.id
                assertEquals(tagName, tag.name)

                tagName = "updated tag"
                adapter.updateTag(testTagId, tagName)
                val res = adapter.getTagById(tagId)
                if (res != null) {
                    assertEquals(tagName, res.name)
                }

                val deleteResult = adapter.deleteTag(testTagId)
                assertTrue(deleteResult)
            }
        }
    }
}