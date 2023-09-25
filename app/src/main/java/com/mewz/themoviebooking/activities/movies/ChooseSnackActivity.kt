package com.mewz.themoviebooking.activities.movies

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.adapters.SnackAdapter
import com.mewz.themoviebooking.adapters.SnackBottomSheetAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutSnack
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutSnackList
import com.mewz.themoviebooking.data.vos.snack.SnackCategoryVO
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.databinding.ActivityChooseSnackBinding
import com.mewz.themoviebooking.mvp.presenters.ChooseSnackPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.ChooseSnackPresenterImpl
import com.mewz.themoviebooking.mvp.views.ChooseSnackView
import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.utils.data.SeatData
import com.mewz.themoviebooking.utils.data.TicketData

@RequiresApi(Build.VERSION_CODES.O)
class ChooseSnackActivity : BaseActivity(), ChooseSnackView {

    private lateinit var binding: ActivityChooseSnackBinding

    private lateinit var mAdapter: SnackAdapter
    private lateinit var mSnackBottomSheetAdapter: SnackBottomSheetAdapter

    private lateinit var mPresenter: ChooseSnackPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var mMovieId: String? = null
    private var mMovieName: String? = null
    private var mCinemaInfo: CinemaData? = null
    private var mSeatInfo: SeatData? = null

    private var mSnackCategoryAllList: MutableList<String?>? = null
    private var mSnackCategoryList: List<SnackCategoryVO>? = null
    private var mSnackList: MutableLiveData<MutableList<SnackVO>>? = MutableLiveData<MutableList<SnackVO>>()
    private lateinit var mSnackListForBottomSheet: MutableList<SnackVO>

    companion object{

        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        const val EXTRA_MOVIE_NAME = "EXTRA_MOVIE_NAME"
        const val EXTRA_CINEMA_INFO = "EXTRA_CINEMA_INFO"
        const val EXTRA_SEAT_INFO = "EXTRA_SEAT_INFO"

        fun newIntent(context: Context, movieId: String, movieName: String, cinemaInfo: CinemaData?, seatInfo: SeatData?) : Intent{
            val intent = Intent(context, ChooseSnackActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            intent.putExtra(EXTRA_MOVIE_NAME, movieName)
            intent.putExtra(EXTRA_CINEMA_INFO, cinemaInfo)
            intent.putExtra(EXTRA_SEAT_INFO, seatInfo)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseSnackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMovieId = intent?.getStringExtra(EXTRA_MOVIE_ID) ?: ""
        mMovieName = intent?.getStringExtra(EXTRA_MOVIE_NAME) ?: ""
        mCinemaInfo = intent?.getSerializableExtra(EXTRA_CINEMA_INFO) as CinemaData?
        mSeatInfo = intent?.getSerializableExtra(EXTRA_SEAT_INFO) as SeatData?

        // Toast.makeText(this, "$mMovieId $mMovieName $mCinemaInfo $mSeatInfo", Toast.LENGTH_LONG).show()
        Log.d("ChooseSnack", "data.{$mMovieId $mMovieName $mCinemaInfo $mSeatInfo}")

        mSnackCategoryAllList = mutableListOf("All")
        mSnackCategoryList = mutableListOf()
        mSnackListForBottomSheet = mutableListOf()

        setUpPresenter()

        setUpRecyclerView()
        setUpListeners()

        setUpSnackTabLayout()
        setUpRequestData()
        // setUpHasItemRecyclerView()

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<ChooseSnackPresenterImpl, ChooseSnackView>()
    }

    private fun setUpRequestData() {
        mTheMovieBookingModel.getSnackCategory(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            onSuccess = {
                mSnackCategoryList = it as MutableList<SnackCategoryVO>
                setUpTabLayout(it)
            },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

    private fun setUpTabLayout(snackCategoryList: List<SnackCategoryVO>) {
        for (categoryList in snackCategoryList) {
            mSnackCategoryAllList?.add(categoryList.title)
        }
        mSnackCategoryAllList?.forEach { snackCategoryTitle ->
            binding.tabLayoutSnack.newTab().apply {
                text = snackCategoryTitle
                binding.tabLayoutSnack.addTab(this)
            }
        }
    }

    private fun setUpRequestSnackData(categoryId: String) {
        mTheMovieBookingModel.getSnack(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            categoryId = categoryId,
            onSuccess = {
                mSnackList?.value = it as MutableList<SnackVO>
                mAdapter.setNewData(it)
            },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

    private fun setUpSnackTabLayout() {
        binding.tabLayoutSnack.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    setUpRequestSnackData("")
                }else{
                    mSnackCategoryList?.get(tab?.position?.minus(1) ?: 0)?.id?.let {
                        setUpRequestSnackData(it.toString())
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setUpListeners() {

        binding.btnBackChooseSnackScreen.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.btnUpArrow.setOnClickListener {
            mPresenter.onTapBottomSheet()
        }

        binding.btnNextScreen.setOnClickListener {
            mPresenter.onTapNextScreen()
        }

        binding.btnSkip.setOnClickListener {
            mPresenter.onTapNextScreen()
        }
    }

    private fun createTicket(): TicketData{
        return TicketData(mMovieId, mMovieName, mCinemaInfo, mSeatInfo, getSnackTotalPrice(), mSnackList?.value)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpSnackQuantityAndPrice() {
        binding.tvTotalSnackPRice.text = "${getSnackTotalPrice()}Ks"

        var snackTotalQuantity = 0
        mSnackList?.value?.forEach {
            snackTotalQuantity += it.quantity
        }
        binding.tvSnackCount.text = snackTotalQuantity.toString()
    }

    private fun getSnackTotalPrice(): String {
        var snackTotalPrice = 0
        mSnackList?.value?.forEach {
            snackTotalPrice += it.price?.times(it.quantity) ?: 0
        }
        return snackTotalPrice.toString()
    }

    private fun getCheckoutSnackList(snackList: MutableList<SnackVO>): CheckoutSnackList{
        val checkoutSnackList = mutableListOf<CheckoutSnack>()
        for (snack in snackList){
            checkoutSnackList.add(CheckoutSnack(snack.id, snack.quantity))
        }
        return CheckoutSnackList(checkoutSnackList)
    }


    @SuppressLint("SetTextI18n")
    private fun setUpBottomSheet() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_snack_detail)
        dialog.setCancelable(true)

        setUpSnackBottomSheetRecyclerView(dialog)

        dialog.findViewById<AppCompatImageView>(R.id.btnDownArrow)?.setOnClickListener {
            dialog.dismiss()
        }

        var snackTotalQuantity = 0
        mSnackList?.value?.forEach {
            snackTotalQuantity += it.quantity
        }

        dialog.findViewById<AppCompatTextView>(R.id.tvSnackCount)?.text = snackTotalQuantity.toString()
        dialog.findViewById<AppCompatTextView>(R.id.tvTotalSnackPRice)?.text = "${getSnackTotalPrice()  }Ks"

        dialog.findViewById<AppCompatImageView>(R.id.btnNextScreen)?.setOnClickListener {
            mTheMovieBookingModel.getSnack(
                authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
                categoryId = "",
                onSuccess = { snack ->
                    mSnackList?.value = snack as MutableList
                    val snackList = getCheckoutSnackList(mSnackList?.value ?: mutableListOf())
                    val ticket = TicketInformation(null, null, null, null, null, null)
                    startActivity(CheckoutActivity.newIntent(this, "Checkout", createTicket(), snackList, ticket))
                },
                onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
            )
            dialog.dismiss()
        }

        dialog.show()

    }

    @SuppressLint("CutPasteId")
    private fun setUpSnackBottomSheetRecyclerView(dialog: BottomSheetDialog) {
        mSnackBottomSheetAdapter = SnackBottomSheetAdapter()
        dialog.findViewById<RecyclerView>(R.id.rvBottomSheetSnackList)?.adapter = mSnackBottomSheetAdapter
        dialog.findViewById<RecyclerView>(R.id.rvBottomSheetSnackList)?.layoutManager =
            LinearLayoutManager(this)
        mSnackBottomSheetAdapter.setNewData(setUpSnackList())
    }

    private fun setUpSnackList(): MutableList<SnackVO> {
        mTheMovieBookingModel.getSnack(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            categoryId = "",
            onSuccess = { snack ->
                mSnackList?.value = snack as MutableList
                for (snack in mSnackList?.value ?: mutableListOf()){
                    if (snack.quantity > 0){
                        mSnackListForBottomSheet.add(snack)
                    }
                }
            },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
        return mSnackListForBottomSheet
    }


    private fun setUpRecyclerView() {
        mAdapter = SnackAdapter(mPresenter)
        binding.rvSnackList.adapter = mAdapter
        binding.rvSnackList.layoutManager =
            GridLayoutManager(this, 2 , GridLayoutManager.VERTICAL, false)
    }

    override fun showSnackPlus(snackId: Int) {
        mSnackList?.observe(this){snackList ->
            snackList.forEach {
                if (it.id == snackId){
                    it.quantity++
                }
            }
            mAdapter.setNewData(snackList)
        }
        setUpSnackQuantityAndPrice()
    }


    override fun showSnackMinus(snackId: Int) {
        mSnackList?.observe(this){snackList ->
            snackList.forEach {
                if (it.id == snackId){
                    it.quantity--
                    if (it.quantity <= 0) {
                        it.quantity = 0
                    }
                }
            }
            mAdapter.setNewData(snackList)
        }
        setUpSnackQuantityAndPrice()
    }

    override fun showBottomSheetSnack() {
         setUpBottomSheet()
    }

    override fun navigateToBackScreen() {
        super.onBackPressed()
    }

    override fun navigateToCheckoutScreen() {
        mTheMovieBookingModel.getSnack(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            categoryId = "",
            onSuccess = { snack ->
                mSnackList?.value = snack as MutableList
                val snackList = getCheckoutSnackList(mSnackList?.value ?: mutableListOf())
                val ticket = TicketInformation(null, null, null, null, null, null)
                startActivity(CheckoutActivity.newIntent(this, "Checkout", createTicket(), snackList, ticket))
            },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )

    }
}