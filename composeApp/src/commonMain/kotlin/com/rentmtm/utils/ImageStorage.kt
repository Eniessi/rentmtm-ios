package com.rentmtm.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.SYSTEM

object ImageStorage {
    suspend fun saveImageToCache(bytes: ByteArray, fileName: String): String {
        return withContext(Dispatchers.Default) {
            val tempDir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY

            val filePath = tempDir.resolve(fileName)

            FileSystem.SYSTEM.write(filePath) {
                write(bytes)
            }

            filePath.toString()
        }
    }
}