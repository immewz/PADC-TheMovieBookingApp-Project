package com.mewz.mymoviebookingapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.databinding.FragmentProfileBinding
import com.mewz.mymoviebookingapp.ui.activities.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false)

        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonLogoutListener()
    }

    private fun setButtonLogoutListener() {
        binding.llLogout.setOnClickListener {

            val dialog = MaterialAlertDialogBuilder(requireActivity(),R.style.RoundedAlertDialog)
                .setTitle("Log Out")
                .setMessage("Are you sure to log out ?")
                .setCancelable(false)
                .setPositiveButton("Yes"){dialog, which ->
                    mMovieBookingModel.logout(
                        authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
                        onSuccess = {
                            mMovieBookingModel.deleteAll()
                            val intent = Intent(requireActivity(),LoginActivity::class.java)
                            startActivity(intent)
                        },
                        onFailure = {
                            showErrorMessage(it)
                        }
                    )
                }
                .setNegativeButton("Cancel"){dialog,which ->
                    dialog?.dismiss()
                }
                .create()
            dialog.show()
        }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(requireActivity(),error, Toast.LENGTH_LONG).show()
    }


}