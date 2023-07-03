package com.mewz.mymoviebookingapp.ui.activities.cinema

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
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.databinding.ActivityCinemaDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class CinemaDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCinemaDetailBinding
    private var mCinemaId: Int? = null

    private var isVideoPlaying: Boolean = false

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        private const val EXTRA_CINEMA_ID = "EXTRA_CINEMA_ID"
        fun newIntent(context: Context, cinemaId: Int): Intent{
            val intent = Intent(context,CinemaDetailActivity::class.java)
            intent.putExtra(EXTRA_CINEMA_ID, cinemaId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCinemaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCinemaId = intent?.getIntExtra(EXTRA_CINEMA_ID,0)

        setUpListeners()
        bindCinemaInfoData()
    }

    private fun bindCinemaInfoData() {
        mCinemaId?.let {
            mMovieBookingModel.getCinemaInfo(it).let {cinema ->
                binding.tvCinema.text = cinema?.name
                binding.tvCinemaLocation.text = cinema?.address
                cinema?.promoVdoUrl?.let {
                    playCinemaVideo(it)
                }
                cinema?.facilities?.forEach{faciliies ->
                    binding.chipFacilities.addView(createFacilitiesChip(applicationContext, faciliies.img.toString(), faciliies.title))
                }
                cinema?.safety?.forEach{safety ->
                    binding.chipSafety.addView(createSafetyChip(applicationContext, safety))
                }

            }
        }
    }

    private fun createFacilitiesChip(applicationContext: Context?, imgUrl: String?, title: String?): View? {
        val chip = Chip(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 15, 0)
        chip.layoutParams = layoutParams
        chip.gravity = Gravity.CENTER
        chip.textSize = 14.0f
        chip.typeface = ResourcesCompat.getFont(this, R.font.intermedium)
        chip.text = title
        chip.setTextColor(Color.parseColor("#00FF6A"))
        chip.setChipBackgroundColorResource(R.color.colorPrimary)

        lifecycleScope.launch {
            val bitmap = imgUrl?.let { downloadImage(it) }
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

    private fun createSafetyChip(applicationContext: Context?, safety: String): View? {
        return Chip(this).apply {
            text = safety
            setChipBackgroundColorResource(R.color.green)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }


    private fun playCinemaVideo(url: String) {
        setUpVideoView(url)
        if (!isVideoPlaying){
            binding.vvVideoCinemaInfo.resume()
            isVideoPlaying = true
        }else{
            binding.vvVideoCinemaInfo.pause()
            isVideoPlaying = false
        }
    }

    private fun setUpVideoView(url: String) {
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.vvVideoCinemaInfo)

        val videoUri = Uri.parse(url)
        binding.vvVideoCinemaInfo.setMediaController(mediaController)
        binding.vvVideoCinemaInfo.setVideoURI(videoUri)
        binding.vvVideoCinemaInfo.requestFocus()
    }

    private fun setUpListeners() {
        binding.btnBackCinemaDetailView.setOnClickListener {
            super.onBackPressed()
        }
    }
}