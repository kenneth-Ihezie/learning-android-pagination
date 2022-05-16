package com.example.learningandroidpagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningandroidpagination.viewmodel.RemoteViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var rvDoggoRemote: RecyclerView
    lateinit var remoteViewModel: RemoteViewModel
    lateinit var adapter: RemoteDoggoImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMembers()
        setUpViews()
        fetchDoggoImages()
    }


    private fun fetchDoggoImages() {
        lifecycleScope.launch {
            remoteViewModel.fetchDoggoImages().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initMembers() {
        remoteViewModel = defaultViewModelProviderFactory.create(RemoteViewModel::class.java)
        adapter = RemoteDoggoImageAdapter()
    }

    private fun setUpViews() {
        rvDoggoRemote = findViewById(R.id.rvDoggoRemote)
        rvDoggoRemote.layoutManager = GridLayoutManager(this, 2)
        rvDoggoRemote.adapter = adapter
    }
}