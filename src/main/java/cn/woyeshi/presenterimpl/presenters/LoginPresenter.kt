package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.BaseResponse
import cn.woyeshi.entity.beans.manager.UserInfo
import cn.woyeshi.presenter.base.BaseSubscriber
import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.IBasePresenter
import cn.woyeshi.presenter.base.IBaseView
import cn.woyeshi.presenterimpl.service.LoginService
import io.reactivex.Flowable

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
        val flowAble: Flowable<BaseResponse<List<UserInfo>>> = loginService.login(userName, password)
        flowAble.subscribe(object : BaseSubscriber<BaseResponse<List<UserInfo>>, List<UserInfo>>(flowAble) {
//            override fun onNext(t: List<UserInfo>) {
//                super.onNext(t)
//                iView.onLoginRequestSuccess(t)
//            }
        })
    }
}



