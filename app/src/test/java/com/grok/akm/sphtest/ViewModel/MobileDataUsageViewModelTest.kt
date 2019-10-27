package com.grok.akm.sphtest.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.grok.akm.sphtest.MainCoroutineRule
import com.grok.akm.sphtest.api.Status
import com.grok.akm.sphtest.model.ApiResponse
import com.grok.akm.sphtest.repository.Repository
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MobileDataUsageViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: Repository

    @InjectMocks
    lateinit var viewModel: MobileDataUsageViewModel

    @Mock
    lateinit var observer: Observer<ApiResponse>

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass(){
            RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline()}
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }


    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        viewModel.getMobileDataUsageLiveData().observeForever(observer)
    }

    @Test
    fun testNull() {

        Assert.assertNotNull(viewModel.getMobileDataUsageLiveData())
        Assert.assertTrue(viewModel.getMobileDataUsageLiveData().hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {

        viewModel.loadMobileDataUsage()
        viewModel.getMobileDataUsageLiveData().observeForever{
            Assert.assertEquals(
                Status.LOADING,
                it.status
            )
        }
        viewModel.getMobileDataUsageLiveData().observeForever{
            Assert.assertEquals(
                Status.SUCCESS,
                it.status
            )
        }
    }

    @Test
    fun testApiFetchDataError() {

        viewModel.loadMobileDataUsage()

        viewModel.getMobileDataUsageLiveData().observeForever{
            Assert.assertEquals(
                Status.LOADING,
                it.status
            )
        }
        viewModel.getMobileDataUsageLiveData().observeForever{
            Assert.assertEquals(
                Status.ERROR,
                it.status
            )
        }
    }

}