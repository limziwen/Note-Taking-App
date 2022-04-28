package persistence

data class ProfileData(var name: String, val notes: ArrayList<String>, val tags: Map<String, ArrayList<String>>) {}
