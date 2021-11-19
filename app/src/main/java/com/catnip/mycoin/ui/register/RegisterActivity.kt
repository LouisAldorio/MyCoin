package com.catnip.mycoin.ui.register

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import com.catnip.mycoin.R
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.databinding.ActivityLoginBinding
import com.catnip.mycoin.databinding.ActivityRegisterBinding
import com.catnip.mycoin.ui.login.LoginViewModel
import com.catnip.mycoin.utils.StringUtils
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(),RegisterContract.View {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setClickListener()
        initViewModel()
    }

    override fun initView() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etUsername.setText("")
        binding.etEmail.setText("")
        binding.etPassword.setText("")
    }

    override fun initViewModel() {
        val dialog = Dialog(this)
        viewModel.getRegisterResponseLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(dialog, true)
                }
                is Resource.Success -> {
                    showLoading(dialog, false)
                    showToast(true, getString(R.string.text_register_success))
                    initView()
                }
                is Resource.Error -> {
                    showLoading(dialog, false)
                    val msg = response.message.toString()
                    when {
                        "email_1 dup key" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_register_failed,
                                getString(R.string.text_email_is_exist)
                            )
                        )
                        "username_1 dup key" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_register_failed,
                                getString(R.string.text_name_is_exist)
                            )
                        )
                        "alphanumeric characters" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_register_failed,
                                getString(R.string.text_name_should_only_alphanumeric)
                            )
                        )
                    }
                }
            }
        })
    }

    override fun setClickListener() {
        binding.btnRegister.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.registerUser(
                    AuthRequest(
                        binding.etUsername.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                )
            }
        }
    }

    override fun checkFormValidation(): Boolean {
        val email = binding.etEmail.text.toString()
        val name = binding.etUsername.text.toString()
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
            name.isEmpty() -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_fill_name)
                )
            }
            name.count() < 6 -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_name_shorter_than_minimum)
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

    override fun showLoading(dialog: Dialog, isLoading: Boolean) {
        if (isLoading) {
            dialog.window?.setTitle(Window.FEATURE_NO_TITLE.toString())
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.loading_screen)
            dialog.setCancelable(false)
            dialog.show()
        } else dialog.cancel()
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
        } else {
            FancyToast.makeText(
                this,
                msg,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                true
            ).show()
        }
    }
}