package com.jinjoo.managerpouch.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.jinjoo.managerpouch.PouchApplication
import com.jinjoo.managerpouch.R
import com.jinjoo.managerpouch.databinding.TabItemBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class CategoryTabListAdapter :
    RecyclerView.Adapter<CategoryTabListAdapter.CategoryTabViewHolder>() {

    private val compositeDisposable = CompositeDisposable()
    private var categoryList: ArrayList<String> =
        PouchApplication.getApplicationContext().resources.getStringArray(R.array.category_array)
            .toCollection(ArrayList())
    val itemClickSubject: PublishSubject<Int> = PublishSubject.create()
    private var preSelectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTabViewHolder {
        val binding = TabItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("CategoryTabListAdapter", "list : ${categoryList.toString()}")
        return CategoryTabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryTabViewHolder, position: Int) {
        holder.bind(categoryList[position])

        compositeDisposable.add(
            holder.binding.categoryName
                .clicks()
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe {
                    preSelectedPosition = position
                    notifyDataSetChanged()
                    itemClickSubject.onNext(position)
                }
        )
        tabSelected(holder, position)

    }

    private fun tabSelected(holder: CategoryTabViewHolder, position: Int) {
        if (preSelectedPosition == position) {
            holder.binding.categoryName.apply {
                setBackgroundResource(R.drawable.tab_background)
                setTextColor(PouchApplication.getApplicationContext().getColor(R.color.white))
            }

        } else {
            holder.binding.categoryName.apply {
                background = null
                setTextColor(
                    PouchApplication.getApplicationContext().getColor(R.color.tab_text_color)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun getItemId(position: Int): Long {
        return categoryList[position].hashCode().toLong()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        itemClickSubject.onComplete()
        compositeDisposable.clear()
    }

    fun getList(): ArrayList<String> {
        return categoryList
    }


    inner class CategoryTabViewHolder(val binding: TabItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.category = category
        }
    }
}