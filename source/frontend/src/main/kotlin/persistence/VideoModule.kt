package persistence

class VideoModule : Module{
    constructor(content: String = ""): super(NoteTypeEnum.VIDEO) {
        this.setContent(content)
    }
}