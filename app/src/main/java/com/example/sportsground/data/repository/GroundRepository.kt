package com.example.sportsground.data.repository

import com.example.sportsground.data.GroundData
import com.example.sportsground.data.GroundModel
import com.example.sportsground.data.ResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GroundRepository {
    suspend fun getAllGround(): ResultResponse<List<GroundModel>>
}

class GroundRepositoryImpl : GroundRepository {
    override suspend fun getAllGround(): ResultResponse<List<GroundModel>> {
        return withContext(Dispatchers.IO) {
            val data = GroundData
            if (data.isEmpty()) {
                ResultResponse.Error(IllegalArgumentException("Post not found"))
            } else {
                ResultResponse.Success(data)
            }
        }
    }

}


