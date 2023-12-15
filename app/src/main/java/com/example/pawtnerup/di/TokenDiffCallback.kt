package com.example.pawtnerup.di

import androidx.recyclerview.widget.DiffUtil
import com.example.pawtnerup.data.room.TokenEntity

class TokenDiffCallback(private val oldTokenList: List<TokenEntity>, private val newTokenList: List<TokenEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTokenList.size
    override fun getNewListSize(): Int = newTokenList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTokenList[oldItemPosition].id == newTokenList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldToken = oldTokenList[oldItemPosition]
        val newToken = newTokenList[newItemPosition]
        return oldToken.token == newToken.token
    }
}