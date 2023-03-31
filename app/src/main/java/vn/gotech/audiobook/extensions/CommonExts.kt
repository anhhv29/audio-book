package vn.gotech.audiobook.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.google.i18n.phonenumbers.PhoneNumberUtil
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import vn.gotech.audiobook.R
import vn.gotech.audiobook.base.domain.ressponse.Book
import vn.gotech.audiobook.base.domain.ressponse.Card
import vn.gotech.audiobook.base.domain.ressponse.News
import java.io.PrintWriter
import java.io.StringWriter
import java.text.NumberFormat
import java.util.*

fun Throwable.showStackTrace(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    this.printStackTrace(pw)
    return sw.toString()
}

fun Int.asColor(context: Context): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        context.resources.getColor(this, context.theme)
    else context.resources.getColor(this)
}

fun String.asNumber(hour: Double): String? {
    var total_sec = hour * 60.0 * 60.0
    var i = 4
    val number = IntArray(4)
    while (i > 0) {
        i--
        number[i] = (total_sec % 60).toInt()
        total_sec /= 60.0
    }

    val builder = StringBuilder()
    if (number[0] > 0) builder.append(number[0]).append(" ngày ")
    if (number[1] > 0) builder.append(number[1]).append(" giờ ")
    if (number[2] > 0) builder.append(number[2]).append(" phút ")
    if (number[3] > 0) builder.append(number[3]).append(" giây ")
    return if (builder.isEmpty()) "0" else builder.toString()
}

fun String.asPhone(): String? {
    val phoneUtil = PhoneNumberUtil.getInstance()
    val phoneNumber = try {
        phoneUtil.parse(this, "VN")
    } catch (e: Exception) {
        null
    }
    return if (phoneNumber != null && phoneUtil.isValidNumber(phoneNumber)) phoneUtil.format(
        phoneNumber,
        PhoneNumberUtil.PhoneNumberFormat.E164
    ) else this
}

fun String.asDate(): String? {
    return try {
        Toolbox.displayDateFormat.format(Toolbox.apiDateFormat2.parse(this))
    } catch (e: Exception) {
        this
    }
}

fun String.asMonth(): String? {
    return try {
        Toolbox.displayDateFormat2.format(Toolbox.apiDateFormat2.parse(this))
    } catch (e: Exception) {
        this
    }
}

fun String.asDateTime(): String? {
    return try {
        Toolbox.displayDateTimeFormat.format(Toolbox.apiDateFormat.parse(this))
    } catch (e: Exception) {
        this
    }
}

fun String.asConvertUnixtime(): String? {
    return try {
        val date = Date(this.toLong() * 1000L)
        Toolbox.displayDateTimeFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun String.asDateToUnixtime(): Long {
    return Toolbox.apiDateFormat2.parse(this)?.time ?: System.currentTimeMillis()
}

fun Long.asMoney(): String {
    val numberFormat = NumberFormat.getIntegerInstance(Locale("vi"))
    val formatted = numberFormat.format(this)
    return "$formatted VNĐ"
}

fun Double.asMoney(): String {
    val numberFormat = NumberFormat.getIntegerInstance(Locale("vi"))
    val formatted = numberFormat.format(this)
    return "$formatted VNĐ"
}

fun TextView.setPhone(phone: CharSequence, phoneNumber: String) {
    if (phone.length >= phoneNumber.length) {
        this.text = phone.setPhone(phoneNumber)
        this.movementMethod = LinkMovementMethod.getInstance()
    } else this.text = phone
}

fun CharSequence.setPhone(phoneNumber: String): CharSequence {
    val spannable = SpannableString(this)
    if (this.isNotEmpty() && phoneNumber.isNotEmpty()) {
        val positionStart =
            if (this.indexOf(phoneNumber.substring(0)) > 0) this.indexOf(phoneNumber.substring(0)) else 0
        val positionEnd = positionStart + (phoneNumber.length)
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            positionStart, positionEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            UnderlineSpan(),
            positionStart, positionEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                //what happens whe i click
                val uri = Uri.parse("tel:$phoneNumber")
                val i = Intent(Intent.ACTION_DIAL, uri)
                view.context.startActivity(i)
            }
        }
        spannable.setSpan(
            clickableSpan,
            positionStart, positionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannable
}

fun TextView.setUnderLine(context: Context, s: CharSequence) {
    this.text = s.asUnderLine(context, s.toString())
    this.movementMethod = LinkMovementMethod.getInstance()
}

fun CharSequence.asUnderLine(context: Context, s: String): CharSequence {
    val spannable = SpannableString(this)
    if (this.isNotEmpty() && s.isNotEmpty()) {

        spannable.setSpan(
            ForegroundColorSpan(context.resources.getColor(R.color.md_deep_orange_500)),
            0, s.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            UnderlineSpan(),
            0, s.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannable
}

fun Long.asNumber(): String? {
    var i = 3
    var sec = this
    val number = IntArray(3)
    while (i > 0) {
        i--
        sec /= 60
        number[i] = (sec % 60).toInt()
    }

    val builder = StringBuilder()
    if (number[0] > 0) builder.append(number[0]).append(" ngày ")
    if (number[1] > 0) builder.append(number[1]).append(" giờ ")
    if (number[2] > 0) builder.append(number[2]).append(" phút")
    return if (builder.isEmpty()) "0" else builder.toString()
}

fun String.asHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    else Html.fromHtml(this)

}

fun Int.dpToPx(ctx: Context): Int {
    val displayMetrics = ctx.resources.displayMetrics
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        displayMetrics
    ).toInt()
}

fun Int.pxToDp(ctx: Context): Float {
    val displayMetrics = ctx.resources.displayMetrics
    return this.toFloat() / (displayMetrics.densityDpi / 160f)
}

fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.observable(): Observable<String> {

    val subject = PublishSubject.create<String>()

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            subject.onNext(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })

    return subject
}

fun Long.asLongToTime(): String {
    val seconds = (this / 1000).toInt() % 60
    val minutes = (this / (1000 * 60) % 60).toInt()
    val hours = (this / (1000 * 60 * 60)).toInt() % 24

    val strSeconds = if (seconds.toString().length < 2) "0$seconds" else seconds.toString()
    val strMinutes = if (minutes.toString().length < 2) "0$minutes" else minutes.toString()
    val strHours = if (hours.toString().length < 2) "0$hours" else hours.toString()
    return if (hours > 0) "${strHours}:${strMinutes}:${strSeconds}" else "${strMinutes}:${strSeconds}"
}

fun Card.convert2Book(): Book {
    val book = Book()
//    book.id = this.id
//    book.image = this.image
//    book.favorite = this.favorite
//    book.isRead = this.isRead
//    book.name = this.name
//    book.rating = this.rating
//    book.viewCount = this.viewCount
//    book.progress = this.progress
    return book
}

fun Card.convert2News(): News {
    val news = News()
    news.id = this.id
    news.thumbnail = this.image
    news.favorite = this.favorite
    news.isRead = this.isRead
    news.name = this.name
    news.rating = this.rating
    news.viewCount = this.viewCount
    return news
}