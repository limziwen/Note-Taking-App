package com.cs398.plugins

import com.google.gson.Gson
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoDatabase
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.ClientNoteData
import models.NoteData
import models.ProfileData
import org.litote.kmongo.*

//https://litote.org/kmongo/
var dbUri = "mongodb+srv://cs398:cs398@cluster0.in0ry.mongodb.net/NotesApp?retryWrites=true&w=majority"
val client = KMongo.createClient(dbUri)
val database: MongoDatabase = client.getDatabase("NotesApp")
//How to import data class?

val profileCollection = database.getCollection<ProfileData>("ProfileData")
val noteCollection = database.getCollection<NoteData>("NoteData")
private val gson = Gson()

fun Application.configureRouting() {
    // Starting point for a Ktor app:
    routing {
        route("/note"){
            post{
                try{
                    val note = call.receive<NoteData>()
                    note.apply { noteCollection.insertOne(note) }
                    //returns id of inserted note
                    call.respondText(note._id.toString())
                } catch (e: Error) {
                    println("Error ${e.message}")
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
            get{
                try{
                    val allNotes : FindIterable<NoteData> = noteCollection.find()
                    val listNotes: ArrayList<NoteData> = ArrayList()

                    for (doc in allNotes) {
                        var test: NoteData = doc
                        listNotes += doc
                    }

                    // println(listNotes)
                    call.respondText(gson.toJson(listNotes))
                }catch (e : Error){
                    println("error msg: ${e.message}")
                    throw Exception(e.message)
                }
            }
            route("/{id}") {
                get {
                    try {
                        println("getting note ${call.parameters["id"]}")
                        val note = noteCollection.findOne(NoteData::_id eq call.parameters["id"].toString())
                        if (note == null) {
                            println("No note found")
                        } else {
                            val noteClientData = NoteData(
                                note._id.toString(),
                                note.title,
                                note.dateCreated,
                                note.dateLastModified,
                                note.modules,
                            )
                            call.respond(gson.toJson(noteClientData))
                        }
                    } catch (e: Error) {
                        println("error msg: ${e.message}")
                        throw Exception(e.message)
                    }
                }
            }
            put {
                try{
                    val note = call.receive<ClientNoteData>()
                    noteCollection.updateOne(NoteData::_id eq note.onlineId, note)
                    call.respond(HttpStatusCode.OK)
                } catch (e: Error) {
                    println("Error ${e.message}")
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}
