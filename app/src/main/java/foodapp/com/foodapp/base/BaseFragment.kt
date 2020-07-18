package foodapp.com.foodapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    protected abstract fun getBinding(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?
    ): VB

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}