package co.android.wframadhan.themoviesuperapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import co.android.wframadhan.domain.models.Movie
import co.android.wframadhan.themoviesuperapp.App
import co.android.wframadhan.themoviesuperapp.R
import co.android.wframadhan.themoviesuperapp.ViewModelFactory
import co.android.wframadhan.themoviesuperapp.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {


    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private lateinit var viewModel: HomeViewModel
    private val adapter: MovieAdapter by lazy {
        MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it) // Set the Movie to the _navigateToSelectedMovie Live Data
        })
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModel.onViewLoaded()
        initView()

    }

    private fun initView() {
        observeviewState()
        observeClick()
        changeStatusBar(R.color.colorHomeBg)
        setupRecyclerView()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        val color = if (hidden) R.color.white
        else R.color.colorHomeBg
        changeStatusBar(color)
    }

    private fun changeStatusBar(color: Int) {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), color)
    }

    private fun observeviewState() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeViewState.Loading -> {
                    Log.d("TAG", "LOADING")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                is Error -> {
                    Log.d("TAG", "ERROR HOME FRAGMENT")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                }
                is HomeViewState.Presenting -> {
                    binding.statusImage.visibility = View.GONE
                    showMovies(it.results)
                }
            }
        })
    }

    private fun observeClick() {
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (it != null) { //Open the Detail Fragment if _navigateToSelectedMovie is not Null
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                )
                viewModel.displayMovieDetailsComplete() // Clear the _navigateToSelectedMovie after the Detail fragment is opened
            }
        })
    }

    private fun showMovies(movies: List<Movie>) {
        adapter.submitList(removeBrokenMovies(movies))
    }

    private fun setupRecyclerView() {
        Log.d("TAG", "setupRecyclerView")
        binding.movieRecyclerView.setHasFixedSize(true)
        binding.movieRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        binding.movieRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
