package com.tunaskelapa.pantunn.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tunaskelapa.pantunn.R
import com.tunaskelapa.pantunn.data.DataParcel
import com.tunaskelapa.pantunn.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PANTUN = "extra_pantun"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pantun = intent.getParcelableExtra<DataParcel>(EXTRA_PANTUN) as DataParcel
        binding.result.text = pantun.genre
        binding.tvPantunDetail.text = getString(R.string.tv_pantun_details, pantun.bait1, pantun.bait2, pantun.bait3, pantun.bait4)

    }
}