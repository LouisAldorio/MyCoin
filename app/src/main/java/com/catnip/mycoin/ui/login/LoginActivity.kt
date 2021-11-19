package com.catnip.mycoin.ui.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import com.catnip.mycoin.R
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.UserData
import com.catnip.mycoin.databinding.ActivityLoginBinding
import com.catnip.mycoin.ui.coinlist.CoinListActivity
import com.catnip.mycoin.ui.register.RegisterActivity
import com.catnip.mycoin.utils.StringUtils
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(),LoginContract.View{

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setOnClick()
        initViewModel()
    }

    override fun initView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initViewModel() {
        val dialog = Dialog(this)
        viewModel.getLoginResultLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(dialog, true)
                }
                is Resource.Success -> {
                    showLoading(dialog, false)
                    showToast(true, getString(R.string.text_login_success))
                    response.data?.let {
                        saveSessionLogin(it)
                    }
                }
                is Resource.Error -> {
                    showLoading(dialog, false)
                    val msg = response.message.orEmpty()
                    showToast(
                        false,
                        getString(R.string.text_login_failed,msg)
                    )
                }
            }
        })
    }

    override fun navigateToCoinList() {
        val intent = Intent(this, CoinListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun setOnClick() {
        binding.cvButtonAuth.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.loginUser(
                    AuthRequest(
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString()
                    )
                )
            }
        }
        binding.cvButtonRegisterNew.setOnClickListener {
            Log.d("testos", "setOnClick: masuk pak eko")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun saveSessionLogin(data: UserData) {
        viewModel.saveSession(data.token.orEmpty())
    }

    override fun showToast(isSuccess: Boolean, msg: String) {
        if (isSuccess) {
            FancyToast.makeText(
                this,
                msg,
                Toast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                true
            ).show()
            navigateToCoinList()
        } else {
            FancyToast.makeText(
                this,
                msg,
                Toast.LENGTH_SHORT,
                FancyToast.ERROR,
                true
            ).show()
        }
    }

    override fun showLoading(dialog: Dialog, isLoading: Boolean) {
        if (isLoading) {
            dialog.window?.setTitle(Window.FEATURE_NO_TITLE.toString())
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.loading_screen)
            dialog.setCancelable(false)
            dialog.show()
        } else dialog.cancel()
    }

    override fun checkFormValidation(): Boolean {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        var isFormValid = true
        when {
            email.isEmpty() -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_fill_email)
                )
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_check_email)
                )
            }
            pass.isEmpty() -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_fill_password)
                )
            }
        }
        return isFormValid
    }
}