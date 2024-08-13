package com.example.suitmediatest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmediatest.API.ApiClient
import com.example.suitmediatest.User.User
import com.example.suitmediatest.User.UserAdapter
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var emptyStateText: TextView

    private var users = mutableListOf<User>()
    private var currentPage = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        emptyStateText = findViewById(R.id.emptyStateText)

        adapter = UserAdapter(users) { user ->
            val intent = Intent()
            intent.putExtra("SELECTED_USER_NAME", "${user.first_name} ${user.last_name}")
            setResult(RESULT_OK, intent)
            finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            loadUsers()
        }

        loadUsers()

        // Pagination
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == users.size - 1 && currentPage < totalPages) {
                    currentPage++
                    loadUsers()
                }
            }
        })
    }

    private fun loadUsers() {
        swipeRefreshLayout.isRefreshing = true
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getUsers(currentPage, 10)
                if (currentPage == 1) {
                    users.clear()
                }
                users.addAll(response.data)
                adapter.notifyDataSetChanged()
                emptyStateText.visibility = if (users.isEmpty()) View.VISIBLE else View.GONE
                totalPages = response.total_pages
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}
