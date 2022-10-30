package com.amila.newsapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.amila.newsapp.R
import com.amila.newsapp.ui.news.NewsActivity
import com.amila.newsapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signin.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    private fun isValidated():Boolean{
        if (edtUserName.text.toString().isEmpty()){
            edtUserName.error = getString(R.string.error_username_empty)
            return false
        }
        if (edtPassword.text.toString().isEmpty()){
            edtPassword.error = getString(R.string.error_password_empty)
            return false
        }
        return true
    }

    fun onClickLogin(view: View) {
        if(isValidated()) {
            viewModel.getSavedUser(
                edtUserName.text.toString(),
                Utils.encryptString(edtPassword.text.toString())).observe(
                this, Observer { userList ->
                    if (userList.isNotEmpty()){
                        startActivity(Intent(this, NewsActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, getString(R.string.error_user_incorrect),
                            Toast.LENGTH_SHORT).show()
                    }
            })
        }
    }

    fun onClickToRegister(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
}