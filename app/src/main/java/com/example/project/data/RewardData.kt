package com.example.project.data

data class RewardData(
    val rid: String,
    var rewardId: String,
    val quantity: Int,
    val requiredPoints: Int,
    val rewardName: String,
){
    // Add a no-argument constructor
    constructor() : this("","", 0, 0, "") // Modify the default values based on your requirements
}
