package cn.woyeshi.presenterimpl.presenters

import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.IBasePresenter
import cn.woyeshi.presenter.base.IBaseView
import cn.woyeshi.presenterimpl.service.RegisterService

interface IRegisterView : IBaseView {

}

interface IRegisterPresenter<T : IRegisterView> : IBasePresenter<T> {
    fun getVerifyCode(phone: String)
    fun register(phone: String, pwd: String, code: String)
}

class RegisterPresenter<T : IRegisterView>(t: T) : BasePresenter<T>(t), IRegisterPresenter<T> {

    private val registerService = RegisterService()


    override fun getVerifyCode(phone: String) {


    }

    override fun register(phone: String, pwd: String, code: String) {


    }

}

