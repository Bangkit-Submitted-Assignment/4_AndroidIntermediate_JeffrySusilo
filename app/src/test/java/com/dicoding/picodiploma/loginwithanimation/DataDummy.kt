package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = mutableListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                name = "name: $i",
                description = "description: $i",
                photoUrl = "photoUrl: $i",
                createdAt = "createdAt: $i"
            )
            items.add(story)
        }
        return items
    }
    val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXdSYU5zcVdMRVVPeFM0ZGoiLCJpYXQiOjE3MDE0NDcxMTB9.wEPp5CjPEF-bS7sdgpPh6vxw-2vYaexn2k0PrD3_GMY"
}