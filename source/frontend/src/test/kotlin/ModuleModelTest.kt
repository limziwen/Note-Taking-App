import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import persistence.ImageModule
import persistence.NoteTypeEnum
import persistence.TextModule
import persistence.VideoModule

internal class ModuleModelTest {
    var textModule = TextModule()
    var imageModule = ImageModule()
    var videoModule = VideoModule()

    @Test
    fun hasType() {
        //ho w do we access protected variables?
        assertEquals(NoteTypeEnum.BODY, textModule.getType())
        assertEquals(NoteTypeEnum.IMAGE, imageModule.getType())
        assertEquals(NoteTypeEnum.VIDEO, videoModule.getType())
    }

    @Test
    fun testEmptyContent() {
        assertEquals("", textModule.getContent())
        assertEquals("", imageModule.getContent())
        assertEquals("", videoModule.getContent())
    }

    @Test
    fun testContent(){
        textModule.setContent("test1")
        imageModule.setContent("test2")
        videoModule.setContent("test3")
        assertEquals("test1", textModule.getContent())
        assertEquals("test2", imageModule.getContent())
        assertEquals("test3", videoModule.getContent())
    }
}