package ipvc.estg.smartcity.api

data class Pontos (
    val id: Int,
    val descricao: String,
    val lat: String,
    val lon: String,
    val tipo: String,
    val user_id: Int,
    val titulo: String
)