package com.bogdankostyrko.tfstest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bogdankostyrko.tfstest.databinding.ActivityMainBinding
import com.bogdankostyrko.tfstest.models.ReactionItem

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listEmoji =
            listOf("🤣","😊","🎨","❓","😂","🎰","😒","👌","😘","💕","🤷‍♀️","🤦‍♂️","🤦‍♀️","🙌","👍")
        repeat(2) {
            binding.fbReactions.addReaction(getReaction(listEmoji))
        }
    }

    private fun getReaction(listEmoji: List<String>): ReactionItem = ReactionItem(reaction = listEmoji.random(), (0..300).random())
}