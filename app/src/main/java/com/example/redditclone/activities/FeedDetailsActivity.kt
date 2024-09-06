package com.example.redditclone.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redditclone.databinding.ActivityFeedDetailsBinding

class FeedDetailsActivity : AppCompatActivity() {

    // Declarando o binding
    private lateinit var binding: ActivityFeedDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializando o binding com o layout
        binding = ActivityFeedDetailsBinding.inflate(layoutInflater)

        // Definindo o conteúdo da view como o root da binding
        setContentView(binding.root)

        binding.closeFdBtn.setOnClickListener {
            finish()
        }
    }
}
