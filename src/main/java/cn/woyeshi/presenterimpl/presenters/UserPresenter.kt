package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.beans.manager.LoginInfo
import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.IBasePresenter
import cn.woyeshi.presenter.base.IBaseView
import io.reactivex.observers.DisposableObserver

/**
 * Created by wys on 2017/11/8.
 */
interface IUserView : IBaseView {
    fun onLoginRequestSuccess(loginInfo: LoginInfo)
    fun onLoginRequestError(e: Throwable)
}

interface IUserPresenter<T : IUserView> : IBasePresenter<T> {
    fun login(userName: String, password: String)
}

class UserPresenter<T : IUserView>(t: T) : BasePresenter<T>(t), IUserPresenter<T> {

    private val loginService = LoginService()

    override fun login(userName: String, password: String) {
        addSubscription(loginService.login(userName, password)
                .subscribeWith(object : DisposableObserver<LoginInfo>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: LoginInfo) {
                        iView.onLoginRequestSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        iView.onLoginRequestError(e)
                    }
                }))
    }
}



