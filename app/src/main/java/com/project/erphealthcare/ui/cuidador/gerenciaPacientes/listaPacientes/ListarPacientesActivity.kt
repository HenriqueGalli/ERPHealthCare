package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.AssociateCaregiverUserResult
import com.project.erphealthcare.data.result.GetListaPacienteResult
import com.project.erphealthcare.databinding.ActivityListarPacientesBinding

class ListarPacientesActivity : AppCompatActivity(), OnRemovePaciente {

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
        viewModel.associarLiveData.observe(this) { res ->
            when (res) {
                is AssociateCaregiverUserResult.Success -> updateList()
                is AssociateCaregiverUserResult.ServerError -> deleteError()
                else -> deleteError()
            }
        }
    }

    private fun updateList() {
        intent.getStringExtra("TOKEN")?.let { viewModel.getListaPacientes(it) }
    }

    private fun deleteError() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Ocorreu um erro ao remover o paciente")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, id ->

            }
        val alert = builder.create()
        alert.show()
    }

    private fun setupList(pacientesLista: ArrayList<Paciente>) {
        adapter = if (intent.hasExtra("EDITAR_PACIENTE"))
            ListaPacientesAdapter(pacientesLista, true, this, intent.getStringExtra("TOKEN")?:"")
        else
            ListaPacientesAdapter(pacientesLista, false, this, intent.getStringExtra("TOKEN")?:"")
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

    override fun onClick(email: String, cpf: String) {
        viewModel.deletarAssociacaoPaciente(email, cpf)
    }
}