package latheesh.com.dictionary.model


data class ResultData(
    val id: Long = 0,
    val definition: String = "",
    val thumbUp: Int = 0,
    val thumbDown: Int = 0,
    val word: String = "",
    val author: String = "",
    val example: String = ""
)