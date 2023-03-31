package vn.gotech.audiobook.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

class VersionCheckingViewModel : ViewModel() {

    fun versionChecking(ref: DatabaseReference): LiveData<DataSnapshot> {
        return VersionCheckingLiveData(ref)
    }
}