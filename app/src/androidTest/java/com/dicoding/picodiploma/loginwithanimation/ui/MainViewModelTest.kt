package com.dicoding.picodiploma.loginwithanimation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.picodiploma.loginwithanimation.DataDummy
import com.dicoding.picodiploma.loginwithanimation.MainDispatcherRule
import com.dicoding.picodiploma.loginwithanimation.data.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.getOrAwaitValue
import com.dicoding.picodiploma.loginwithanimation.view.main.MainAdapter
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var quoteRepository: UserRepository

//    private val token = DataDummy().token

    @Test
    fun whenGetQuoteShouldNotNullandReturnData() = runTest {
        val dummyQuote = DataDummy().generateDummyQuoteResponse()
//        val data: PagingData<QuoteResponseItem> = PagingData.from(dummyQuote)
        val data: PagingData<ListStoryItem> = QuotePagingSource.snapshot(dummyQuote)
        val expectedQuote = MutableStateFlow<PagingData<ListStoryItem>>(data)
        expectedQuote.value = data
        Mockito.`when`(quoteRepository.getStoryPager()).thenReturn(expectedQuote)

        val mainViewModel = MainViewModel(quoteRepository)
        val actualQuote: PagingData<ListStoryItem> = mainViewModel.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = MainAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyQuote.size, differ.snapshot().size)
        Assert.assertEquals(dummyQuote[0], differ.snapshot()[0])
    }

    @Test
    fun whenGetQuoteEmptyShouldReturnNoData() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedQuote = MutableStateFlow<PagingData<ListStoryItem>>(data)
        expectedQuote.value = data
        Mockito.`when`(quoteRepository.getStoryPager()).thenReturn(expectedQuote)

        val mainViewModel = MainViewModel(quoteRepository)
        val actualQuote: PagingData<ListStoryItem> = mainViewModel.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = MainAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertEquals(0, differ.snapshot().size)
    }
}

class QuotePagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}