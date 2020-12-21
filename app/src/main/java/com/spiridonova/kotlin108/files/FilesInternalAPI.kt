package com.spiridonova.kotlin108.files

import android.util.Log
import java.io.File
import java.io.FileOutputStream

class FilesInternalAPI(var rootFile: File) {
    private var files: ArrayList<File>? = null
    private var currDir: String = ""

    fun setRootDir(rootFile: File) {
        this.rootFile = rootFile
    }

    fun deleteFile(file: File) {
        if(file.exists())
            file.delete();
    }

    fun createFile(filename: String): File {
        return this.createDir("${filename}.txt")
    }

    fun writeToFile(filename: String, text: String) {
        val outFile: File = File(rootFile, filename)
        val out : FileOutputStream = FileOutputStream(outFile)
        val bytes = text.toByteArray()
        out.write(bytes)
    }

    fun writeToFileTxt(filename: String, text: String) {
        this.writeToFile("${filename}.txt", text)
    }

    fun createDir(filename: String): File {
        var file = File(rootFile, filename)
        if (!file.exists()) {
            Log.d("filereader", "Failed to find file: " + filename)
        }
        return file
    }

    fun mkDir(filename: String) {
        var file = File(rootFile, filename)
        file.mkdir()
    }

    fun getFiles() : Array<File>? {
        return rootFile.listFiles()
    }


}