package com.yomogi.smoky

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yomogi.smoky.databinding.FragmentProjectDetailsBinding

private const val KEY_PROJECT_ID = "project_id"

open class ProjectFragment : Fragment() {

    private lateinit var binding : FragmentProjectDetailsBinding
    private val LOG_TAG : String = ProjectFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.d(LOG_TAG, "onCreateView Called")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details,
            container, false) as FragmentProjectDetailsBinding

        return requireNotNull(this.binding).root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d(LOG_TAG, "onActivityCreated Called")

        //Dependency Injection
        val factory :ProjectViewModel.Factory = ProjectViewModel.Factory(
            activity?.applicationContext as Application, requireNotNull(arguments).getString(KEY_PROJECT_ID)!!)

        //viewModelインスタンスをproject_idをキーとして取得
        val viewModel : ProjectViewModel = ViewModelProviders.of(this, factory).
            get(ProjectViewModel::class.java)

        //ViewにViewModelをセット
        binding.projectViewModel = viewModel
        //app:visibleGone="@{isLoading}"をtrueに設定する
        binding.isLoading = true

        //データ監視を開始 -> 差分を監視して、検知次第でViewModelに通知する
        observeViewModel(viewModel)
    }

    //Modelの監視クラス(Observer Class)
    private fun observeViewModel(viewModel: ProjectViewModel){
        viewModel.getObservableProject().observe(this, Observer { project ->
            if(project != null) {
                Log.d(LOG_TAG, "projectSet")
                binding.isLoading = false
                viewModel.setProject(project)
            }else{
                Log.d(LOG_TAG, "project resume")
            }
        })
    }

    //Activity遷移のタイミングでidをFragmentToFragmentで渡す
    companion object{
        fun forProject(projectId : String) : ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()

            args.putString(KEY_PROJECT_ID, projectId)
            fragment.arguments = args

            return fragment
        }
    }


}