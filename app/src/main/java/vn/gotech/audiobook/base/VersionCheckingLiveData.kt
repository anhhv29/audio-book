package vn.gotech.audiobook.base

import androidx.lifecycle.LiveData
import com.google.firebase.database.*

class VersionCheckingLiveData (ref: DatabaseReference) : LiveData<DataSnapshot>() {

    var query: Query = ref

    private var eventListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {
            p0.let { value = it }
        }

    }

    override fun onActive() {
        super.onActive()
        query.addListenerForSingleValueEvent(eventListener)
    }

    override fun onInactive() {
        super.onInactive()
        query.removeEventListener(eventListener)
    }

}