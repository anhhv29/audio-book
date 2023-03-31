package vn.gotech.audiobook.extensions

import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.ressponse.*

fun AppCompatActivity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content)?.windowToken, 0)
}

fun Fragment.hideKeyboard(): Boolean {
    context?.let {
        val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(
            this.view?.findViewById<View>(android.R.id.content)?.windowToken,
            0
        )
    }

    return true
}

fun Fragment.hideKeyboardForce(): Boolean {
    context?.let {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    return true
}

fun AppCompatActivity.getBookSave(): Book? {
    return if (UserDataManager.lastBook.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastBook, Book::class.java)
    else null
}

fun Fragment.getBookSave(): Book? {
    return if (UserDataManager.lastBook.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastBook, Book::class.java)
    else null
}

fun Fragment.getNewsSave(): News? {
    return if (UserDataManager.lastNews.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastNews, News::class.java)
    else null
}

fun AppCompatActivity.getNewsSave(): News? {
    return if (UserDataManager.lastNews.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastNews, News::class.java)
    else null
}


fun Fragment.getPodcastSave(): Podcast? {
    return if (UserDataManager.lastPodcast.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastPodcast, Podcast::class.java)
    else null
}

fun AppCompatActivity.getPodcastSave(): Podcast? {
    return if (UserDataManager.lastPodcast.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastPodcast, Podcast::class.java)
    else null
}

fun Fragment.getEpisodeSave(): Episode? {
    return if (UserDataManager.lastEpisode.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastEpisode, Episode::class.java)
    else null
}

fun AppCompatActivity.getEpisodeSave(): Episode? {
    return if (UserDataManager.lastEpisode.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastEpisode, Episode::class.java)
    else null
}

fun AppCompatActivity.getChapterSave(): Chapter? {
    return if (UserDataManager.lastChapter.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastChapter, Chapter::class.java)
    else null
}

fun Fragment.getChapterSave(): Chapter? {
    return if (UserDataManager.lastChapter.isNotEmpty())
        Toolbox.gson.fromJson(UserDataManager.lastChapter, Chapter::class.java)
    else null
}

fun Any.asThumbnailToString(): String {
    return try {
        var url = this as String
        url = if (url.contains(Const.BASE_URL)) url else Const.BASE_URL + url
        return url
    } catch (e: Exception) {
        ""
    }
}

fun AppCompatActivity.setStatusBarColor() {
    if (isFinishing) {
        return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}
