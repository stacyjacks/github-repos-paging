package kurmakaeva.anastasia.githubrepos.detailui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kurmakaeva.anastasia.githubrepos.R
import kurmakaeva.anastasia.githubrepos.databinding.FragmentDetailRepoBinding

class RepoDetailFragment: Fragment() {

    private lateinit var viewModel: RepoDetailViewModel
    private lateinit var binding: FragmentDetailRepoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_detail_repo, container, false)

        val args by navArgs<RepoDetailFragmentArgs>()

        binding.lifecycleOwner = this

        viewModelSetup(args.repoOwner, args.repoName)

        return binding.root
    }

    private fun viewModelSetup(owner: String, repoName: String) {
        viewModel = ViewModelProvider(this).get(RepoDetailViewModel::class.java)
        viewModel.getRepo(owner, repoName)

        val languageText = getString(R.string.language_text)

        onDetailLoading()

        viewModel.singleRepoData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                detailRepoNameTextView.text = it.name
                detailRepoOwnerTextView.text = it.owner.login
                detailRepoDescriptionTextView.text = it.description
                detailRepoLanguageTextView.text = languageText.replace("%s", it.language)
                detailStarCount.text = it.stargazers_count.toString()
                detailForkCount.text = it.forks_count.toString()
            }

            val avatarUrl = it.owner.avatar_url

            Glide.with(this)
                .load(avatarUrl)
                .into(binding.detailOwnerAvatar)

            val repoUrl = it.html_url
            binding.viewOnGitHubButton.setOnClickListener {
                val viewInBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repoUrl))
                startActivity(viewInBrowserIntent)
            }

            onDetailLoaded()
        })
    }

    private fun onDetailLoading() {
        binding.apply {
            detailStatsTextView.visibility = View.GONE
            detailStatsDataLL.visibility = View.GONE
            viewOnGitHubButton.visibility = View.GONE
            progressCircularDetail.visibility = View.VISIBLE
        }
    }

    private fun onDetailLoaded() {
        binding.apply {
            progressCircularDetail.visibility = View.GONE
            detailStatsTextView.visibility = View.VISIBLE
            detailStatsDataLL.visibility = View.VISIBLE
            viewOnGitHubButton.visibility = View.VISIBLE
        }
    }
}