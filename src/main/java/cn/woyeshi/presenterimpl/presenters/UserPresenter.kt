package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.beans.manager.LoginInfo
import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.IBasePresenter
import cn.woyeshi.presenter.base.IBaseView
import io.reactivex.observers.DisposableObserver


//------------------------------------------ IViews ------------------

/**
 * Created by wys on 2017/11/8.
 */
interface ILoginView : IBaseView {
    fun onLoginRequestSuccess(loginInfo: LoginInfo)
    fun onLoginRequestError(e: Throwable)
}

interface IRegisterView : IBaseView {

}


//------------------------------------------ IPresenters ------------------

interface ILoginPresenter<T : ILoginView> : IBasePresenter<T> {
    fun login(userName: String, password: String)
}

interface IRegisterPresenter<T : IRegisterView> : IBasePresenter<T> {
    fun getVerifyCode(phone: String)
    fun register(phone: String, pwd: String, code: String)
}


//------------------------------------------- Presenters ------------------

class LoginPresenter<T : ILoginView>(t: T) : BasePresenter<T>(t), ILoginPresenter<T> {

    private val loginService = LoginService()

    override fun login(userName: String, password: String) {
        addSubscription(
                loginService.login(userName, password)
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
                        })
        )
    }
}

class RegisterPresenter<T : IRegisterView>(t: T) : BasePresenter<T>(t), IRegisterPresenter<T> {
    override fun getVerifyCode(phone: String) {


    }

    override fun register(phone: String, pwd: String, code: String) {


    }

}



