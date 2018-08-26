package cn.woyeshi.presenterimpl.presenters

import android.text.TextUtils
import cn.woyeshi.entity.BaseResponse
import cn.woyeshi.entity.beans.manager.UploadResult
import cn.woyeshi.presenter.base.BasePresenter
import cn.woyeshi.presenter.base.BaseSubscriber
import cn.woyeshi.presenter.base.IBaseView
import cn.woyeshi.presenter.base.RetrofitUtils
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File


interface IFileUploadView : IBaseView {
    fun onUploadSuccess(url: String)
    fun onUploadFail()

}

interface IFileUploadService {

    @Multipart
    @POST("images/upload/")
    fun uploadImage(@Part("description") description: RequestBody, @Part file: MultipartBody.Part): Flowable<BaseResponse<UploadResult>>

}

class FileUploadPresenter<T : IFileUploadView>(t: T) : BasePresenter<T>(t) {

    private val fileUploadService by lazy { RetrofitUtils.create(IFileUploadService::class.java) }

    fun uploadImage(file: File) {
        val fileExtension = file.extension
        val mediaType = "image/$fileExtension"
        val requestFile = RequestBody.create(MediaType.parse(mediaType), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val description = RequestBody.create(MediaType.parse(mediaType), mediaType)
        val flowAble = observe(fileUploadService.uploadImage(description, body))
        flowAble.subscribe(object : BaseSubscriber<UploadResult>(flowAble) {
            override fun onNext(t: UploadResult) {
                super.onNext(t)
                if (TextUtils.isEmpty(t.url)) {
                    iView.onUploadFail()
                } else {
                    iView.onUploadSuccess(t.url)
                }
            }
        })
    }

}