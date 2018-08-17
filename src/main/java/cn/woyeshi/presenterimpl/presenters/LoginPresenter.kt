package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.entity.BaseResponse
import cn.woyeshi.entity.beans.manager.UserInfo
import cn.woyeshi.presenter.base.*
import cn.woyeshi.presenterimpl.R
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by wys on 2017/11/8.
 */
interface ILoginView : IBaseView {
    fun onLoginRequestSuccess(loginInfo: UserInfo)
}

interface ILoginService {

    @GET("user/")
    fun login(@Query("userName") userName: String, @Query("password") password: String): Flowable<BaseResponse<List<UserInfo>>>

}

class LoginPresenter<T : ILoginView>(t: T) : BasePresenter<T>(t) {

    private var loginService: ILoginService = RetrofitUtils.create(ILoginService::class.java)

    fun login(userName: String, password: String) {
        val flowAble: Flowable<List<UserInfo>> = observe(loginService.login(userName, password))
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
}



