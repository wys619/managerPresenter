package cn.woyeshi.presenterimpl.service

import cn.woyeshi.entity.BaseResponse
import cn.woyeshi.entity.beans.manager.UserInfo
import cn.woyeshi.entity.beans.manager.VerifyCodeInfo
import cn.woyeshi.presenter.base.BaseObservable
import cn.woyeshi.presenter.base.BaseService
import cn.woyeshi.presenter.base.RetrofitUtils
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by wys on 2017/11/8.
 */
interface IUserService {

    @GET("user/")
    fun login(@Query("userName") userName: String, @Query("password") password: String): Flowable<BaseResponse<List<UserInfo>>>

    @GET("verifyCode/")
    fun getVerifyCode(@Query("phone") phone: String): BaseObservable<VerifyCodeInfo>


}

class LoginService : BaseService() {

    private var userService: IUserService = RetrofitUtils.create(IUserService::class.java)

    fun login(userName: String, password: String): Flowable<BaseResponse<List<UserInfo>>> {
        return observe(userService.login(userName, password))
    }


}