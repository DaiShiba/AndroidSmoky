package com.yomogi.smoky

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yomogi.smoky.databinding.ProjectListItemBinding
import java.util.*

class ProjectAdapter : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    //JVMのシグネチャ衝突を避けるためPrivate付与
    private var projectList : List<Project>? = null
    @Nullable private var projectClickCallback : ProjectClickCallback
    private val TAG = ProjectAdapter::class.java.simpleName

    constructor(@Nullable projectClickCallback: ProjectClickCallback) {
        this.projectClickCallback = projectClickCallback
    }

    //現状差分を取得してListをRecyclerViewにセットする
    fun setProjectList(projectList : List<Project>) {
        Log.d(TAG,"setProjectList called")

        if(this.projectList == null) {
            Log.d(TAG,"projectList is null")
            Log.d(TAG,"設定予定のprojectList size:${projectList.size}")
            this.projectList = projectList

            //positionStartの位置からitemCountの範囲において、データ変更があったことを登録しているObserverに通知
            notifyItemRangeInserted(0, projectList.size)
        }else{
            Log.d(TAG,"projectList is Notnull")
            //差分計算ユーティリティ
            val result : DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    //nullではないことを保証する、nullの場合はエラースロー
                    return requireNotNull(this@ProjectAdapter.projectList).size
                }

                override fun getNewListSize(): Int {
                    return projectList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return requireNotNull(this@ProjectAdapter.projectList)[oldItemPosition].id == projectList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project : Project = projectList[newItemPosition]
                    val oldProject : Project = projectList[oldItemPosition]

                    return project.id == oldProject.id && Objects.equals(project.git_url, oldProject.git_url)
                }
            })

            this.projectList = projectList

            //DiffUtilメソッド差分からRecycleViewAdapterのNotifiyがコールされる
            result.dispatchUpdatesTo(this)
        }
    }

    //継承したインナークラスのViewHolderをレイアウトと共に生成
    //bindするViewにCallbackを設定→ViewHolderを返却
    override fun onCreateViewHolder(parent: ViewGroup,view : Int): ProjectViewHolder {
        Log.d(TAG,"onCreateViewHolder called")
        val binding = DataBindingUtil.
            inflate(LayoutInflater.from(parent.context), R.layout.project_list_item,
                parent, false) as ProjectListItemBinding

        binding.callback = projectClickCallback

        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        Log.d(TAG,"onBindViewHolder called")
        holder.binding.project = requireNotNull(projectList)[position]
        holder.binding.executePendingBindings()
    }

    //リストのサイズを返却
    override fun getItemCount() : Int{
        Log.d(TAG,"getItemCount called")
        //Kotlinに三項演算子はないため、If文で実装する
        //return projectList == null ? 0 : projectList.size
        return if(projectList == null) 0 else requireNotNull(projectList).size
    }

    //インナークラスにViewHolderを継承project_list_itemとbindingを紐づけ
    open class ProjectViewHolder : RecyclerView.ViewHolder {
        var binding: ProjectListItemBinding

        constructor(binding: ProjectListItemBinding) : super(binding.root) {
            Log.d(ProjectViewHolder::class.java.simpleName,"ProjectViewHolder constructor called")
            this.binding = binding
        }
    }
}
