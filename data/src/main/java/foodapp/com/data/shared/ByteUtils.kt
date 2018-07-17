package foodapp.com.data.shared

object ByteUtils {

    fun toBytes(valueInUnit: Long, unit: ByteUnit): Long {
        return valueInUnit * unit.factor()
    }

    enum class ByteUnit(private val factor: Long) {
        BYTES(1), KB(1024), MB(1024 * 1024), GB(1024 * 1024 * 1024);

        fun factor(): Long {
            return factor
        }
    }
}
