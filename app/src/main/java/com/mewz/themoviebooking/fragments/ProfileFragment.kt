package com.mewz.themoviebooking.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.login.LoginActivity
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.FragmentProfileBinding
import com.mewz.themoviebooking.mvp.presenters.ProfilePresenter
import com.mewz.themoviebooking.mvp.presenters.impls.ProfilePresenterImpl
import com.mewz.themoviebooking.mvp.views.ProfileView

class ProfileFragment : Fragment(), ProfileView {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var mPresenter: ProfilePresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false)

        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListeners()

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(requireActivity())[ProfilePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            mPresenter.onTapLogout()
        }

    }

    private fun setUpAlertDialog() {
        val dialog = MaterialAlertDialogBuilder(requireActivity(), R.style.CustomAlertDialog)
            .setTitle("Logout !")
            .setMessage("Are you sure logout ?")
            .setNegativeButton("Cancel"){ dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("Accept"){dialog, which ->
                mTheMovieBookingModel.logout(
                    authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
                    onSuccess = {
                        mTheMovieBookingModel.deleteAllEntities()
                        Toast.makeText(requireActivity(), "${it.message}",Toast.LENGTH_SHORT).show()
                        startActivity(LoginActivity.newIntent(requireActivity()))
                        requireActivity().finish()
                    },
                    onFailure = { Toast.makeText(requireActivity(), it ,Toast.LENGTH_SHORT).show() }
                )

            }
        dialog.show()
    }

    override fun navigateToLoginScreen() {
        setUpAlertDialog()
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }


}