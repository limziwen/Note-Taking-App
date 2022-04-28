package models

import org.bson.types.ObjectId

data class ProfileData(
    var name: String ?= null,
    var _id: ObjectId ?= null,
    ){}
