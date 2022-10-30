package com.amila.newsapp.ui.auth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.amila.newsapp.R
import com.amila.newsapp.models.User
import com.amila.newsapp.utils.DialogHelper
import com.amila.newsapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signin.*

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val TAG = "DBCheck"
    val viewModel : AuthViewModel by viewModels()
    var isUsernameAvailable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //Check the username in the users table before insert..
        edtUserName.addTextChangedListener { editable ->
            viewModel.getUserList(editable.toString()).observe(this, Observer { userList ->
                if (userList.isNotEmpty()){
                    edtUserName.error = getString(R.string.error_username_not_available)
                    isUsernameAvailable = false
                }else{
                    isUsernameAvailable = true
                }
            })
        }
    }

    private fun isValidated():Boolean{
        if (edtFirstName.text.toString().isEmpty()){
            edtFirstName.error = getString(R.string.error_first_name_empty)
            return false
        }
        if (edtLastName.text.toString().isEmpty()){
            edtLastName.error = getString(R.string.error_last_name_empty)
            return false
        }
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

    fun onClickSignUp(view: View) {
        if(isValidated() && isUsernameAvailable) {

            val user = User(
                edtUserName.text.toString(), edtFirstName.text.toString(),
                edtLastName.text.toString(), Utils.encryptString(edtPassword.text.toString()))
            try {
                viewModel.insertUser(user)
                Toast.makeText(this, getString(R.string.txt_user_saved_success),
                    Toast.LENGTH_SHORT).show()
                finish()
            }catch (ex: Exception){
                DialogHelper.showErrorDialog(this, getString(R.string.txt_error), getString(R.string.error_username_not_available))
            }
        }
    }
}