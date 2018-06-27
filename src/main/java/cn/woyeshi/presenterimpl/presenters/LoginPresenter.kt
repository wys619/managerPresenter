package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.BaseResponse
import cn.woyeshi.entity.beans.manager.UserInfo
import cn.woyeshi.presenter.base.BaseDisposableObserver
import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.IBasePresenter
import cn.woyeshi.presenter.base.IBaseView
import cn.woyeshi.presenterimpl.service.LoginService
import io.reactivex.Observable

/**
 * Created by wys on 2017/11/8.
 */
interface ILoginView : IBaseView {
    fun onLoginRequestSuccess(loginInfo: List<UserInfo>)
    fun onLoginRequestError(e: Throwable)
}

interface ILoginPresenter<T : ILoginView> : IBasePresenter<T> {
    fun login(userName: String, password: String)
}

class LoginPresenter<T : ILoginView>(t: T) : BasePresenter<T>(t), ILoginPresenter<T> {

    private val loginService = LoginService()

    override fun login(userName: String, password: String) {
        val observer: Observable<List<UserInfo>> = loginService.login(userName, password)
        val ss = observer.subscribeWith(object : BaseDisposableObserver<List<UserInfo>>(observer) {
            override fun onNext(t: List<UserInfo>) {
                super.onNext(t)
                iView.onLoginRequestSuccess(t)
            }
        })
        addSubscription(ss)
    }
}



