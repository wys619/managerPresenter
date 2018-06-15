package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.beans.manager.LoginInfo
import cn.woyeshi.presenter.base.BaseService
import cn.woyeshi.presenter.base.RetrofitUtils
import cn.woyeshi.presenterimpl.iViews.ILoginView
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by wys on 2017/11/8.
 */
interface ILoginService {

    @GET("user/")
    fun login(@Query("userName") userName: String, @Query("password") password: String): Observable<LoginInfo>

}

class LoginService<V : ILoginView> constructor(v: V) : BaseService<V>(v) {

    private var loginService: ILoginService = RetrofitUtils.create(ILoginService::class.java)

    fun login(userName: String, password: String): Observable<LoginInfo> {
        return observe(loginService.login(userName, password))
    }

}