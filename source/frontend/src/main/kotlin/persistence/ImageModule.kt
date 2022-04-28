package persistence

class ImageModule : Module {
    constructor(content: String = "") : super(NoteTypeEnum.IMAGE){
        this.setContent(content)
    }
}