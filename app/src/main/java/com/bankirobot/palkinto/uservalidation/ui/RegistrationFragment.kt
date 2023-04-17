package com.bankirobot.palkinto.uservalidation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.utils.ConfiguredProgressBar
import com.bankirobot.palkinto.utils.UserData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_registration.*
import org.jetbrains.anko.toast
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class RegistrationFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var configuredProgressBar: ConfiguredProgressBar
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_registration, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("User")
        val layout = layoutInflater.inflate(
            R.layout.progress_dialog, requireView()
                .findViewById(R.id.custom_toast_container)
        )
        configuredProgressBar = ConfiguredProgressBar(requireContext(), layout)
        reg_button_back.setOnClickListener { requireActivity().onBackPressed() }
        reg_button_continue.setOnClickListener(runThread)
    }

    private val runThread = View.OnClickListener {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onUserValidation(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("RegistrationFragment: ", error.toException())
            }
        }
        val t1 = Thread { databaseReference.addValueEventListener(valueEventListener) }
        if (t1.isAlive) return@OnClickListener
        configuredProgressBar.start()
        t1.start()
    }


    private fun onUserValidation(dataSnapshot: DataSnapshot) {
        try {
            Thread.sleep(400)
            if (validateUserData()) {
                Log.w("RegistrationFragment", "validateUserData() is true")
                Thread.sleep(800)
                val firebaseEmails = ArrayList<String>()
                dataSnapshot.children.forEach {
                    val firebaseEmail = it.child("email").getValue(String::class.java) ?: return@forEach
                    firebaseEmails.add(firebaseEmail)
                }
                if (!firebaseEmails.contains(email)) {
                    Log.w("RegistrationFragment", "Success registration")
                    val userData = UserData(firstName, lastName, email, password)
                    databaseReference.push().setValue(userData)
                    viewModel.text.value = email
                    requireActivity().onBackPressed()
                } else {
                    requireActivity().toast(resources.getText(R.string.reg_user_busy))
                    Log.w("RegistrationFragment", "Unsuccessful registration")
                }
            } else {
                requireActivity().toast(resources.getText(R.string.reg_inc_validate))
                Log.w("RegistrationFragment", "validateUserData() is false")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            Log.w("RegistrationFragment", "configuredProgressBar::stop")
            handler.post(configuredProgressBar::stop)
        }
    }

    private fun validateUserData(): Boolean {
        val root =
            isFirstName && isLastName && isEmail && isEmailValidate && isPassword && isPasswordValidate
        if (root) return true
        else {
            handler.post {
                reg_errFirstName.text =
                    if (isFirstName) " " else resources.getString(R.string.reg_inc_person_names)
                reg_errLastName.text =
                    if (isLastName) " " else resources.getString(R.string.reg_inc_person_names)
                reg_errEmail.text =
                    if (isEmail) " " else resources.getString(R.string.reg_inc_email)
                reg_errEmailValidate.text =
                    if (isEmailValidate) " " else resources.getString(R.string.reg_inc_validate)
                reg_errPassword.text =
                    if (isPassword) " " else resources.getString(R.string.reg_inc_psw)
                reg_errPasswordValidate.text =
                    if (isPasswordValidate) " " else resources.getString(R.string.reg_inc_validate)
            }
            return false
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private val isFirstName: Boolean
        get() {
            firstName = reg_firstName.text.toString().toLowerCase(Locale.ROOT).trim()
            return firstName.length in 3..20
        }

    private val isLastName: Boolean
        get() {
            lastName = reg_lastName.text.toString().toLowerCase(Locale.ROOT).trim()
            return lastName.length in 3..20
        }

    private val isEmail: Boolean
        get() {
            email = reg_email.text.toString().toLowerCase(Locale.ROOT).trim()
            return email.contains("@gmail.com")
        }

    private val isEmailValidate: Boolean
        get() = this.email == reg_emailValidate.text.toString().toLowerCase(Locale.ROOT).trim()

    private val isPassword: Boolean
        get() {
            password = reg_password.text.toString()
            return password.length > 5 && isValidPassword(password)
        }

    private val isPasswordValidate: Boolean
        get() = password == reg_passwordValidate.text.toString()
}