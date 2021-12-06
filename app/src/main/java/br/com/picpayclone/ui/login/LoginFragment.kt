package br.com.picpayclone.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.picpayclone.R
import br.com.picpayclone.data.Usuario
import br.com.picpayclone.data.UsuarioLogado
import br.com.picpayclone.ui.componente.Componente
import br.com.picpayclone.ui.componente.ComponenteViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val componenteViewModel: ComponenteViewModel by sharedViewModel()

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componenteViewModel.temComponente = Componente(bottomNavigation = false)
        configurarBotaoLogin()
    }

    private fun configurarBotaoLogin() {
        buttonLogin.setOnClickListener {
            UsuarioLogado.usuario = Usuario("lsouza")
            vaiParaHome()
        }
    }

    private fun vaiParaHome() {
        val direcao =
            LoginFragmentDirections.actionLoginFragmentToNavigationHome()
        val controlador = findNavController()
        controlador.navigate(direcao)
    }

}