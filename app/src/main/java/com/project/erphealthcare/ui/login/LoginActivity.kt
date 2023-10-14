package com.project.erphealthcare.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.model.LoginResponse
import com.project.erphealthcare.data.result.LoginResult
import com.project.erphealthcare.databinding.ActivityLoginBinding
import com.project.erphealthcare.ui.cuidador.cadastro.CreateCuidadorActivity
import com.project.erphealthcare.ui.cuidador.home.HomeCuidadorActivity
import com.project.erphealthcare.ui.paciente.cadastro.CadastroPacienteActivity
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            login.isEnabled = loginState.isDataValid

//            if (loginState.usernameError != null) {
//                username.error = getString(loginState.usernameError)
//            }
//            if (loginState.passwordError != null) {
//                password.error = getString(loginState.passwordError)
//            }
        })

        loginViewModel.loginLiveData.observe(this) { loginResult ->

            when (loginResult) {
                is LoginResult.Success -> successLogin(loginResult.loginResponse)
                is LoginResult.ApiError -> showLoginFailed("Usuário ou senha inválidos")
                else -> showLoginFailed("Ocorreu um erro ao realizar o login, tente novamente mais tarde")
            }

            loading.visibility = View.GONE
        }

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.toString(),
                password.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
                //binding.loginErro?.visibility = View.GONE
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        }

        binding.cadastroPaciente?.setOnClickListener {
            val intent = Intent(this, CadastroPacienteActivity::class.java)
            startActivity(intent)
        }

        binding.cadastroProfissional?.setOnClickListener {
            val intent = Intent(this, CreateCuidadorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun successLogin(model: LoginResponse) {
        if (model.cuidador) {
            val intent = Intent(this, HomeCuidadorActivity::class.java)
            intent.putExtra("TOKEN", model.apiKey)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomePacienteActivity::class.java)
            intent.putExtra("TOKEN", model.apiKey)
            startActivity(intent)
        }


    }

    private fun showLoginFailed(@SuppressLint("SupportAnnotationUsage") @StringRes errorString: String) {
        //Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
        binding.loginErro?.visibility = View.VISIBLE
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}