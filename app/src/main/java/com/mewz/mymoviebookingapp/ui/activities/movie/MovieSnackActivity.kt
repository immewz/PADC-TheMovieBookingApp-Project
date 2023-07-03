package com.mewz.mymoviebookingapp.ui.activities.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutSnack
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutSnackList
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackCategoryVO
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackVO
import com.mewz.mymoviebookingapp.databinding.ActivityMovieSnackBinding
import com.mewz.mymoviebookingapp.ui.adapters.movie.BottomSheetDialogFoodAdapter
import com.mewz.mymoviebookingapp.ui.adapters.movie.MovieSnackAdapter
import com.mewz.mymoviebookingapp.ui.delgates.SnackViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.utils.CinemaData
import com.mewz.mymoviebookingapp.ui.utils.MovieData
import com.mewz.mymoviebookingapp.ui.utils.SeatData
import com.mewz.mymoviebookingapp.ui.utils.TicketData

class MovieSnackActivity : AppCompatActivity(), SnackViewHolderDelegate, java.io.Serializable {

    private lateinit var binding: ActivityMovieSnackBinding

    private lateinit var mMovieSnackAdapter: MovieSnackAdapter
    private lateinit var mBottomSheetDialogFoodAdapter: BottomSheetDialogFoodAdapter

    private var mSnackCategoryTitleList: MutableList<String?>? = null
    private var mSnackCategoryList: MutableList<SnackCategoryVO>? = null
    private var mSnackList: MutableLiveData<MutableList<SnackVO>>? = MutableLiveData<MutableList<SnackVO>>()

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    private var mMovieInfo: MovieData? = null
    private var mCinemaInfo: CinemaData? = null
    private var mSeatInfo: SeatData? = null
    private var mTicketInfo: TicketData? = null

    private var mCheckoutSnackList: CheckoutSnackList? = null

    companion object{
        private const val EXTRA_MOVIE_INFO = "EXTRA_MOVIE_INFO"
        private const val EXTRA_CINEMA_INFO = "EXTRA_CINEMA_INFO"
        private const val EXTRA_SEAT_INFO = "EXTRA_SEAT_INFO"
        fun newIntent(context: Context, movieInfo: MovieData?, cinemaInfo: CinemaData?, seatInfo: SeatData?): Intent{
            val intent = Intent(context,MovieSnackActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_INFO,movieInfo)
            intent.putExtra(EXTRA_CINEMA_INFO,cinemaInfo)
            intent.putExtra(EXTRA_SEAT_INFO,seatInfo)
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieSnackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMovieInfo = intent?.getSerializableExtra(EXTRA_MOVIE_INFO) as MovieData?
        mCinemaInfo = intent?.getSerializableExtra(EXTRA_CINEMA_INFO) as CinemaData?
        mSeatInfo = intent?.getSerializableExtra(EXTRA_SEAT_INFO) as SeatData?
//        Log.d("CinemaSnack","dataInfo.${mCinemaInfo},${mMovieInfo},${mSeatInfo}")

        mSnackCategoryTitleList = mutableListOf("All")
        mSnackCategoryList = mutableListOf()

        setUpButtonListener()
        setUpSnackRecyclerView()
        requestSnackData()
        setUpSnackTabLayoutListener()

    }


    private fun requestSnackData() {
        mMovieBookingModel.getSnackCategory(
            authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
            onSuccess = {
                mSnackCategoryList = it as MutableList<SnackCategoryVO>
                setUpSnackTabLayout(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }


    private fun setUpSnackTabLayout(snackCategoryList: List<SnackCategoryVO>) { // tabLayout category
        for (categoryList in snackCategoryList) {
            mSnackCategoryTitleList?.add(categoryList.title)
        }

        mSnackCategoryTitleList?.forEach { snackCategoryTitle ->
            binding.tabLayoutSnack.newTab().apply {
                text = snackCategoryTitle
                binding.tabLayoutSnack.addTab(this)
            }
        }

    }

    private fun requestSnackListData(categoryId: String){
        mMovieBookingModel.getSnackByCategoryId(
            authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
            categoryId = categoryId,
            onSuccess = {
                mSnackList?.value = it as MutableList<SnackVO>
                mMovieSnackAdapter.bindSnackListData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }

    private fun setUpSnackTabLayoutListener() { // tabLayout category position
        binding.tabLayoutSnack.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    requestSnackListData("")
                }else{
                    mSnackCategoryList?.get(tab?.position?.minus(1) ?: 0)?.id?.let { categoryId ->
                        requestSnackListData(categoryId.toString())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

        private fun checkoutActivity(){
        mMovieBookingModel.getSnackByCategoryId(
            authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
            categoryId = "",
            onSuccess = {snack ->
                mSnackList?.value = snack as MutableList
                mCheckoutSnackList = getCheckoutSnackList(mSnackList?.value ?: mutableListOf())
                mTicketInfo = TicketData(mMovieInfo,mCinemaInfo,mSeatInfo,getSnackTotalPrice(),mSnackList?.value)
                startActivity(MovieCheckOutActivity.newIntent(this,mTicketInfo,mCheckoutSnackList))
                finish()
//                Log.d("MovieSnack","${mTicketInfo},${mCheckoutSnackList}")
            }, onFailure = {
                showErrorMessage(it)
            }
        )
    }

    private fun getCheckoutSnackList(snackList: MutableList<SnackVO>): CheckoutSnackList{
        val checkoutSnackList = mutableListOf<CheckoutSnack>()
        for (snack in snackList) {
            checkoutSnackList.add(CheckoutSnack(snack.id, snack.quantity))
        }
        return CheckoutSnackList(checkoutSnackList)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpSnackRecyclerView() {
        mMovieSnackAdapter = MovieSnackAdapter(this)
        binding.rvSnackList.adapter = mMovieSnackAdapter
        binding.rvSnackList.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        mMovieSnackAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setUpButtonListener() {
        binding.btnOrderFood.setOnClickListener {
            checkoutActivity()
        }

        setUpBottomSheetDialog()
    }

    @SuppressLint("NotifyDataSetChanged", "CutPasteId")
    @RequiresApi(Build.VERSION_CODES.P)
    private fun setUpBottomSheetDialog() {
        binding.ivFoodie.setOnClickListener {

            val bottomDialog = BottomSheetDialog(this)
            val dialogView = layoutInflater.inflate(R.layout.layout_bottom_sheet_dialog_food, null, false)
            bottomDialog.setContentView(dialogView)
            bottomDialog.setCancelable(true)

            mBottomSheetDialogFoodAdapter = BottomSheetDialogFoodAdapter()
            bottomDialog.requireViewById<RecyclerView>(R.id.rvFoodBottomSheetDialogMoviesFood).adapter = mBottomSheetDialogFoodAdapter
            bottomDialog.requireViewById<RecyclerView>(R.id.rvFoodBottomSheetDialogMoviesFood).layoutManager =
                LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            bottomDialog.requireViewById<RecyclerView>(R.id.rvFoodBottomSheetDialogMoviesFood).setHasFixedSize(true)
            mBottomSheetDialogFoodAdapter.notifyDataSetChanged()

            bottomDialog.show()

            bottomDialog.findViewById<AppCompatImageView>(R.id.ivFoodie)?.setOnClickListener {
                bottomDialog.dismiss()
            }
        }
    }

    private fun setUpSnackPriceAndCount() { // price and count
        binding.tvFoodPrice.text = getSnackTotalPrice().toString()

        var snackTotalCount = 0
        mSnackList?.value?.forEach {
            snackTotalCount += it.quantity
        }
//        binding.tvFoodPrice.text = snackTotalCount.toString()
    }

    private fun getSnackTotalPrice(): Long { // total price
        var snackTotalPrice = 0L
        mSnackList?.value?.forEach {
            snackTotalPrice += it.price?.times(it.quantity) ?: 0
        }
        return snackTotalPrice
    }

    override fun onTapFoodCountPlus(snackId: Int) { // btn Add
        mSnackList?.observe(this){snackList ->
            snackList.forEach {
                if (it.id == snackId){
                    it.quantity++
                }
            }
            mMovieSnackAdapter.bindSnackListData(snackList)
        }
        setUpSnackPriceAndCount()
    }

    override fun onTapFoodCountMinus(snackId: Int) { // btn Sub
        mSnackList?.observe(this){snackList ->
            snackList.forEach {
                if (it.id == snackId){
                    it.quantity--
                    if (it.quantity <= 0){
                        it.quantity = 0
                    }
                }
            }
            mMovieSnackAdapter.bindSnackListData(snackList)
        }
        setUpSnackPriceAndCount()
    }


    private fun showErrorMessage(error: String) {
        Toast.makeText(this,error, Toast.LENGTH_LONG).show()
    }
}

