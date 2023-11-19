package com.example.project.functions

//import TransactionManager
import android.content.ContentValues.TAG
import android.util.Log
import com.example.project.data.RewardData
import com.example.project.data.UserRecord
import com.google.common.math.Quantiles
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

// Redeem
fun updateTransactionInFirebase(
    uid: String,
    rewardKey: String,
    requiredPoints: Int,
    redeemedQuantity: Int
) {
    val transactionId = generateUniqueTransactionId()
    val transactionDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    val database = FirebaseDatabase.getInstance()
    val transactionsRef = database.getReference("transactions")

    val transactionRef = transactionsRef.child(transactionId)

    transactionRef.child("transactionId").setValue(transactionId)
    transactionRef.child("userId").setValue(uid)
    transactionRef.child("rewardId").setValue(rewardKey)
    transactionRef.child("requiredPoints").setValue(requiredPoints)
    transactionRef.child("redeemedQuantity").setValue(redeemedQuantity)
    transactionRef.child("transactionDate").setValue(transactionDate)
}


private fun generateUniqueTransactionId(): String {
    val timestamp = Timestamp.now().toDate().time
    val uniqueIdentifier = generateRandomString()
    return "$timestamp-$uniqueIdentifier"
}

private fun generateRandomString(length: Int = 8): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}


//fun displayTransactionsForUser(uid: String) {
//    val transactionManager = TransactionManager()
//
//    transactionManager.getTransactionsForUser(uid) { transactions ->
//        // Now you have a list of transactions for the user
//        // Display them in your UI or perform any other operations
//        for (transaction in transactions) {
//            println("Transaction ID: ${transaction.transactionId}, Date: ${transaction.transactionDate}")
//            // Add code here to display other transaction details
//        }
//    }
//}

