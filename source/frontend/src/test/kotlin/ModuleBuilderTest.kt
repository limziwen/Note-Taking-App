import logic.ModuleBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import persistence.Note
import persistence.NoteTypeEnum

internal class ModuleBuilderTest {
    private var builder = ModuleBuilder()

    @Test
    fun checkModuleCreate() {
        val moduleBuilder = ModuleBuilder("Text")
        val module = moduleBuilder.getResult()
        assertEquals(module?.getType(), NoteTypeEnum.BODY)

        val moduleBuilder2 = ModuleBuilder("Image")
        val module2 = moduleBuilder2.getResult()
        assertEquals(module2?.getType() ,NoteTypeEnum.IMAGE)

        val moduleBuilder3 = ModuleBuilder("Video")
        val module3 = moduleBuilder3.getResult()
        assertEquals(module3?.getType() ,NoteTypeEnum.VIDEO)
    }

    @Test
    fun builderCreatesNullModule() {
        val emptyModule = builder.getResult()
        assertNull(emptyModule)
    }

    @Test
    fun checkBuilderCreatesModuleWithContent() {
        builder.createModule("Text", "Taking notes")
        val textModule = builder.getResult()
        assertEquals(textModule?.getContent(), "Taking notes")

        builder.createModule("Image", "${System.getProperty("user.dir")}/image.jpg")
        val imageModule = builder.getResult()
        assertEquals(imageModule?.getContent() ,"${System.getProperty("user.dir")}/image.jpg")

        builder.createModule("Video", "${System.getProperty("user.dir")}/video.mp4")
        val videoModule = builder.getResult()
        assertEquals(videoModule?.getContent(),"${System.getProperty("user.dir")}/video.mp4")
    }

    @Test
    fun checkModuleResets() {
        builder.createModule("Text", "Test")
        var module = builder.getResult()
        assertEquals(module?.getType(), NoteTypeEnum.BODY)
        assertEquals(module?.getContent(), "Test")
        builder.reset()
        assertNull(builder.getResult())
    }
}