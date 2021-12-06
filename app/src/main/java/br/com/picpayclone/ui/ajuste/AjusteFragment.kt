package br.com.picpayclone.ui.ajuste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.picpayclone.data.UsuarioLogado
import br.com.picpayclone.databinding.FragmentAjustesBinding
import br.com.picpayclone.ui.componente.Componente
import br.com.picpayclone.ui.componente.ComponenteViewModel
import kotlinx.android.synthetic.main.fragment_ajustes.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AjusteFragment : Fragment() {

    private val controlador by lazy { findNavController() }
    private val componenteViewModel: ComponenteViewModel by sharedViewModel()

    private var _binding: FragmentAjustesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAjustesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componenteViewModel.temComponente = Componente(bottomNavigation = true)
        configuraBotaoSair()
        configuraDadosUsuario()
    }

    private fun configuraDadosUsuario() {
        UsuarioLogado.usuario.let { usuario ->
            textViewLoginPrincipal.text = usuario.login
            textViewNomeCompleto.text = usuario.nomeCompleto
            textViewLogin.text = usuario.login
            textViewEmail.text = usuario.email
            textViewNumero.text = usuario.numeroTelefone
        }
    }

    private fun configuraBotaoSair() {
        buttonSair.setOnClickListener {
            val direcao = AjusteFragmentDirections.actionGlobalLoginFragment()
            controlador.navigate(direcao)
        }
    }
}