package com.catnip.mycoin.ui.coindetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import coil.load
import com.catnip.mycoin.R
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.mycoin.databinding.ActivityCoinDetailBinding
import com.catnip.mycoin.databinding.ActivityCoinListBinding
import com.catnip.mycoin.ui.coinlist.CoinListViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.MenuItem


@AndroidEntryPoint
class CoinDetailActivity : AppCompatActivity(), CoinDetailContract.View {

    private lateinit var binding : ActivityCoinDetailBinding
    private val viewModel : CoinDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeViewModel()
        getDetailData()
    }

    override fun initView() {
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.pbLoading.isVisible = isLoading
    }

    override fun showContent(isContentShown: Boolean) {
        binding.clContent.isVisible = isContentShown
    }

    override fun showErrMsg(isError: Boolean, msg: String?) {
        binding.tvMessage.isVisible = isError
        binding.tvMessage.text = msg
    }

    override fun observeViewModel() {
        viewModel.getCoinDetailLiveData().observe(this) {
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

                    //setData
                    it.data?.let { it1 -> bindData(it1) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showErrMsg(true, it.message)
                }
            }
        }
    }

    override fun getDetailData() {
        val coinID = intent.getStringExtra("CoinID")
        if (coinID != null) {
            viewModel.getCoinDetail(coinID)
        }
    }

    private fun bindData(data : CoinDetailResponse) {
        binding.ivLogo.load(data.image?.thumb)
        binding.tvCoinName.text = data.name
        binding.tvCoinPrice.text = getString(R.string.text_placeholder_coin_price, intent.getStringExtra("CoinPrice"))
        binding.tvCoinSymbol.text = data.symbol
        binding.btnLink.text = data.links?.homepage?.get(0) ?: ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvDescriptions.setText(Html.fromHtml(data.description?.en ?: "", Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.tvDescriptions.setText(Html.fromHtml(data.description?.en ?: ""));
        }

        binding.btnLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.links?.homepage?.get(0)))
            startActivity(browserIntent)
        }


        data.categories?.forEach {
            val chip = layoutInflater.inflate(R.layout.item_chip_category, binding.cgGroups, false) as Chip
            chip.text = (it)
            binding.cgGroups.addView(chip)
        }
    }
}