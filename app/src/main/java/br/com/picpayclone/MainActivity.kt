package br.com.picpayclone

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.picpayclone.databinding.ActivityMainBinding
import br.com.picpayclone.ui.componente.ComponenteViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val controlador by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    private val componenteViewModel: ComponenteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        componenteViewModel._componentes.observe(this, Observer {
            it?.let { temComponentes ->
                if (temComponentes.bottomNavigation){
                    navView.visibility = VISIBLE
                }else {
                    navView.visibility = GONE
                }
            }
        })
        navView.setupWithNavController(controlador)
    }
}