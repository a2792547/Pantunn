package com.tunaskelapa.pantunn.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        binding.etInput1.text = pantun.bait1
        binding.etInput2.text = pantun.bait2
        binding.etInput3.text = pantun.bait3
        binding.etInput4.text = pantun.bait4
    }
}