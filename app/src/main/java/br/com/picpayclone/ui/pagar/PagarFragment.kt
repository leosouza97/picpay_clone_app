package br.com.picpayclone.ui.pagar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.picpayclone.data.Usuario
import br.com.picpayclone.databinding.FragmentPagarBinding
import br.com.picpayclone.ui.componente.Componente
import br.com.picpayclone.ui.componente.ComponenteViewModel
import kotlinx.android.synthetic.main.fragment_pagar.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PagarFragment : Fragment() {

    private val controlador by lazy { findNavController() }
    private val pagarViewModel: PagarViewModel by viewModel()
    private val componenteViewModel: ComponenteViewModel by sharedViewModel()
    private var _binding: FragmentPagarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPagarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componenteViewModel.temComponente = Componente(bottomNavigation = true)
        observarContatos()
    }

    private fun observarContatos() {
        pagarViewModel.contatos.observe(viewLifecycleOwner, Observer {
            it?.let { usuarios ->
                configurarRecycleView(usuarios)
            }
        })
    }

    private fun configurarRecycleView(usuarios: List<Usuario>) {
        recyclerView.adapter = PagarAdapter(usuarios){usuario ->
            vaiParaTransacao(usuario)
        }
    }

    private fun vaiParaTransacao(usuario: Usuario) {
        val direcao =
            PagarFragmentDirections.actionNavigationPagarToNavigationTransferencia(usuario)
        controlador.navigate(direcao)
    }
}