package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.beans.manager.LoginResult
import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.IBasePresenter
import cn.woyeshi.presenter.base.RetrofitUtils
import cn.woyeshi.presenterimpl.iViews.ILoginView
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by wys on 2017/11/8.
 */
interface ILoginPresenter : IBasePresenter {

    fun login(userName: String, password: String): Flowable<LoginResult>

}

interface LoginService {

    @GET("user/")
    fun login(@QueryMap params: HashMap<String, String>): Flowable<LoginResult>

}

class LoginPresenter<V : ILoginView> constructor(v: V) : BasePresenter<V>(v), ILoginPresenter {

    private var loginService: LoginService = RetrofitUtils.create(LoginService::class.java)

    override fun login(userName: String, password: String): Flowable<LoginResult> {
        val map: HashMap<String, String> = HashMap()
        map["userName"] = userName
        map["password"] = password
        return observe(loginService.login(map))
    }

}