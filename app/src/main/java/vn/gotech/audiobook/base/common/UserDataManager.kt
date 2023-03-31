package vn.gotech.audiobook.base.common

import android.content.Context
import android.content.SharedPreferences

object UserDataManager {
    private const val PREF_NAME = "user_data_manager"

    private const val KEY_USER_ID = "user_id"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_USER_INFO = "user_info"
    private const val KEY_SKIP_UPDATE = "skip_update"
    private const val KEY_USER_AVATAR = "user_avatar"
    private const val KEY_USER_PHONE = "user_phone"
    private const val KEY_PACKAGE = "user_package"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_CREATE_AT = "createAt"
    private const val KEY_USER_DEVICE = "user_device"
    private const val KEY_SEARCH_KEYWORDS = "search_keywords"
    private const val KEY_IMEI = "device_imei"
    private const val KEY_BOOK = "book"
    private const val KEY_CHAPTER = "chapter"
    private const val KEY_NEWS = "news"
    private const val KEY_PODCAST = "podcast"
    private const val KEY_EPISODE = "episode"
    private const val KEY_IS_LOOP = "isLoop"
    private const val KEY_IS_RANDOM = "isRandom"

    var accessToken: String
        get() = pref.getString(KEY_ACCESS_TOKEN, "") ?: ""
        set(value) = pref.edit().putString(KEY_ACCESS_TOKEN, value).apply()
    var isLoop: Boolean
        get() = pref.getBoolean(KEY_IS_LOOP, false)
        set(value) = pref.edit().putBoolean(KEY_IS_LOOP, value).apply()
    var isRandom: Boolean
        get() = pref.getBoolean(KEY_IS_RANDOM, false)
        set(value) = pref.edit().putBoolean(KEY_IS_RANDOM, value).apply()
    var lastBook: String
        get() = pref.getString(KEY_BOOK, "") ?: ""
        set(value) = pref.edit().putString(KEY_BOOK, value).apply()
    var lastChapter: String
        get() = pref.getString(KEY_CHAPTER, "") ?: ""
        set(value) = pref.edit().putString(KEY_CHAPTER, value).apply()
    var lastNews: String
        get() = pref.getString(KEY_NEWS, "") ?: ""
        set(value) = pref.edit().putString(KEY_NEWS, value).apply()
    var lastPodcast: String
        get() = pref.getString(KEY_PODCAST, "") ?: ""
        set(value) = pref.edit().putString(KEY_PODCAST, value).apply()
    var lastEpisode: String
        get() = pref.getString(KEY_EPISODE, "") ?: ""
        set(value) = pref.edit().putString(KEY_EPISODE, value).apply()
    var skipUpdate: Boolean
        get() = pref.getBoolean(KEY_SKIP_UPDATE, false)
        set(value) = pref.edit().putBoolean(KEY_SKIP_UPDATE, value).apply()
    var currentUserAvatar: String
        get() = pref.getString(KEY_USER_AVATAR, "") ?: ""
        set(value) = pref.edit().putString(KEY_USER_AVATAR, value).apply()
    var currentUserId: Long
        get() = pref.getLong(KEY_USER_ID, -1L)
        set(value) = pref.edit().putLong(KEY_USER_ID, value).apply()
    var currentUserPhone: String
        get() = pref.getString(KEY_USER_PHONE, "") ?: ""
        set(value) = pref.edit().putString(KEY_USER_PHONE, value).apply()
    var currentUserName: String
        get() = pref.getString(KEY_USER_NAME, "") ?: ""
        set(value) = pref.edit().putString(KEY_USER_NAME, value).apply()
    var currentCreateAt: String
        get() = pref.getString(KEY_CREATE_AT, "") ?: ""
        set(value) = pref.edit().putString(KEY_CREATE_AT, value).apply()
    var currentUserDevice: String
        get() = pref.getString(KEY_USER_DEVICE, "") ?: ""
        set(value) = pref.edit().putString(KEY_USER_DEVICE, value).apply()
    var currentUserSearchKeywords: String
        get() = pref.getString(KEY_SEARCH_KEYWORDS, "") ?: ""
        set(value) = pref.edit().putString(KEY_SEARCH_KEYWORDS, value).apply()
    var currentIMEI: String
        get() = pref.getString(KEY_IMEI, "") ?: ""
        set(value) = pref.edit().putString(KEY_IMEI, value).apply()
    var currentPackage: Int
        get() = pref.getInt(KEY_PACKAGE, 0)
        set(value) = pref.edit().putInt(KEY_PACKAGE, value).apply()

    var displayWidth: Int = 0
    var displayHeight: Int = 0

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private lateinit var pref: SharedPreferences

    fun init(context: Context) {
        pref = getPrefs(context)
        val edit = pref.edit()
        edit.remove(KEY_SKIP_UPDATE)
        edit.apply()

        val displayMetrics = context.resources.displayMetrics
        displayWidth = displayMetrics.widthPixels
        displayHeight = displayMetrics.heightPixels
    }

    fun deleteUserInfo() {
        val edit = pref.edit()
        edit.remove(KEY_ACCESS_TOKEN)
        edit.remove(KEY_USER_AVATAR)
        edit.remove(KEY_USER_ID)
        edit.remove(KEY_USER_PHONE)
        edit.remove(KEY_USER_NAME)
        edit.remove(KEY_USER_DEVICE)
        edit.remove(KEY_SKIP_UPDATE)
        edit.remove(KEY_SEARCH_KEYWORDS)
        edit.remove(KEY_USER_INFO)
        edit.remove(KEY_CREATE_AT)
        edit.remove(KEY_IS_LOOP)
        edit.remove(KEY_IS_RANDOM)
        edit.remove(KEY_BOOK)
        edit.remove(KEY_CHAPTER)
        edit.remove(KEY_NEWS)
        edit.remove(KEY_PODCAST)
        edit.remove(KEY_EPISODE)
        edit.remove(KEY_PACKAGE)
        edit.apply()
    }
}