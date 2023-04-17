package com.bankirobot.palkinto.welcome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bankirobot.palkinto.MainActivity
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.menu.MenuController
import com.bankirobot.palkinto.uservalidation.UserController
import com.bankirobot.palkinto.utils.SaveUserData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import java.lang.Exception

class WelcomeFragment : Fragment() {

    private var progressStatus = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_welcome, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val saveUserData = SaveUserData(requireContext())
        if (MainActivity.NETWORK_VALIDATION) {
            databaseReference = FirebaseDatabase.getInstance().getReference("User")
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onUserValidation(snapshot, saveUserData)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("WelcomeFragment: ", error.toException())
                }
            }
            val thread = Thread { databaseReference.addValueEventListener(valueEventListener) }
            thread.start()
        } else {
            val thread = Thread { onUserValidationLostNetwork(saveUserData) }
            thread.start()
//            onUserValidationLostNetwork(saveUserData)
        }
    }
    private fun onUserValidationLostNetwork(saveUserData: SaveUserData) {
        try {
            setOneProgressStatus()
            when {
                saveUserData.firstLaunch -> {
                    setOneProgressStatus()
                    findNavController().navigate(R.id.action_welcome_to_first_steps)
                    Log.w("WelcomeFragment", "when -> saveUserData.firstLaunch")
                }
                saveUserData.userID.isNotEmpty() -> {
                    setOneProgressStatus()
                    startActivity(Intent(requireContext(), MenuController::class.java))
                    Log.w("WelcomeFragment: ", "when -> saveUserData.userID.isNotEmpty()")
                }
                else -> {
                    setOneProgressStatus()
                    startActivity(Intent(requireContext(), UserController::class.java))
                    Log.w("WelcomeFragment: ", "when -> else")
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            while (progressStatus < 100) {
                progressStatus++
                welcome_progressBar.progress = progressStatus
                Thread.sleep(20)
            }
            requireActivity().finish()
        }
    }
    private fun onUserValidation(dataSnapshot: DataSnapshot, saveUserData: SaveUserData) {
        try {
            if (dataSnapshot.children.none()) {
                Log.w("WelcomeFragment: ", "dataSnapshot.children.none()")
                startActivity(Intent(requireContext(), UserController::class.java))
                requireActivity().finish()
            } else {
                val firebaseUserIDs = ArrayList<String>()
                dataSnapshot.children.forEach {
                    Thread.sleep(40)
                    setOneProgressStatus()
                    handler.post { welcome_progressBar.progress = progressStatus }
                    firebaseUserIDs.add(it.key!!)
                }
                setOneProgressStatus()
                if (saveUserData.firstLaunch) {
                    setOneProgressStatus()
                    findNavController().navigate(R.id.action_welcome_to_first_steps)
                    Log.w("WelcomeFragment: ", "saveUserData.firstLaunch")
                } else if (saveUserData.userID.isNotEmpty()) {
                    setOneProgressStatus()
                    if (firebaseUserIDs.contains(saveUserData.userID)) {
                        setOneProgressStatus()
                        Log.w("WelcomeFragment: ", "saveUserData.userID == userID")
                        startActivity(Intent(requireContext(), MenuController::class.java))
                        requireActivity().finish()
                    } else {
                        setOneProgressStatus()
                        startActivity(Intent(requireContext(), UserController::class.java))
                        Log.w("WelcomeFragment: ", "else saveUserData.userID == userID")
                        requireActivity().finish()
                    }
                } else {
                    setOneProgressStatus()
                    startActivity(Intent(requireContext(), UserController::class.java))
                    Log.w("WelcomeFragment: ", "else saveUserData.userID.isNotEmpty()")
                    requireActivity().finish()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            while (progressStatus < 100) {
                progressStatus++
                handler.post { welcome_progressBar.progress = progressStatus }
                Thread.sleep(20)
            }
        }
    }

    private fun setOneProgressStatus() {
        if (progressStatus < 100) progressStatus++
    }
}