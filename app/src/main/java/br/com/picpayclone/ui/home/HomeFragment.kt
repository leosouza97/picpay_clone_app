package br.com.picpayclone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.picpayclone.data.UsuarioLogado
import br.com.picpayclone.databinding.FragmentHomeBinding
import br.com.picpayclone.extension.formatarMoeda
import br.com.picpayclone.ui.componente.Componente
import br.com.picpayclone.ui.componente.ComponenteViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val componenteViewModel: ComponenteViewModel by sharedViewModel()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (UsuarioLogado.isUsuarioNaoLogado()){
            vaiParaLogin()
        }
        componenteViewModel.temComponente = Componente(bottomNavigation = true)
        observarSaldo()
        observarErro()
        observarTransacoes()
        observarProgress()

    }

    private fun observarProgress() {
        homeViewModel.onLoading.observe(viewLifecycleOwner, Observer { onLoading ->
            if (onLoading) {
                progressBarTransferencia.visibility = VISIBLE
                recyclerView.visibility = GONE
            } else {
                progressBarTransferencia.visibility = GONE
                recyclerView.visibility = VISIBLE
            }

        })
    }

    private fun observarTransacoes() {
        homeViewModel.transacoes.observe(viewLifecycleOwner, Observer {
            it?.let { transacoes ->
                recyclerView.adapter = HomeAdapter(transacoes)
            }
        })
    }

    private fun observarErro() {
        homeViewModel.onError.observe(viewLifecycleOwner, Observer {
            it?.let { mensage ->
                Toast.makeText(this@HomeFragment.context, mensage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun observarSaldo() {
        homeViewModel.saldo.observe(viewLifecycleOwner, Observer {
            it?.let { saldo ->
                textViewSaldo.text = saldo.formatarMoeda()
            }
        })
    }

    private fun vaiParaLogin() {
        val direcao =
            HomeFragmentDirections.actionGlobalLoginFragment()
        val controlador = findNavController()
        controlador.navigate(direcao)
    }
}