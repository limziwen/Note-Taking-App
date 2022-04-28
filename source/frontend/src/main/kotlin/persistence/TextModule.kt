package persistence

class TextModule : Module {
    //properties: font, size, maximum length, date created, date edited
    constructor(type: NoteTypeEnum = NoteTypeEnum.BODY, content: String = "") : super(type) {
        this.setContent(content)
    }
}