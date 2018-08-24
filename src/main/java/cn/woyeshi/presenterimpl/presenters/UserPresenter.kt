package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.BaseResponse
import cn.woyeshi.entity.beans.manager.UserInfo
import cn.woyeshi.presenter.base.*
import cn.woyeshi.presenterimpl.R
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by wys on 2017/11/8.
 */
interface IUserView : IBaseView {
    fun onLoginRequestSuccess(loginInfo: UserInfo)
    fun onRegisterSuccess(t: UserInfo)
    fun onUpdateUserSuccess()
}

interface IUserService {

    @GET("user/")
    fun login(@Query("userName") userName: String, @Query("password") password: String): Flowable<BaseResponse<List<UserInfo>>>

    @POST("user/")
    fun register(@Query("userName") phone: String, @Query("password") pwd: String, @Query("code") code: String): Flowable<BaseResponse<UserInfo>>

    @PUT("user/")
    fun updateUser(@Body user: UserInfo): Flowable<BaseResponse<Unit>>
}

class UserPresenter<T : IUserView>(t: T) : BasePresenter<T>(t) {

    private var userService: IUserService = RetrofitUtils.create(IUserService::class.java)

    fun login(userName: String, password: String) {
        val flowAble: Flowable<List<UserInfo>> = observe(userService.login(userName, password))
        flowAble.subscribe(object : BaseSubscriber<List<UserInfo>>(flowAble) {
            override fun onNext(t: List<UserInfo>) {
                if (t.size == 1) {
                    iView.onLoginRequestSuccess(t[0])
                } else {
                    throw BaseException(-1, getActivity()?.getString(R.string.string_login_fail))
                }
            }
        })
    }

    fun register(phone: String, pwd: String, code: String) {
        val flowAble = observe(userService.register(phone, pwd, code))
        flowAble.subscribe(object : BaseSubscriber<UserInfo>(flowAble) {
            override fun onNext(t: UserInfo) {
                iView.onRegisterSuccess(t)
            }
        })
    }

    fun updateUser(user: UserInfo) {
        val flowAble = observe(userService.updateUser(user))
        flowAble.subscribe(object : BaseSubscriber<Unit>(flowAble) {
            override fun onNext(t: Unit) {
                iView.onUpdateUserSuccess()
            }
        })
    }
}



