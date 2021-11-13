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


@AndroidEntryPoint
class CoinDetailActivity : AppCompatActivity(), CoinDetailContract.View {

    private lateinit var binding : ActivityCoinDetailBinding
    private val viewModel : CoinDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(true);
        initView()
        observeViewModel()
        getDetailData()
    }

    override fun initView() {
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.tvDescriptions.text = data.description?.en ?: ""


        data.categories?.forEach {
            var chip = layoutInflater.inflate(R.layout.item_chip_category, binding.cgGroups, false) as Chip
            chip.text = (it)
            binding.cgGroups.addView(chip)
        }
    }
}