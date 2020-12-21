package com.spiridonova.kotlin108

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.spiridonova.kotlin108.files.FilesExternalAPI
import com.spiridonova.kotlin108.files.WRITE_REQ
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var navController : NavController
    var awaitingText: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = this.findNavController(R.id.main_navigation_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var uri : Uri? = null
            if (data != null) {
                uri = data.data
                if (uri != null) run {
                    when (requestCode) {
                        WRITE_REQ -> {
                            try {
                                val fileDescriptor: ParcelFileDescriptor? =
                                    contentResolver.openFileDescriptor(uri, "w")
                                val fileOutputStream =
                                    FileOutputStream(fileDescriptor?.fileDescriptor)
                                fileOutputStream.write(awaitingText.toByteArray())
                                fileOutputStream.close()
                                fileDescriptor?.close()
                            } catch (e:Exception) {

                            }

                        }
                        else -> {
                            Log.d("aaaa", "unknown_req")

                        }
                    }
                }
            }
        }
        awaitingText = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}