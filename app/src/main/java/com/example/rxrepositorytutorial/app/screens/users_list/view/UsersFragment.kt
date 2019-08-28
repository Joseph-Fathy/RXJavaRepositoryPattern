package com.example.rxrepositorytutorial.app.screens.users_list.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.rxrepositorytutorial.R
import com.example.rxrepositorytutorial.app.App
import com.example.rxrepositorytutorial.app.BaseFragment
import com.example.rxrepositorytutorial.app.screens.users_list.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.users_fragment.*

class UsersFragment : BaseFragment() {


    private val viewModel = App.injectUsersViewModel()
    private var users: List<User> = emptyList()

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
        response.setOnClickListener {
            getUser()
        }

    }

    override fun onStart() {
        super.onStart()
        getAll()
    }

    private fun getAll() {
        subscribe(
            viewModel.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it?.users?.isNotEmpty()!!) {
                        users = it.users
                        Log.wtf("RxRepo", "MainFragment__Number Users Found  = ${it.users.size} users")
                    } else {
                        Log.wtf("RxRepo", "MainFragment__Error = ${it.throwable?.message}")
                        handleError(it?.throwable)
                    }

                }, {
                    handleError(it)
                })

        )
    }

    private fun getUser(indexOfUserInList: Int = 0) {
        if (users.isNullOrEmpty()) return

        subscribe(
            viewModel.getUserWithId(users[indexOfUserInList]?.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it?.user != null) {
                        Log.wtf("RxRepo", "MainFragment__User Found  = ${it?.user?.userId}")
                    } else {
                        Log.wtf("RxRepo", "MainFragment__Error = ${it?.throwable?.message}")
                        handleError(it?.throwable)
                    }
                }, {
                    handleError(it)
                })
        )
    }

    private fun handleError(throwable: Throwable?) {
        response.text = throwable?.message
    }

    private fun handleLoading(isLoading: Boolean?) {
        progress.visibility = isLoading.let { if (isLoading!!) View.VISIBLE else View.GONE }
    }

    private fun handleResponse(responseString: String) {
        response.text = responseString
    }

}