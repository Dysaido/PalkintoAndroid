package com.bankirobot.palkinto.uservalidation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.menu.MenuController
import com.bankirobot.palkinto.utils.SaveUserData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("User")
        viewModel.text.observe(viewLifecycleOwner, { login_email.setText(it) })
        login_button_registration.setOnClickListener { findNavController().navigate(R.id.action_login_to_registration) }
        login_button_access.setOnClickListener(runThread)
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
        login_error.text = ""
        val t1 = Thread { databaseReference.addValueEventListener(valueEventListener) }
        if (t1.isAlive) return@OnClickListener
        login_progress.visibility = View.VISIBLE
        t1.start()
    }

    private fun onUserValidation(dataSnapshot: DataSnapshot) {
        try {
            Thread.sleep(600)
            val email = login_email.text.toString()
            val password = login_password.text.toString()
            dataSnapshot.children.forEach {
                val firebaseEmail = it.child("email").getValue(String::class.java) ?: return@forEach
                if (email == firebaseEmail) {
                    Thread.sleep(20)
                    val passwordFirebase = it.child("password").getValue(String::class.java)!!
                    if (passwordFirebase == password) {
                        val firstNameFirebase = it.child("firstName").getValue(String::class.java)!!
                        val lastNameFirebase = it.child("lastName").getValue(String::class.java)!!
                        val userID = it.key!!
                        val saveUserData = SaveUserData(requireContext())
                        saveUserData.firstName = firstNameFirebase
                        saveUserData.lastName = lastNameFirebase
                        saveUserData.email = email
                        saveUserData.userID = userID
                        startActivity(Intent(requireActivity(), MenuController::class.java))
                        requireActivity().finish()
                        return@onUserValidation
                    }
                }
            }
            handler.post { login_error.text = getString(R.string.login_inc_user_or_psw) }
        } catch (e: Exception) {
            handler.post { login_error.setText(R.string.global_error_connection) }
        } finally {
            handler.post { login_progress.visibility = View.INVISIBLE }
        }
    }

}

class LoginViewModel : ViewModel() {
    val text = MutableLiveData<String>()
}