package com.chs.module_wan.ui.project

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.R
import com.chs.module_wan.model.DataX
import com.chs.module_wan.model.ProjectData
import com.chs.module_wan.model.ProjectListItemData
import kotlinx.android.synthetic.main.wan_item_home_list.*
import kotlinx.android.synthetic.main.wan_item_project.*

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class ProjectAdapter() :
    AbsPageListAdapter<ProjectListItemData, ProjectHolder>(object : DiffUtil.ItemCallback<ProjectListItemData>() {
        override fun areItemsTheSame(oldItem: ProjectListItemData, newItem: ProjectListItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProjectListItemData, newItem: ProjectListItemData): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun getLayoutId(): Int = R.layout.wan_item_project

    override fun createCurrentViewHolder(view: View, viewType: Int): ProjectHolder {
        return ProjectHolder(view)
    }
}

class ProjectHolder(itemView: View) : BaseViewHolder<ProjectListItemData>(itemView) {
    override fun setContent(item: ProjectListItemData) {
        tv_name.text = item.author
    }
}