package com.tunaskelapa.pantunn.data

class DataPantun {
    var genre: String? = null
    var bait1: String? = null
    var bait2: String? = null
    var bait3: String? = null
    var bait4: String? = null

    constructor() {}
    constructor(
        genre: String?,
        bait1: String?,
        bait2: String?,
        bait3: String?,
        bait4: String?
    ) {
        this.genre = genre
        this.bait1 = bait1
        this.bait2 = bait2
        this.bait3 = bait3
        this.bait4 = bait4
    }
}