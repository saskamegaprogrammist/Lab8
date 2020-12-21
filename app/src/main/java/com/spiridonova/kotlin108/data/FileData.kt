package com.spiridonova.kotlin108.data

import java.io.File
import java.io.Serializable

data class FileData(public var rootDir: File, public var name: String, public var text: String): Serializable