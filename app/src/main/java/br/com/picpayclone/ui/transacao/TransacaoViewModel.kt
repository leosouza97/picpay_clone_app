package br.com.picpayclone.ui.transacao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.picpayclone.data.Transacao
import br.com.picpayclone.data.Usuario
import br.com.picpayclone.service.ApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class TransacaoViewModel (private val apiService: ApiService): ViewModel() {

    private val _transacao = MutableLiveData<Transacao>()
    val transacao: LiveData<Transacao> = _transacao

    fun transferir(transacao: Transacao){
        viewModelScope.launch {

            try {
                _transacao.value = apiService.realizarTransacao(transacao)
            }catch (e: Exception){
                Log.i("","")
            }


        }
    }

}