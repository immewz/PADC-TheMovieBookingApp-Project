package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.ui.viewholders.movie.BottomSheetDialogFoodViewHolder

class BottomSheetDialogFoodAdapter:RecyclerView.Adapter<BottomSheetDialogFoodViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetDialogFoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_bottom_sheet_dialog_food,parent,false)
        return BottomSheetDialogFoodViewHolder(view.rootView)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: BottomSheetDialogFoodViewHolder, position: Int) {
    }
}