package com.example.project.functions

import android.content.ContentValues.TAG
import android.util.Log
import com.example.project.data.RewardData
import com.example.project.data.UserRecord
import com.google.common.math.Quantiles
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
        }
    })
}

// Redeem
fun getRewardsDataFromFirebaseWithKeys(onDataFetched: (List<Pair<String, RewardData>>) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val rewardsRef = database.getReference("rewards")

    rewardsRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val rewardsList = mutableListOf<Pair<String, RewardData>>()

            for (childSnapshot in snapshot.children) {
                val rewardId = childSnapshot.key
                val rewardData = childSnapshot.getValue(RewardData::class.java)
                if (rewardId != null && rewardData != null) {
                    rewardsList.add(rewardId to rewardData)
                }
            }
            onDataFetched(rewardsList)
        }

        override fun onCancelled(error: DatabaseError) {
        }
    })
}


// Redeem
fun updateAccumulatedPoints(uid: String, newPoints: Int, onUpdatePoints: () -> Unit) {
    val newPoints = newPoints

    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    val uid = uid
    val userPath = uid
    val userRef = usersRef.child(userPath)

    // Update the "accumulatedPoints" field
    userRef.child("accumulatedPoints").setValue(newPoints)
        .addOnSuccessListener {
            Log.d(TAG, "User data updated successfully with newPoints")
            onUpdatePoints.invoke()
        }
        .addOnFailureListener { e ->
            Log.e(TAG, "Error updating user data with newPoints", e)
        }
}

fun updateRewardQuantity(rewardKey: String, newQuantity: Int, onUpdatePoints: () -> Unit) {
    val newQuantity = newQuantity

    val database = FirebaseDatabase.getInstance()
    val rewardsRef = database.getReference("rewards")

    val rewardRef = rewardsRef.child(rewardKey)

    rewardRef.child("quantity").setValue(newQuantity)
        .addOnSuccessListener {
            Log.d(TAG, "User data updated successfully with newQuantity")
            onUpdatePoints.invoke()
        }
        .addOnFailureListener { e ->
            Log.e(TAG, "Error updating user data with newQuantity", e)
        }
}