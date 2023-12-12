package com.example.project.data

data class MyRewardData(
    val myRewardId: String,
    var rewardId: String,
    val rewardName: String,
    val transactionDate: String?,
    val expiryDate: String?,
    var rewardStatus: String,
    val uid: String
){
    constructor() : this("","", "", "", "", "", "")
}
