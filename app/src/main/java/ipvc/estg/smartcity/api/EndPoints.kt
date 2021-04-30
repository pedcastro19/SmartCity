package ipvc.estg.smartcity.api


import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    //Todos os users
    @GET("users")
    fun getUsers(): Call<List<User>>

    //User pelo username
    @FormUrlEncoded
    @POST("users/post")
    fun getUserByNome(@Field("username") username: String?): Call<User>

/*    //Todos os Pontos
    @GET("/pontos")
    fun getPontos(): Call<List<Pontos>>

    //Pontos por id
    @GET("/pontos({id}")
    fun getPontosID(@Path("id") id: Int): Call<Pontos>
*/
}