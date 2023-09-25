package com.mewz.themoviebooking.activities.cinema

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.ActivityCinemaInfoBinding
import com.mewz.themoviebooking.mvp.presenters.CinemaInfoPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.CinemaInfoPresenterImpl
import com.mewz.themoviebooking.mvp.views.CinemaInfoView
import com.mewz.themoviebooking.utils.data.CinemaInfoFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.Stack

class CinemaInfoActivity : BaseActivity(), CinemaInfoView {

    private lateinit var binding: ActivityCinemaInfoBinding

    private lateinit var mPresenter: CinemaInfoPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private val safetyList = CinemaInfoFactory().safetyList
    private lateinit var stack: Stack<String>
    private var isPlayingVideo: Boolean = false
    private var isClickedFavourite: Boolean = false
    private var mCinemaId: Int? = 0

    companion object{

        const val EXTRA_CINEMA_ID = "EXTRA_CINEMA_ID"

        fun newIntent(context: Context, cinemaId: Int): Intent{
            val intent = Intent(context, CinemaInfoActivity::class.java)
            intent.putExtra(EXTRA_CINEMA_ID, cinemaId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCinemaInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCinemaId = intent?.getIntExtra(EXTRA_CINEMA_ID, 0) ?: 0
        Toast.makeText(this, "$mCinemaId", Toast.LENGTH_LONG).show()

        setUpPresenter()
        setUpListeners()
        setUpBindData()
    }



    private fun setUpPresenter() {
        mPresenter = getPresenter<CinemaInfoPresenterImpl, CinemaInfoView>()
    }

    private fun setUpBindData() {
        mCinemaId?.let { id ->
            mTheMovieBookingModel.getCinema(id)?.also {

                binding.tvCinemaInfoName.text = it.name
                binding.tvCinemaInfoLocation.text = it.address

                it.promoVdoUrl?.let { url ->
                    playCinemaInfoVideo(url)
                }

                it.facilities?.forEach { facility ->
                    binding.cgFacilities.addView(createDynamicChipForFacilities(facility.title, facility.img))
                }

                it.safety?.forEach{ safety ->
                    safetyList.add(safety)
                }
                setSafetyLinearLayout()

            }
        }
    }

    private fun setSafetyLinearLayout() {
        val safetyFactory = CinemaInfoFactory()
        stack = safetyFactory.pushToStack(safetyList)

        val row: Int = if(safetyList.size%2 == 1) {
            (safetyList.size/2)+1
        }else {
            safetyList.size/2
        }

        for(i in 0 until row) {
            binding.llSafety.addView(setTextViewStyle())
        }
    }

    private fun setTextViewStyle() : LinearLayout {
        val linearLayoutInner = LinearLayout(this)
        linearLayoutInner.orientation = LinearLayout.HORIZONTAL

        val innerLinearLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        innerLinearLayoutParams.setMargins(0,0,0,20)
        linearLayoutInner.layoutParams = innerLinearLayoutParams

        for(i in stack.indices){
            if(i == 2){
                stack.removeAt(0)
                stack.removeAt(0)
                break
            }
            val textView = AppCompatTextView(this)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0,0,15,0)
            textView.layoutParams =layoutParams
            textView.setPadding(20,12,20,12)
            textView.background = ContextCompat.getDrawable(this,R.drawable.background_safety_box)
            textView.text = stack[i]
            textView.textSize = 14f
            textView.setTextColor(Color.parseColor("#111111"))
            textView.gravity = Gravity.CENTER
            textView.typeface = ResourcesCompat.getFont(this,R.font.inter_medium)
            linearLayoutInner.addView(textView)
        }
        return linearLayoutInner
    }


    private fun createDynamicChipForFacilities(title: String?, img: String?): Chip {
        val chip = Chip(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        chip.layoutParams = layoutParams
        chip.gravity = Gravity.CENTER
        chip.textSize = 14.0f
        chip.typeface = ResourcesCompat.getFont(this, R.font.inter_medium)
        chip.text = title
        chip.setTextColor(Color.parseColor("#00FF6A"))
        chip.setChipBackgroundColorResource(R.color.background)

        lifecycleScope.launch {
            val bitmap = img?.let { downloadImage(it) }
            val drawable = BitmapDrawable(resources, bitmap)
            chip.chipIcon = drawable
        }
        return chip
    }

    private suspend fun downloadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun playCinemaInfoVideo(url: String) {
        setUpVideoView(url)
        binding.vvCinemaInfo.start()
        if (!isPlayingVideo){
            binding.vvCinemaInfo.resume()
            isPlayingVideo = true
        }else{
            binding.vvCinemaInfo.pause()
            isPlayingVideo = false
        }
    }

    private fun setUpVideoView(url: String) {
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.vvCinemaInfo)

        val videoUri = Uri.parse(url)
        binding.vvCinemaInfo.setMediaController(mediaController)
        binding.vvCinemaInfo.setVideoURI(videoUri)
        binding.vvCinemaInfo.requestFocus()
    }


    private fun setUpListeners() {
        binding.btnBackCinemaDetail.setOnClickListener {
            mPresenter.onTapBackScreen()
        }

        binding.btnFavourite.setOnClickListener {
            if (isClickedFavourite){
                binding.btnFavourite.setColorFilter(applicationContext.resources.getColor(android.R.color.transparent))
                isClickedFavourite = false
            }else{
                binding.btnFavourite.setColorFilter(applicationContext.resources.getColor(R.color.colorAccent))
                isClickedFavourite = true
            }

        }
    }

    override fun navigateToCinemaScreen() {
        onBackPressed()
    }
}