package logic

import persistence.*
import java.time.LocalDateTime

class ModuleBuilder: Builder {

    private var module: Module? = null

    constructor(type: String = "", content: String = ""){
        this.createModule(type, content)
    }

    fun setContent(content: String) {
        this.module?.setContent(content)
    }

    fun getResult(): Module? {
        return if(this.module != null){
            this.module
        }else{
            null
        }
    }

    override fun reset() {
        this.module = null
    }

    fun createModule(type: String, content: String = ""){
        when (type) {
            "Text" -> {
                this.module = TextModule(content = content)
            }
            "Image" -> {
                this.module = ImageModule(content)
            }
            "Video" -> {
                this.module = VideoModule(content)
            }
            "" -> {
                this.module = null
            }
        }
    }


}