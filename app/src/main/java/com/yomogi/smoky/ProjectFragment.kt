package com.yomogi.smoky

import android.os.Bundle
import androidx.fragment.app.Fragment

private const val KEY_PROJECT_ID = "project_id"

open class ProjectFragment : Fragment() {

    companion object{
        fun forProject(projectId : String) : ProjectFragment {
            var fragment : ProjectFragment = ProjectFragment()
            var args : Bundle = Bundle()

            args.putString(KEY_PROJECT_ID, projectId)
            fragment.setArguments(args)

            return fragment
        }
    }
}