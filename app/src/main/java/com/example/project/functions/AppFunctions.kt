package com.example.project.functions

import android.content.ContentValues.TAG
import android.util.Log
import com.example.project.data.RewardData
import com.example.project.data.UserRecord
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//@Composable
fun getUserDataFromFirebase(uid: String, onResult: (UserRecord?) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val userRecord = snapshot.getValue(UserRecord::class.java)
            onResult(userRecord)
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle error
            onResult(null)
        }
    })
}

fun getRewardsDataFromFirebase(onResult: (List<RewardData>) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val rewardsRef = database.getReference("rewards")

    rewardsRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val rewardsList = mutableListOf<RewardData>()

            for (rewardSnapshot in snapshot.children) {
                val reward = rewardSnapshot.getValue(RewardData::class.java)
                reward?.let {
                    rewardsList.add(it)
                }
            }

            onResult(rewardsList)
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle error
            onResult(emptyList()) // or null, depending on your error handling strategy
        }
    })
}

//fun updateAccumulatedPointsInFirebase(uid: String, newPoints: Int) {
//    val userReference = FirebaseFirestore.getInstance().collection("users").document(uid)
//
//    Log.d(TAG, "Updating user data. UID: $uid, New Points: $newPoints")
//
//    userReference.update("accumulatedPoints", newPoints)
//        .addOnSuccessListener {
//            // Update successful
//            Log.d(TAG, "User data updated successfully")
//        }
//        .addOnFailureListener { e ->
//            // Handle errors
//            Log.e(TAG, "Error updating user data", e)
//        }
//}

//fun updateAccumulatedPointsInFirebase(uid: String, newPoints: Int, onComplete: (Boolean) -> Unit) {
//    val db = FirebaseFirestore.getInstance()
//    val data = hashMapOf("points" to newPoints)
//
//    db.collection("users").document(uid).set(data)
//        .addOnSuccessListener {
//            Log.d(TAG, "User data updated successfully")
//            onComplete(true) // Notify success
//        }
//        .addOnFailureListener { e ->
//            Log.e(TAG, "Error updating user data", e)
//            onComplete(false) // Notify failure
//        }
//}