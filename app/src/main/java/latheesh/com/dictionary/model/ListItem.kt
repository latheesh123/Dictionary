package latheesh.com.dictionary.model

import com.google.gson.annotations.SerializedName


data class ListItem(

    @field:SerializedName("defid")
    val defid: Long? = null,

    @field:SerializedName("sound_urls")
    val soundUrls: List<Any?>? = null,

    @field:SerializedName("thumbs_down")
    val thumbsDown: Int? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("written_on")
    val writtenOn: String? = null,

    @field:SerializedName("definition")
    val definition: String? = null,

    @field:SerializedName("permalink")
    val permalink: String? = null,

    @field:SerializedName("thumbs_up")
    val thumbsUp: Int? = null,

    @field:SerializedName("word")
    val word: String? = null,

    @field:SerializedName("current_vote")
    val currentVote: String? = null,

    @field:SerializedName("example")
    val example: String? = null
)