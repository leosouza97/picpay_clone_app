package br.com.picpayclone.ui.pagar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.picpayclone.data.Usuario
import br.com.picpayclone.data.UsuarioLogado
import br.com.picpayclone.service.ApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class PagarViewModel(private val apiService: ApiService) : ViewModel() {

    private val _contatos = MutableLiveData<List<Usuario>>()
    val contatos: LiveData<List<Usuario>> = _contatos


    init {
        viewModelScope.launch {
            try {
                val login = UsuarioLogado.usuario.login
                _contatos.value = apiService.getContatos(login)
            } catch (e: Exception){
                Log.e("erro", e.message ?: "")
            }

        }

    }

}