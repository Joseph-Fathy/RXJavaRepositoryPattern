package com.example.rxrepositorytutorial.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.rxrepositorytutorial.App
import com.example.rxrepositorytutorial.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.users_fragment.*

class MainFragment : BaseFragment() {


    private val viewModel = App.injectUsersViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.users_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadingLiveData.observe(this, Observer {
            handleLoading(it)
        })

        viewModel.responseLiveData.observe(this, Observer { it?.let { it1 -> handleResponse(it1) } })
        progress.visibility = View.GONE
        button.setOnClickListener {
            start()
        }

    }

    override fun onStart() {
        super.onStart()
    }

    private fun start(){
        subscribe(
            viewModel.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.users != null)
                        Log.wtf("RXRepo", "MainFragment__Number Users Found  = ${it.users.size} users")
                    else
                        Log.wtf("RXRepo", "MainFragment__Error = ${it.throwable?.message}")
                }, {
                    handleError(it)
                })

        )
    }

    private fun handleError(throwable: Throwable?) {
        response.text = throwable?.message
        handleLoading(false)
    }

    private fun handleLoading(isLoading: Boolean?) {
        progress.visibility = isLoading.let { if (isLoading!!) View.VISIBLE else View.GONE }
    }

    private fun handleResponse(responseString: String) {
        response.text = responseString
    }

}