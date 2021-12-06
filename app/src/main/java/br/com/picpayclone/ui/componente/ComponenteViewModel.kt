package br.com.picpayclone.ui.componente

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComponenteViewModel : ViewModel() {

    val _componentes = MutableLiveData<Componente>().also {
        it.value = temComponente
    }

    var temComponente = Componente()
        set(value){
            field = value
            _componentes.value = value
        }


}

data class Componente(

    val bottomNavigation: Boolean = false

)