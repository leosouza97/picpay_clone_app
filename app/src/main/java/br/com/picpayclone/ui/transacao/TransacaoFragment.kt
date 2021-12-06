package br.com.picpayclone.ui.transacao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.picpayclone.R
import br.com.picpayclone.data.CartaoCredito
import br.com.picpayclone.data.Transacao
import br.com.picpayclone.data.UsuarioLogado
import br.com.picpayclone.databinding.FragmentTransferenciaBinding
import br.com.picpayclone.extension.formatar
import br.com.picpayclone.ui.componente.Componente
import br.com.picpayclone.ui.componente.ComponenteViewModel
import kotlinx.android.synthetic.main.fragment_transferencia.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class TransacaoFragment : Fragment() {

    private var _binding: FragmentTransferenciaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val argumentos by navArgs<TransacaoFragmentArgs>()
    private val transacaoViewModel: TransacaoViewModel by viewModel()
    private val usuario by lazy { argumentos.usuario }
    private val controlador by lazy { findNavController() }
    private val componenteViewModel: ComponenteViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTransferenciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componenteViewModel.temComponente = Componente(bottomNavigation = false)
        configurarUsuario()
        configurarRadioGroup()
        configurarBotaoTransferir()
        observarTransacao()

    }

    private fun observarTransacao() {
        transacaoViewModel.transacao.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val direcao =
                TransacaoFragmentDirections.actionNavigationTransferenciaToNavigationPagar()
            controlador.navigate(direcao)

        })
    }

    private fun configurarBotaoTransferir() {
        buttonTransferir.setOnClickListener {
            val isCartaoCredito = radioButtonCartaoCredito.isChecked
            val valor = getValor()
            val transacao = if (isCartaoCredito) {
                criarTransferenciaCartao(isCartaoCredito, valor)
            } else {
                criarTransferenciaSaldo(valor)
            }
            transacaoViewModel.transferir(transacao)
        }
    }

    private fun criarTransferenciaSaldo(valor: Double): Transacao {
        return Transacao(
            Transacao.gerarHash(),
            UsuarioLogado.usuario,
            usuario,
            Calendar.getInstance().formatar(),
            false,
            valor,
            criarCartaoCredito()
        )
    }

    private fun criarTransferenciaCartao(cartaoCredito: Boolean, valor: Double): Transacao {

        return Transacao(
            Transacao.gerarHash(),
            UsuarioLogado.usuario,
            usuario,
            Calendar.getInstance().formatar(),
            cartaoCredito,
            valor,
            criarCartaoCredito()
        )
    }

    private fun getValor(): Double{
        val valor = editTextValor.text.toString()
        return if (valor.isEmpty()){
            0.0
        }else{
            valor.toDouble()
        }
    }

    private fun criarCartaoCredito(): CartaoCredito {
        val numero = editTextNumeroCartao.text.toString()
        val nome = editTextTitular.text.toString()
        val vencimento = editTextVencimento.text.toString()
        val cvv = editTextCVV.text.toString()
        return CartaoCredito(
            numeroCartao = numero,
            nomeTitular = nome,
            dataExpiracao = vencimento,
            codigoSeguranca = cvv,
            usuario = UsuarioLogado.usuario
        )
    }

    private fun configurarUsuario() {
        textViewNome.text = usuario.login
        textViewNomeCompleto.text = usuario.nomeCompleto
    }

    private fun configurarRadioGroup() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButtonCartaoCredito -> {
                    constraintLayoutCartaoCredito.visibility = VISIBLE
                }
                R.id.radioButtonSaldo -> {
                    constraintLayoutCartaoCredito.visibility = INVISIBLE
                }
            }

        }
    }
}