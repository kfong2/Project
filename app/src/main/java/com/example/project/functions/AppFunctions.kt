package com.example.project.functions

import com.example.project.data.RewardData
import com.example.project.data.UserRecord
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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