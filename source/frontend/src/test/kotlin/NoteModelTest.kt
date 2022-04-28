import persistence.Note
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NoteModelTest {
    // Testing Object
    val testNote = Note("Test note")

    // expect the note to have a title
    @Test
    fun hasTitle() {
        val title = testNote.getTitle()
        assertEquals("Test note", title)
    }

    // expect note to have an empty modules array
    @Test
    fun hasModulesArray() {
        val modules = testNote.getModules()
        assertEquals(arrayListOf<Any>(), modules)
    }

    // expect the createdAt field to be not null
    @Test
    fun hasCreatedDate() {
        val date = testNote.getCreatedAtDate()
        assertNotNull(date)
    }

    @Test
    fun hasLastModifiedDate() {
        val date = testNote.getLastModifiedDate()
        assertNotNull(date)
    }

    @Test
    fun hasTags() {
        val tags = testNote.getTags()
        assertNotNull(tags)
    }

    @Test
    fun hasTagStrings() {
        val tags = testNote.getTagStrings()
        assertNotNull(tags)
    }
}