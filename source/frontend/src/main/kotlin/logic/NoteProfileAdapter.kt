package logic

import persistence.Note
import persistence.NoteMetaData
import persistence.Tag
import java.sql.*
import java.time.LocalDateTime
import java.time.format.DateTimeParseException


class NoteProfileAdapter {
    private var connection: Connection? = null

    fun connectToDb(name: String = "profile") {
        var conn: Connection? = null
        try {
            val url = "jdbc:sqlite:./src/main/resources/${name}.db"
            conn = DriverManager.getConnection(url)
            println("Connection to SQLite has been established.")
        } catch (e: SQLException) {
            println(e.message)
        }
        connection = conn
    }

    fun getDBConnection(): Connection? {
        return connection;
    }

    private fun query(sql: String): ResultSet? {
        return try {
            if (connection == null || connection?.isClosed == true) {
                connectToDb()
            }
            val query = connection!!.createStatement()
            val results = query.executeQuery(sql)
            results
        } catch (ex: SQLException) {
            println(ex.message)
            null
        }
    }

    private fun update(sql: String): Int {
        return try {
            if (connection == null || connection?.isClosed == true) {
                connectToDb()
            }

            val query = connection!!.createStatement()
            val results = query.executeUpdate(sql)
            connection?.close()
            results
        } catch (ex: SQLException) {
            println(ex.message)
            -1
        }
    }

    fun createNotesTable() {
        try {
            val sql =
                "create table notes (title varchar(100) NOT NULL, dateCreated varchar(255) NOT NULL, datemodified varchar(255) NOT NULL, file VARCHAR(255), onlineid VARCHAR(255), online BOOLEAN)"
            val result = update(sql)
            if (result == -1) {
                throw SQLException()
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
    }

    fun createTagsTable() {
        try {
            val sql = "create table tags (title varchar(100) NOT NULL)"
            val result = update(sql)
            if (result == -1) {
                throw  SQLException()
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
    }

    fun createTagNoteRelation() {
        try {
            val sql = "create table tagnotes (noteId INTEGER NOT NULL, tagId INTEGER NOT NULL, FOREIGN KEY (noteId) REFERENCES notes(rowid), FOREIGN KEY (tagId) REFERENCES tags(rowid))"
            val result = update(sql)
            if (result == -1) {
                throw  SQLException()
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
    }

    fun checkIfTableExists(table: String): Boolean {
        if (connection == null || connection?.isClosed == true) {
            connectToDb()
        }
        val dbm: DatabaseMetaData? = connection?.metaData
        val tables = dbm?.getTables(null, null, table, null)
        if (connection != null) {
            connection!!.close()
        }
        if (tables != null) {
            return tables.next()
        }
        return false
    }

    fun getLatestItem(table: String): Int {
        val sql = "select max(rowid) from $table"
        val result = query(sql)
        return result?.getInt("max(rowid)") ?: 0
    }

    fun createNoteRecord(
        title: String,
        dateCreated: LocalDateTime = LocalDateTime.now(),
        dateModified: LocalDateTime = LocalDateTime.now(),
        file: String? = null,
        onlineId: String? = null,
        online: Boolean = true): Int? {
        if (!checkIfTableExists("notes")) {
            createNotesTable()
        }
        val fileString = if (file == null) { "null" } else { "\"$file\"" }
        val onlineIdString = if (onlineId == null) {"null"} else {"\"$onlineId\""}
        val sql = "insert into notes values (\"$title\", \"$dateCreated\", \"$dateModified\", $fileString, $onlineIdString, $online)"
        val result = update(sql)
        if (result > 0) {
            val item =  getLatestItem("notes")
            connection?.close()
            return item
        }
        return null
    }

    fun createNoteRecordFromNote(note: Note, path: String? = null): Int? {
        return createNoteRecord(note.getTitle(), note.getCreatedAtDate(), note.getLastModifiedDate(), path)
    }

    fun queryNoteById(id: Int): NoteMetaData? {
        try {
            val sql = "select rowid, * from notes where rowid = $id"
            val result = query(sql)
            val resId = result?.getInt("rowid")
            val resTitle = result?.getString("title")
            val resDateCreated = LocalDateTime.parse(result?.getString("datecreated"))
            val resDateModified = LocalDateTime.parse(result?.getString("datemodified"))
            val resFile = result?.getString("file")
            val resOnlineId = result?.getString("onlineid")
            val resOnline = result?.getBoolean("online")
            connection?.close()
            if (resId != null && resTitle != null) {
                return NoteMetaData(
                    resId,
                    resTitle,
                    resDateCreated,
                    resDateModified,
                    resFile,
                    resOnlineId,
                    null,
                    resOnline
                )
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
            return null
        } catch (e: DateTimeParseException) {
            println(e.message)
            return null
        }
        return null
    }

    fun deleteNoteById(id: Int): Boolean {
        try {
            val sql = "delete from notes where rowid = $id"
            val result = update(sql)
            if (result != 1) {
                return false
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
        return  true
    }

    fun updateNote(
        id: Int,
        title: String? = null,
        dateModified: LocalDateTime? = null,
        path: String? = null,
        onlineId: String? = null,
        online: Boolean? = null
    ): Boolean {
        try {
            if (title == null && dateModified == null && path == null && onlineId == null && online == null) {
                throw Error("All 5 fields are null, failed to update")
            }
            var sql = "update notes set "
            if (dateModified != null) {
                sql += "datemodified = \"$dateModified\""
                if (title != null || path != null || onlineId != null || online != null) {
                    sql += ", "
                }
            }
            if (title != null) {
                sql += "title = \"$title\""
                if (path != null || onlineId != null || online != null) {
                    sql += ", "
                }
            }
            if (path != null) {
                sql += "file = \"$path\""
                if (onlineId != null || online != null) {
                    sql += ", "
                }
            }

            if (onlineId != null) {
                sql += "onlineId = \"$onlineId\""
                if (online != null) {
                    sql += ", "
                }
            }
            if (online != null) {
                sql += "online = $online"
            }

            sql += " where rowid = $id"
            val result = update(sql)
            if (result != 1) {
                return false
            }
        } catch(e: SQLException) {
            println(e.stackTrace)
        }
        return true
    }

    fun dropTable(table: String) {
        val sql = "drop table $table"
        val result = update(sql)
    }

    fun getAllNotes(tags: ArrayList<Tag>? = null): ArrayList<NoteMetaData> {
        var sql = ""
        sql = if (tags != null) {
            getNotesByTags(tags)
        } else {
            "select rowid, * from notes"
        }
        val res = query(sql)
        val dataArray = arrayListOf<NoteMetaData>()
        if (res != null) {
            while (res.next()) {
                val id = res.getInt("rowid")
                val title = res.getString("title")
                val dateCreated = res.getString("dateCreated")
                val dateModified = res.getString("dateModified")
                val file = res.getString("file")
                val onlineId = res.getString("onlineid")
                val tags = getTagsByNoteId(id)
                val online = res.getBoolean("online")
                val noteMetaData = NoteMetaData(
                    id,
                    title,
                    LocalDateTime.parse(dateCreated),
                    LocalDateTime.parse(dateModified),
                    file,
                    onlineId,
                    tags,
                    online
                )
                dataArray.add(noteMetaData)
            }
        }
        return dataArray
    }
    
    fun addTag(name: String): Int? {
        if (!checkIfTableExists("tags") || !checkIfTableExists("tagnotes")) {
            createTagsTable()
            createTagNoteRelation()
        }
        val sql = "insert into tags values (\"${name}\")"
        val result = update(sql)
        if (result > 0) {
            val item = getLatestItem("tags")
            connection?.close()
            return item
        }
        return null
    }

    fun getTagById(id: Int): Tag? {
        try {
            val sql = "select rowid, * from tags where rowid = $id"
            val result = query(sql)
            val resId = result?.getInt("rowid")
            val resTitle = result?.getString("title")
            connection?.close()
            if (resId != null && resTitle != null) {
                return Tag(resId, resTitle)
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
            return null
        } catch (e: DateTimeParseException) {
            println(e.message)
            return null
        }
        return null
    }

    fun updateTag(id: Int, title: String): Boolean {
        try {
            val sql = "update tags set title = \"$title\" where rowid = $id"
            val result = update(sql)
            if (result != 1) {
                return false
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
        return true
    }

    fun deleteTag(id: Int): Boolean {
        try {
            val sql = "delete from tags where rowid = $id"
            val result = update(sql)
            if (result != 1) {
                return false
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
        return true
    }

    fun addTagNoteRelation(tagId: Int, noteId: Int): Int? {
        try {
            val sql = "insert into tagnotes values ($noteId, $tagId)"
            val result = update(sql)
            if (result > 0) {
                val item = getLatestItem("tagnotes")
                connection?.close()
                return item
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
        return  null
    }

    fun getTagByTitle(title: String): Tag? {
        try {
            val sql = "select rowid, * from tags where title = \"$title\""
            val result = query(sql)
            if (result != null) {
                while (result.next()) {
                    val id = result.getInt("rowid")
                    val title = result.getString("title")
                    return Tag(id, title)
                }
            }
        } catch (e: SQLException) {
            println(e.stackTrace)
        }
        return null
    }

    fun getLatestTagId(): Int {
        return getLatestItem("tags")
    }

    fun getNotesByTags(tags: ArrayList<Tag>): String {
        var tagIdList = "("
        tags.forEachIndexed { index, tag ->
            tagIdList += tag.id
            if (index != tags.size - 1) {
                tagIdList += ", "
            }
        }
        tagIdList += ")"
        return "select n.rowid, n.* from notes n, tagnotes r " +
                "where n.rowid in " +
                "(select noteId from tagnotes where tagId in $tagIdList) " +
                "group by n.rowid"
    }

    fun getAllTags(): ArrayList<Tag> {
        val sql = "select rowid, * from tags"
        val returnArray = arrayListOf<Tag>()
        val res = query(sql)
        if (res != null) {
            while (res.next()) {
                val id = res.getInt("rowid")
                val title = res.getString("title")
                returnArray.add(Tag(id, title))
            }
        }
        return returnArray
    }

    fun getTagsByNoteId(noteId: Int): ArrayList<Tag> {
        val sql = "select t.rowid, t.title from tags t where t.rowid in (select r.tagId from tagnotes r where r.noteId = $noteId)"
        val res = query(sql)
        val tags = arrayListOf<Tag>()
        if (res != null) {
            while (res.next()) {
                val tagId = res.getInt("rowid")
                val title = res.getString("title")
                tags.add(Tag(tagId, title))
            }
        }
        return tags
    }
}