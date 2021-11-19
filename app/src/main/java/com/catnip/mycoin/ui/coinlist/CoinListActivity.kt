package com.catnip.mycoin.ui.coinlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.catnip.mycoin.R
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.local.SessionPreference
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.databinding.ActivityCoinListBinding
import com.catnip.mycoin.ui.coindetail.CoinDetailActivity
import com.catnip.mycoin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


//needed for dependency injection
@AndroidEntryPoint
class CoinListActivity : AppCompatActivity(), CoinListContract.View {

    private lateinit var binding : ActivityCoinListBinding
    private val viewModel : CoinListViewModel by viewModels()
    private lateinit var adapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        observeViewModel()
        getData()
    }

    override fun getData() {
        viewModel.getCoinList()
    }

    override fun initView() {
        binding = ActivityCoinListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSwipeRefresh()
        initList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.pbLoading.isVisible = isLoading
    }

    override fun showContent(isContentShown: Boolean) {
        binding.rvContent.isVisible = isContentShown
    }

    override fun showErrMsg(isError: Boolean, msg: String?) {
        binding.tvMessage.isVisible = isError
        binding.tvMessage.text = msg
    }

    override fun observeViewModel() {
        viewModel.getCoinListLiveData().observe(this) {
            when(it) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showErrMsg(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showErrMsg(false, null)
                    setData(it.data)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showErrMsg(true, it.message)
                }
            }
        }
    }

    override fun initList() {
        adapter = CoinListAdapter {

            val intent = Intent(this, CoinDetailActivity::class.java)
            intent.putExtra("CoinID", it.id)
            intent.putExtra("CoinPrice", it.currentPrice.toString())
            startActivity(intent)
        }
        binding.rvContent.apply {
            adapter = this@CoinListActivity.adapter
            layoutManager = LinearLayoutManager(this@CoinListActivity)
        }
    }



    override fun initSwipeRefresh() {
        binding.srlContent.setOnRefreshListener {
            binding.srlContent.isRefreshing = false
            getData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home -> {
                finish()
            }
            R.id.overflowMenu -> {
                SessionPreference(this).deleteSession()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setData(data: List<Coin>?) {
        if (data != null) {
            adapter.setItems(data)
        }
    }
}