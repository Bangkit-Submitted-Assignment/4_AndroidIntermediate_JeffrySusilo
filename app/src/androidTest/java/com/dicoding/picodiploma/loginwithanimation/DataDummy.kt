package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.ListStoryItem

class DataDummy {
    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                "photoUrl $i",
                "createdAt $i",
                "name $i",
                "description $i",
            )
            items.add(quote)
        }
        return items
    }
    val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXdSYU5zcVdMRVVPeFM0ZGoiLCJpYXQiOjE3MDE0MTU4MjF9.ZXHngIfVCksO1OPKe0M6MRB_8zfE0hQDZMwsrE-yYwg"
}