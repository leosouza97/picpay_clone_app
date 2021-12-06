package br.com.picpayclone.service

import br.com.picpayclone.data.PageTransacao
import br.com.picpayclone.data.Transacao
import br.com.picpayclone.data.Usuario
import retrofit2.http.*

interface ApiService {

    @GET("/usuarios/contatos/")
    suspend fun getContatos(@Query("login") login: String): List<Usuario>

    @GET("/usuarios/{login}/saldo")
    suspend fun getSaldo(@Path("login") login: String): Usuario

    @POST("/transacoes")
    suspend fun realizarTransacao(@Body transacao: Transacao): Transacao

    @GET("/transacoes")
    suspend fun getTransacoes(@Query("login") login: String): PageTransacao

}