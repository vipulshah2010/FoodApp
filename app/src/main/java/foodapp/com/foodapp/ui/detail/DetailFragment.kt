package foodapp.com.foodapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.R
import foodapp.com.foodapp.base.BaseFragment
import foodapp.com.foodapp.databinding.FragmentDetailBinding
import foodapp.com.foodapp.themeColor
import foodapp.com.foodapp.ui.list.FoodViewModel
import foodapp.com.foodapp.ui.splash.SplashViewPager
import foodapp.com.foodapp.ui.views.DepthPageTransformer

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: FoodViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    private var pixelDensity: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transformation = MaterialContainerTransform()
        transformation.interpolator = AnimationUtils.LINEAR_INTERPOLATOR
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 300
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pixelDensity = resources.displayMetrics.density

        viewModel.foodItemLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FoodResult.Error -> {
                    Toast.makeText(requireContext(), "Error!",
                            Toast.LENGTH_SHORT).show()
                }
                is FoodResult.Success -> {
                    onLoadFoodItem(it.data)
                }
            }
        })

        takeIf { id != -1 }?.apply {
            viewModel.getFoodItem(args.foodID)
        }

        postponeEnterTransition()
    }

    private fun onLoadFoodItem(foodItem: FoodItem) {

        binding.foodImageView.transitionName = foodItem.votes.toString()

        binding.profileImageView.load(foodItem.profileImage, builder = {
            crossfade(true)
            transformations(CircleCropTransformation())
        })

        binding.foodImageView.load(foodItem.heroImage, builder = {
            target(onError = {
                binding.foodImageView.setImageResource(R.drawable.ic_svg_food)
            }, onSuccess = {
                binding.foodImageView.setImageDrawable(it)
                startPostponedEnterTransition()
            })
        })

        binding.foodDescTextView.text = foodItem.foodDescription

        binding.nameTextView.text = foodItem.name

        binding.dateTextView.text = foodItem.date

        binding.votesTextView.text = "${foodItem.votes}"

        binding.dishesViewPager.setPageTransformer(true, DepthPageTransformer())

        val fragmentManager = childFragmentManager
        binding.dishesViewPager.adapter = SplashViewPager(imagesUrls = foodItem.foodImages,
                manager = fragmentManager)

        binding.tabLayout.setupWithViewPager(binding.dishesViewPager, true)
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?) =
            FragmentDetailBinding.inflate(inflater, container, false)
}