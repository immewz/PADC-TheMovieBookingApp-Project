package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.utils.DateCard
import com.mewz.mymoviebookingapp.ui.viewholders.movie.MovieDateCardViewHolder

class MovieDateCardAdapter(
    private val dateListDateCard: MutableList<String>,
    private val delegate: CinemaDetailViewHolderDelegate
):RecyclerView.Adapter<MovieDateCardViewHolder>() {

    private lateinit var mDateList: MutableList<DateCard>
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDateCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie_data_card,parent,false)
        return MovieDateCardViewHolder(view.rootView,delegate)
    }

    override fun getItemCount(): Int {
        return mDateList.size
    }

    override fun onBindViewHolder(holder: MovieDateCardViewHolder, position: Int) {
        holder.bindData(mDateList[position],position)

        val dateCard = holder.itemView.findViewById<FrameLayout>(R.id.flDateCard)
        if(selectedPosition == position){
            holder.selectColor()
            delegate.onTapDateCard(dateListDateCard[0])
        }else{
            holder.defaultColor()
        }

        dateCard.setOnClickListener {
            val beforePosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(beforePosition)

            delegate.onTapDateCard(dateListDateCard[selectedPosition])
            holder.selectColor()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindDateCardData(dateList: MutableList<DateCard>) {
        mDateList = dateList
        notifyDataSetChanged()
    }
}