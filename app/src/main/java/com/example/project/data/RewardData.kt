package com.example.project.data

data class RewardData(
    val rid: String,
    var rewardId: String,
    val quantity: Int,
    val requiredPoints: Int,
    val rewardName: String,
){
    constructor() : this("","", 0, 0, "")
}
