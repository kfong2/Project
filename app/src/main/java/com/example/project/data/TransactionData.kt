package com.example.project.data

data class TransactionData(
    val transactionId: String?,
    val uid: String?,
    val rewardKey: String?,
    val rewardId: String?,
    val rewardName: String?,
    val requiredPoints: Int?,
    val redeemedQuantity: Int?,
    val transactionDate: String?
){
    // Empty constructor required by Firebase
    constructor() : this("", "", "", "", "",0, 0, "")
}
