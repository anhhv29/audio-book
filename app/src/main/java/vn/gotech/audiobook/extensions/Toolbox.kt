package vn.gotech.audiobook.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.format.Formatter
import android.util.Base64
import android.util.Base64OutputStream
import android.util.Log
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.google.gson.Gson
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import vn.gotech.audiobook.base.common.UserDataManager
import java.io.*
import java.math.BigInteger
import java.net.Inet4Address
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object Toolbox {
    const val TAG = "Toolbox"

    private val defaultGson = Gson()
    var timer = -1
    var timerStart = -1L
    var currentScreen = ""
    var firstOpenApp = true

    val LOCALE_VN = Locale("vi", "VN")
    val displayDateFormat = SimpleDateFormat("dd/MM/yyyy", LOCALE_VN)
    val displayDateFormat2 = SimpleDateFormat("MM/yyyy", LOCALE_VN)
    val displayDateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", LOCALE_VN)
    val displayDateTimeFormat2 = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", LOCALE_VN)
    val apiDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", LOCALE_VN)
    val apiDateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", LOCALE_VN)

    val gson = defaultGson

    fun getMazdaEvo(): Boolean {
        val height = UserDataManager.displayHeight
        val width = UserDataManager.displayWidth

        Log.e("ScreenSize MazdaEvo", "${width.toFloat() / height.toFloat()}")
        return ((width.toFloat() / height.toFloat()) >= 2.0)
    }

    fun getBoxPortrait(): Boolean {
        val height = UserDataManager.displayHeight
        val width = UserDataManager.displayWidth
        Log.e("ScreenSize Box", "${width.toFloat() / height.toFloat()}")
        return ((width.toFloat() / height.toFloat()) <= 1.15)
    }

    fun getEvo(activity: Activity): Boolean {
        val packages: List<ApplicationInfo>

        val pm = activity.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == "vn.gotech.launcher.evo") return true
        }
        return false
    }

    fun getMazda(activity: Activity): Boolean {
        val packages: List<ApplicationInfo>

        val pm = activity.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == "vn.gotech.launcher.mazda") return true
        }
        return false
    }

    fun getDateTimeCurrent(): String {
        val calendar = Calendar.getInstance()
        val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", LOCALE_VN)
        return format.format(calendar.time)
    }

    fun getDateTimeCurrent2(): String {
        val calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyyMMddHHmmss", LOCALE_VN)
        return format.format(calendar.time)
    }

    fun shareText(context: Context, content: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, content)
        context.startActivity(Intent.createChooser(shareIntent, "Share"))
    }

    fun displayTimeComment(timeAtMiliseconds: String): String {
        if (timeAtMiliseconds.isEmpty()) {
            return ""
        }
        //API.log("Day Ago "+dayago);
        var result = "Vừa xong";
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("vi"))
        val todayDate = formatter.format(Date())
        val calendar = Calendar.getInstance()

        val dayagolong = formatter.parse(timeAtMiliseconds).time

        calendar.timeInMillis = dayagolong
        val agoformater = formatter.format(calendar.time)

        try {
            val CurrentDate = formatter.parse(todayDate)
            val CreateDate = formatter.parse(agoformater)

            var different = Math.abs(CurrentDate.time - CreateDate.time);

            val secondsInMilli = 1000;
            val minutesInMilli: Long = secondsInMilli.toLong() * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            val elapsedDays = different / daysInMilli
            different %= daysInMilli

            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            different %= secondsInMilli

            if (elapsedDays == 0L) {
                if (elapsedHours == 0L) {
                    if (elapsedMinutes == 0L) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s"
                        } else {
                            if (elapsedDays > 0 && elapsedSeconds < 59) {
                                return "Vừa xong"
                            }
                        }
                    } else {
                        return "$elapsedMinutes phút trước"
                    }
                } else {
                    return "$elapsedHours giờ trước"
                }

            } else {
                if (elapsedDays <= 29) {
                    return "$elapsedDays ngày trước"
                }
                if (elapsedDays in 30..58) {
                    return "1 tháng trước"
                }
                if (elapsedDays in 59..87) {
                    return "2 tháng trước"
                }
                if (elapsedDays in 88..116) {
                    return "3 tháng trước"
                }
                if (elapsedDays in 117..145) {
                    return "4 tháng trước"
                }
                if (elapsedDays in 146..174) {
                    return "5 tháng trước"
                }
                if (elapsedDays in 175..203) {
                    return "6 tháng trước"
                }
                if (elapsedDays in 204..232) {
                    return "7 tháng trước"
                }
                if (elapsedDays in 233..261) {
                    return "8 tháng trước"
                }
                if (elapsedDays in 262..290) {
                    return "9 tháng trước"
                }
                if (elapsedDays in 291..319) {
                    return "10 tháng trước"
                }
                if (elapsedDays in 320..348) {
                    return "11 tháng trước"
                }
                if (elapsedDays in 349..360) {
                    return "12 tháng trước"
                }

                if (elapsedDays in 361..720) {
                    return "1 năm trước"
                }

                if (elapsedDays > 720) {
                    val formatterYear = SimpleDateFormat("MM/dd/yyyy")
                    val calendarYear = Calendar.getInstance()
                    calendarYear.timeInMillis = dayagolong
                    return formatterYear.format(calendarYear.time) + ""
                }

            }

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result;
    }

    @Throws(IOException::class)
    fun reEncodeBitmap(context: Context, sourceUri: Uri, targetSize: Int, destinationUri: Uri) {
        Log.d(TAG, "reEncodeBitmap: source: $sourceUri")
        Log.d(TAG, "reEncodeBitmap: targetSize: $targetSize")
        Log.d(TAG, "reEncodeBitmap: destination: $destinationUri")

        val closeables = ArrayList<Closeable>()
        var sourceFile: File? = null

        try {
            // Open stream
            val inputStream = context.contentResolver.openInputStream(sourceUri)
            inputStream?.let { closeables.add(it) }

            // Copy to cache
            sourceFile = File(context.cacheDir, UUID.randomUUID().toString())
            FileUtils.copyInputStreamToFile(inputStream!!, sourceFile)

            // Get size
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(sourceFile.canonicalPath, options)
            Log.d(TAG, "reEncodeBitmap: size: " + options.outWidth + " x " + options.outHeight)

            // Setting output size
            val orgMaxWidth = options.outWidth.coerceAtLeast(options.outHeight)
            if (orgMaxWidth > targetSize) {
                // Size is bigger than target size, resize the bitmap
                options.inSampleSize = (orgMaxWidth.toFloat() / targetSize).roundToInt()
                Log.d(TAG, "reEncodeBitmap: inSampleSize = " + options.inSampleSize)
            }
            options.inJustDecodeBounds = false

            // Decode
            val bitmap = BitmapFactory.decodeFile(sourceFile.canonicalPath, options)

            // Exif interface
            val exif = ExifInterface(sourceFile.canonicalPath)
            sourceFile.delete()
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            Log.d(TAG, "reEncodeBitmap: orientation = $orientation")

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> {
                    Log.d(TAG, "reEncodeBitmap: orientation = " + "ORIENTATION_FLIP_HORIZONTAL")
                    matrix.setScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    Log.d(TAG, "reEncodeBitmap: orientation = " + "ORIENTATION_ROTATE_180")
                    matrix.setRotate(180f)
                }
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                    Log.d(TAG, "reEncodeBitmap: orientation = " + "ORIENTATION_FLIP_VERTICAL")
                    matrix.setRotate(180f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_TRANSPOSE -> {
                    Log.d(TAG, "reEncodeBitmap: orientation = " + "ORIENTATION_TRANSPOSE")
                    matrix.setRotate(90f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    Log.d(TAG, "reEncodeBitmap: orientation = " + "ORIENTATION_ROTATE_90")
                    matrix.setRotate(90f)
                }
                ExifInterface.ORIENTATION_TRANSVERSE -> {
                    Log.d(TAG, "reEncodeBitmap: orientation = " + "ORIENTATION_TRANSVERSE")
                    matrix.setRotate(-90f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    Log.d(TAG, "reEncodeBitmap: orientation = " + "ORIENTATION_ROTATE_270")
                    matrix.setRotate(-90f)
                }
            }

            // Rotate
            val bmRotated =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            // Compress
            val outputStream = context.contentResolver.openOutputStream(destinationUri)
            outputStream?.let { closeables.add(it) }
            bmRotated.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)

        } finally {
            for (closeable in closeables) {
                IOUtils.closeQuietly(closeable)
            }
            FileUtils.deleteQuietly(sourceFile)
        }
    }

    fun convertImageFileToBase64(imageFile: File): String {

        return FileInputStream(imageFile).use { inputStream ->
            ByteArrayOutputStream().use { outputStream ->
                Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                    inputStream.copyTo(base64FilterStream)
                    base64FilterStream.flush()
                    outputStream.toString()
                }
            }
        }
    }

    fun setStatusBarGradiant(activity: Activity, @DrawableRes drawableRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val background = activity.resources.getDrawable(drawableRes)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(android.R.color.transparent)
            window.navigationBarColor = activity.resources.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }

    fun setColorStatusBar(activity: Activity, @ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(color)
            window.navigationBarColor = activity.resources.getColor(color)
        }
    }

    @SuppressLint("WifiManagerPotentialLeak")
    fun getIpAddress(context: Context): String {
        var ip = ""
        try {
            val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        } catch (e: java.lang.Exception) {

        }

        if (ip.isEmpty()) {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val networkInterface = en.nextElement()
                    val enumIpAddr = networkInterface.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                            val host = inetAddress.getHostAddress()
                            if (host.isNotEmpty()) {
                                ip = host
                                break
                            }
                        }
                    }

                }
            } catch (e: java.lang.Exception) {

            }
        }

        if (ip.isEmpty())
            ip = "127.0.0.1"
        return ip
    }

    fun getMd5Hash(input: String): String? {
        return try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(input.toByteArray())
            val number = BigInteger(1, messageDigest)
            var md5 = number.toString(16)

            while (md5.length < 32)
                md5 = "0$md5"

            md5
        } catch (e: NoSuchAlgorithmException) {
            Log.e("MD5", e.localizedMessage)
            null
        }
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        var device = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        if (device.isNullOrEmpty()) device = Build.SERIAL
        if (device.isEmpty()) device = Build.DISPLAY
        return device
    }

    fun nextFloatBetween(min: Float, max: Float): Float {
        return (Math.random() * (max - min)).toFloat() + min
    }
}