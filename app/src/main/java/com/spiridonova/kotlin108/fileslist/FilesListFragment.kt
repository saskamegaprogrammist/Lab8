package com.spiridonova.kotlin108.fileslist

import android.app.Activity
import android.content.Context.MODE_APPEND
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spiridonova.kotlin108.MainActivity
import com.spiridonova.kotlin108.R
import com.spiridonova.kotlin108.data.FileInfo
import com.spiridonova.kotlin108.databinding.FilesListFragmentBinding
import com.spiridonova.kotlin108.files.FilesExternalAPI
import com.spiridonova.kotlin108.files.FilesInternalAPI
import java.io.File

class FilesListFragment : Fragment() {
    private var data: Array <File>? = null
    var filesInternalAPI : FilesInternalAPI? = null
    private var filesListDataAdapter: FilesListDataAdapter? = null
    private var recyclerView: RecyclerView? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (savedInstanceState != null) {
            data = savedInstanceState.getSerializable("data") as Array <File>?
        }

        val arguments = this.arguments
        val fileInfo: FileInfo? = arguments?.getSerializable("new_root") as FileInfo?
        if (fileInfo != null) {
            filesInternalAPI = FilesInternalAPI(fileInfo.rootDir)
        } else {
            val f: File? = requireActivity().getExternalFilesDir(null)
//        val f: File? = requireActivity().applicationContext.getE
//        val f: File? = Environment.getRootDirectory()
//        val f: File? = Environment.getDataDirectory()
//        val f: File? = Environment.getStorageDirectory()
//        MediaStore.Downloads.
//        val f: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            f?.absolutePath?.let { Log.d("aaa", it) }
            Log.d("aaaaa", f?.listFiles()?.size.toString())
            filesInternalAPI = f?.let { FilesInternalAPI(it) }
        }

        val supportActionBar =  (activity as? AppCompatActivity)?.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FilesListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.files_list_fragment, container, false
        )
        val view: View = binding.root

        recyclerView = binding.list
        data = filesInternalAPI?.getFiles()

        filesListDataAdapter = FilesListDataAdapter(this, (data)!!)
        recyclerView!!.adapter = filesListDataAdapter

        binding.createFile.setOnClickListener {
            run {
                createFile()
            }
        }

        binding.createDir.setOnClickListener {
            run {
                createDir()
            }
        }

        binding.goUp.setOnClickListener {
            run {
                goUp()
            }
        }

        val spanCount = resources.getInteger(R.integer.spanCount)
        val layoutManager = GridLayoutManager(activity, spanCount)
        recyclerView!!.layoutManager = layoutManager
        return view
    }

    fun createFile() {
        val bundle = Bundle()
        bundle.putSerializable("directory_info", filesInternalAPI?.rootFile?.let { FileInfo(it,true) })
        findNavController().navigate(R.id.to_file_edit, bundle)
    }


    fun createDir() {
        val bundle = Bundle()
        bundle.putSerializable("directory_info", filesInternalAPI?.rootFile?.let { FileInfo(it,false) })
        findNavController().navigate(R.id.to_file_edit, bundle)
    }

    fun goUp() {
        val f: File? = requireActivity().getExternalFilesDir(null)
        if (filesInternalAPI?.rootFile?.absolutePath != f?.absolutePath) {
            filesInternalAPI?.rootFile?.parentFile?.let { filesInternalAPI?.setRootDir(it) }
            data = filesInternalAPI?.getFiles()

            filesListDataAdapter = FilesListDataAdapter(this, (data)!!)
            recyclerView!!.adapter = filesListDataAdapter
        }
    }

    fun changeDir(newDir: File) {
        filesInternalAPI?.setRootDir(newDir)
        data = filesInternalAPI?.getFiles()

        filesListDataAdapter = FilesListDataAdapter(this, (data)!!)
        recyclerView!!.adapter = filesListDataAdapter
    }

    fun deleteFile(file: File) {
        filesInternalAPI?.deleteFile(file)
        data = filesInternalAPI?.getFiles()

        filesListDataAdapter = FilesListDataAdapter(this, (data)!!)
        recyclerView!!.adapter = filesListDataAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.project_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                data = filesInternalAPI?.getFiles()

                filesListDataAdapter = FilesListDataAdapter(this, (data)!!)
                recyclerView!!.adapter = filesListDataAdapter
                true
            }

            R.id.menu_home -> {
                val f: File? = requireActivity().getExternalFilesDir(null)
                filesInternalAPI = f?.let { FilesInternalAPI(it) }
                data = filesInternalAPI?.getFiles()

                filesListDataAdapter = FilesListDataAdapter(this, (data)!!)
                recyclerView!!.adapter = filesListDataAdapter
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
