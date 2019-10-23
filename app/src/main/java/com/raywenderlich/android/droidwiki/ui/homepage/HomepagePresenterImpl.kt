/*
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.droidwiki.ui.homepage

import com.raywenderlich.android.droidwiki.model.HomepageResult
import com.raywenderlich.android.droidwiki.network.Homepage
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class HomepagePresenterImpl @Inject constructor(private val homepage: Homepage)
    : HomepagePresenter {

    private lateinit var homepageView: HomepageView

    /*private val client: OkHttpClient = OkHttpClient()
    private val api: WikiApi = WikiApi(client)
    private val homepage: Homepage = Homepage(api)*/

    override fun setView(homepageView: HomepageView) {
        this.homepageView = homepageView
    }

    override fun loadHomepage() {
        homepageView.displayLoading()
        homepage.get().enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                homepageView.dismissLoading()
                if (response.isSuccessful == true) {
                    response.let {
                        HomepageResult(it).homepage?.let {
                            homepageView.displayHomepage(it)
                        } ?: run {
                            homepageView.displayError(response.message)
                        }
                    }
                } else {
                    homepageView.displayError(response.message)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                homepageView.displayError(e.message)
                e.printStackTrace()
            }
        })
    }
}