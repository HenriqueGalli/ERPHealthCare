package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.GetListaPacienteResult
import com.project.erphealthcare.databinding.ActivityListarPacientesBinding

class ListarPacientesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListarPacientesBinding
    private lateinit var adapter: ListaPacientesAdapter

    private val viewModel: ListarPacientesViewModel =
        ListarPacientesViewModelFactory().create(ListarPacientesViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarPacientesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("TOKEN")) {
            getListaPacientes(intent.getStringExtra("TOKEN"))
            setupObservers()
        }

    }

    private fun setupObservers() {
        viewModel.pacienteLiveData.observe(this) { res ->
            when (res) {
                is GetListaPacienteResult.Success -> setupList(res.pacientesLista ?: arrayListOf())
                is GetListaPacienteResult.ServerError -> setupList(arrayListOf())
            }
        }
    }

    private fun setupList(pacientesLista: ArrayList<Paciente>) {
        adapter = ListaPacientesAdapter(pacientesLista)
        binding.rvPacientes.adapter = adapter
        binding.rvPacientes.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
    }

    private fun getListaPacientes(token: String?) {
        if (token != null) {
            viewModel.getListaPacientes(token)
        }
    }
}