package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.beans.manager.UserInfo
import cn.woyeshi.presenter.base.*
import cn.woyeshi.presenterimpl.service.LoginService
import io.reactivex.Flowable

/**
 * Created by wys on 2017/11/8.
 */
interface ILoginView : IBaseView {
    fun onLoginRequestSuccess(loginInfo: UserInfo)
}

interface ILoginPresenter<T : ILoginView> : IBasePresenter<T> {
    fun login(userName: String, password: String)
}

class LoginPresenter<T : ILoginView>(t: T) : BasePresenter<T>(t), ILoginPresenter<T> {

    private val loginService = LoginService()

    override fun login(userName: String, password: String) {
        val flowAble: Flowable<List<UserInfo>> = loginService.login(userName, password)
        flowAble.subscribe(object : BaseSubscriber<List<UserInfo>>(flowAble) {
            override fun onNext(t: List<UserInfo>) {
                if (t.size == 1) {
                    iView.onLoginRequestSuccess(t[0])
                } else {
                    throw BaseException(-1, "登录失败")
                }
            }
        })
    }
}



