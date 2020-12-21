package com.spiridonova.kotlin108.fileslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.spiridonova.kotlin108.R
import com.spiridonova.kotlin108.data.FileData
import com.spiridonova.kotlin108.data.FileInfo
import com.spiridonova.kotlin108.databinding.FileViewBinding
import java.io.File

class FilesViewHolder(val binding: FileViewBinding, val fragment: Fragment) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(fileData: File?) {
        with(binding) {
            if (fileData != null) {
                fileName.text = fileData.name
                fileName.setOnClickListener{
                    if (fileData.isDirectory) {
                        val bundle = Bundle()
                        bundle.putSerializable("new_root", FileInfo(fileData,false))
                        fragment.findNavController().navigate(R.id.navigation_start, bundle)
                    }
                }
                if (fileData.isDirectory) {
                    editFileButton.visibility = View.INVISIBLE
                    deleteFileButton.visibility = View.INVISIBLE

                } else {
                    editFileButton.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putSerializable("file_data",
                            (fragment as FilesListFragment).filesInternalAPI?.rootFile?.let { it1 ->
                                FileData(
                                    it1,fileData.name, fileData.readText())
                            })
                        fragment.findNavController().navigate(R.id.to_file_edit, bundle)
                    }
                    deleteFileButton.setOnClickListener {
                        (fragment as FilesListFragment).deleteFile(fileData)
                    }
                }

            }
        }
    }
}
