package foodapp.com.foodapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import foodapp.com.foodapp.R
import kotlinx.android.synthetic.main.layout_placeholder_empty_view.view.*
import org.jetbrains.anko.imageResource

class PlaceholderView : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        View.inflate(context, R.layout.layout_placeholder_empty_view, this)
    }

    fun showEmptyView(show: Boolean = true) {
        errorImageView.visibility = if (show) View.VISIBLE else View.GONE
        titleTextView.visibility = if (show) View.VISIBLE else View.GONE
        descriptionTextView.visibility = if (show) View.VISIBLE else View.GONE
        actionButtonContainer.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setContents(imageRes: Int, title: Int, subtitle: Int, buttonTitle: Int, action: () -> (Unit)) {
        errorImageView.imageResource = imageRes
        titleTextView.text = context.getString(title)
        descriptionTextView.text = context.getString(subtitle)
        actionButton.text = context.getString(buttonTitle)

        actionButton.setOnClickListener {
            action()
        }
    }

    fun showProgress(show: Boolean = true) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        errorImageView.visibility = View.GONE
        titleTextView.visibility = View.GONE
        descriptionTextView.visibility = View.GONE
        actionButtonContainer.visibility = View.GONE
    }
}