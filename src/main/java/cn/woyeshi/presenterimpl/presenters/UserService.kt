package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.beans.manager.LoginInfo
import cn.woyeshi.entity.beans.manager.VerifyCodeInfo
import cn.woyeshi.presenter.base.BaseService
import cn.woyeshi.presenter.base.RetrofitUtils
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by wys on 2017/11/8.
 */
interface IUserService {

    @GET("user/")
    fun login(@Query("userName") userName: String, @Query("password") password: String): Observable<LoginInfo>

    @GET("verifyCode/")
    fun getVerifyCode(@Query("phone") phone: String): Observable<VerifyCodeInfo>


}

class LoginService : BaseService() {

    private var userService: IUserService = RetrofitUtils.create(IUserService::class.java)

    fun login(userName: String, password: String): Observable<LoginInfo> {
        return observe(userService.login(userName, password))
    }



}