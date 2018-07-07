package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.BaseResponse
import cn.woyeshi.entity.beans.manager.UserInfo
import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.BaseSubscriber
import cn.woyeshi.presenter.base.IBaseView
import cn.woyeshi.presenter.base.RetrofitUtils
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IRegisterView : IBaseView {
    fun onVerifyCodeGetSuccess()
    fun onRegisterSuccess(t: UserInfo)
}

interface IRegisterService {

    @GET("code/")
    fun getVerifyCode(@Query("phone") phone: String, @Query("type") type: String): Flowable<BaseResponse<Unit>>

    @POST("user/")
    fun register(@Query("userName") phone: String, @Query("password") pwd: String, @Query("code") code: String): Flowable<BaseResponse<UserInfo>>

}

class RegisterPresenter<T : IRegisterView>(t: T) : BasePresenter<T>(t) {

    private val registerService = RetrofitUtils.create(IRegisterService::class.java)


    fun getVerifyCode(phone: String, type: String) {
        val flowAble = observe(registerService.getVerifyCode(phone, type))
        flowAble.subscribe(object : BaseSubscriber<Unit>(flowAble) {
            override fun onNext(t: Unit) {
                iView.onVerifyCodeGetSuccess()
            }
        })
    }

    fun register(phone: String, pwd: String, code: String) {
        val flowAble = observe(registerService.register(phone, pwd, code))
        flowAble.subscribe(object : BaseSubscriber<UserInfo>(flowAble) {
            override fun onNext(t: UserInfo) {
                iView.onRegisterSuccess(t)
            }
        })
    }

}