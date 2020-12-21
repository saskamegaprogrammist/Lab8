package com.spiridonova.kotlin108

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spiridonova.kotlin108.data.FileData
import com.spiridonova.kotlin108.data.FileInfo
import com.spiridonova.kotlin108.databinding.FileEditFragmentBinding
import com.spiridonova.kotlin108.files.FilesExternalAPI
import com.spiridonova.kotlin108.files.FilesInternalAPI

class FileEditFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val supportActionBar =  (activity as? AppCompatActivity)?.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FileEditFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.file_edit_fragment, container, false)
        val arguments = this.arguments
        val fileInfo: FileInfo? = arguments?.getSerializable("directory_info") as FileInfo?
        if (fileInfo != null) {
            if (fileInfo?.type == false) {
                binding.textEdit.visibility = View.INVISIBLE
                binding.savePublic.visibility = View.INVISIBLE
            }
            val fileInternalApi: FilesInternalAPI? = fileInfo?.rootDir?.let { FilesInternalAPI(it) }
            binding.createDir.setOnClickListener {
                run {
                    if (fileInfo?.type != false) {
                        fileInternalApi?.createFile(binding.filenameEdit.editableText.toString())
                        fileInternalApi?.writeToFileTxt(binding.filenameEdit.editableText.toString(), binding.textEdit.editableText.toString())
                    } else {
                        fileInternalApi?.createDir(binding.filenameEdit.editableText.toString())
                        fileInternalApi?.mkDir(binding.filenameEdit.editableText.toString())
                    }
                    findNavController().navigate(R.id.to_start)
                }
            }
            if (fileInfo?.type != false) {
                binding.savePublic.setOnClickListener {
                    run {
                        var filesExternalAPI: FilesExternalAPI? = FilesExternalAPI(this)
                        (requireActivity() as MainActivity).awaitingText =
                            binding.textEdit.editableText.toString()
                        filesExternalAPI?.createFile(binding.filenameEdit.editableText.toString())
                        findNavController().navigate(R.id.to_start)
                    }
                }
            }
        }
        val fileData: FileData? = arguments?.getSerializable("file_data") as FileData?
        if (fileData != null) {
            binding.savePublic.visibility = View.INVISIBLE
            binding.textEdit.setText(fileData?.text)
            binding.filenameEdit.setText(fileData?.name)
            binding.filenameEdit.isEnabled = false
            val fileInternalApi: FilesInternalAPI? = fileData?.rootDir?.let { FilesInternalAPI(it) }
            binding.createDir.setOnClickListener {
                run {
                        fileInternalApi?.writeToFile(fileData?.name, binding.textEdit.editableText.toString())
                    findNavController().navigate(R.id.to_start)
                }
            }
        }

        return binding.root
    }
}