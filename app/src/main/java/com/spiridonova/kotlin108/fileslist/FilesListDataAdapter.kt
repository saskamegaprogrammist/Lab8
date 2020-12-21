package com.spiridonova.kotlin108.fileslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.spiridonova.kotlin108.data.FileInfo
import com.spiridonova.kotlin108.databinding.FileViewBinding
import java.io.File

class FilesListDataAdapter(fragment: Fragment?, data: Array<File>?) :
    RecyclerView.Adapter<FilesViewHolder>() {
    private var files: Array<File>? = data
    private val fragment: Fragment = fragment as FilesListFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FileViewBinding = FileViewBinding.inflate(layoutInflater, parent, false)
        return FilesViewHolder(binding, fragment)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) = holder.bind(files?.get(position))

    override fun getItemCount(): Int {
        if (files == null) {
            return 0
        } else {
            return files!!.size
        }

    }
}
