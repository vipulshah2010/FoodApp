package foodapp.com.foodapp

enum class ErrorType(val icon: Int, val title: Int, val subtitle: Int) {

    CONNECTION_ERROR(
            R.drawable.ic_svg_no_network_connection,
            R.string.inv_connection_error_title,
            R.string.inv_connection_error_subtitle),

    TIMEOUT_ERROR(
            R.drawable.ic_svg_network_error,
            R.string.inv_timeout_error_title,
            R.string.inv_timeout_error_subtitle),

    SERVER_ERROR(
            R.drawable.ic_svg_network_error,
            R.string.inv_generic_error_title,
            R.string.inv_generic_error_subtitle),

    CLIENT_ERROR(
            R.drawable.ic_svg_network_error,
            R.string.inv_generic_error_title,
            R.string.inv_generic_error_subtitle)
}
