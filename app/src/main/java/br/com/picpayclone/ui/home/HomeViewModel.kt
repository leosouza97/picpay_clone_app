package br.com.picpayclone.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.picpayclone.data.Transacao
import br.com.picpayclone.data.Usuario
import br.com.picpayclone.data.UsuarioLogado
import br.com.picpayclone.service.ApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val apiService: ApiService) : ViewModel() {

    private val _saldo = MutableLiveData<Double>()
    val saldo: LiveData<Double> = _saldo
    private val _transacoes = MutableLiveData<List<Transacao>>()
    val transacoes: LiveData<List<Transacao>> = _transacoes
    val onError = MutableLiveData<String>()
    val onLoading = MutableLiveData<Boolean>()

    init {
        onLoading.value = true
        viewModelScope.launch {

            try {
                val login = UsuarioLogado.usuario.login
                _saldo.value = apiService.getSaldo(login).saldo
                _transacoes.value = apiService.getTransacoes(login).content
            }catch (e: Exception){
               onError.value = e.message
            }
            onLoading.value = false
        }

    }
}