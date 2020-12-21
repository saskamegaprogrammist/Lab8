package com.spiridonova.kotlin108.data

import java.io.File
import java.io.Serializable

data class FileInfo(public var rootDir: File, public var type:Boolean): Serializable