package com.yomogi.smoky

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yomogi.smoky.databinding.FragmentProjectListBinding

open class ProjectListFragment : Fragment() {

    companion object {
        val TAG = ProjectListFragment::class.java.simpleName
        private lateinit var projectAdapter : ProjectAdapter
        private lateinit var binding: FragmentProjectListBinding
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView Called")

        //dataBinding用のレイアウトリソースを設定する
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container,
            false)

        //イベントのCallbackをAdapterに送る
        projectAdapter = ProjectAdapter(projectClickCallback)

        //AdapterをRecyclerViewに適用
        binding.projectList.adapter = projectAdapter

        //Loading開始
        binding.isLoading = true

        //RootViewを返却
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putBoolean("isLoading", )
        Log.d(TAG,"onSaveInstanceState Called")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG,"onActivityCreated Called")
        val viewModel : ProjectListViewModel = ViewModelProviders.of(this).
            get(ProjectListViewModel::class.java)

        observeViewModel(viewModel)
    }

    //observe関数でLiveDataのActive状態を監視
    private fun observeViewModel(viewModel: ProjectListViewModel) {
        Log.d(TAG,"observeViewModel Called")

        viewModel.getProjectListObservable().observe(this,
            Observer { projects ->
                Log.d(TAG,"observe called")
                if (projects != null) {
                    Log.d(TAG,"Loading完了")
                    binding.isLoading = false
                    projectAdapter.setProjectList(projects)
                }else{
                    Log.d(TAG, "project resume")
                }
            })
    }

    //Callback Init
    private val projectClickCallback : ProjectClickCallback = object : ProjectClickCallback {
        override fun onClick(project : Project) {
            if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as GithubActivity).show(project)
            }
        }
    }

}