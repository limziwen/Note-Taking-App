package persistence

open class Module {
    private val type: NoteTypeEnum
    private var content: String

    constructor(type: NoteTypeEnum, content: String = ""){
        this.type = type
        this.content = content

    }
    fun getType(): NoteTypeEnum {return this.type}

    fun getContent(): String{return this.content}
    fun setContent(content: String){
        this.content = content
    }

}
