package com.spiridonova.kotlin108.files

import android.content.Intent
import androidx.fragment.app.Fragment
import java.io.File

class FilesExternalAPI(val fragment: Fragment) {
    private var files: ArrayList<File>? = null
    private var currDir: String = ""

    fun createFile(filename: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION and Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        intent.putExtra(Intent.EXTRA_TITLE, "${filename}.txt")
        fragment.requireActivity().startActivityForResult(intent, WRITE_REQ)
    }



}