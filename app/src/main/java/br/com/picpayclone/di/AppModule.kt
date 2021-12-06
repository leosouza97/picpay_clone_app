package br.com.picpayclone.di

import br.com.picpayclone.service.ApiService
import br.com.picpayclone.service.RetrofitService
import br.com.picpayclone.ui.componente.ComponenteViewModel
import br.com.picpayclone.ui.pagar.PagarViewModel
import br.com.picpayclone.ui.home.HomeViewModel
import br.com.picpayclone.ui.ajuste.AjusteViewModel
import br.com.picpayclone.ui.transacao.TransacaoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { PagarViewModel(get()) }
    viewModel { AjusteViewModel() }
    viewModel { ComponenteViewModel() }
    viewModel { TransacaoViewModel(get()) }
}

val serviceModule = module {
    single { RetrofitService.criarService<ApiService>() }
}