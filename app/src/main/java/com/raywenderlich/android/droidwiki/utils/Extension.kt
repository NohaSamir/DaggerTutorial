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

package com.raywenderlich.android.droidwiki.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.annotation.StringRes
import android.text.Html
import android.text.Spanned
import kotlin.reflect.KClass

fun @receiver:StringRes Int.errorDialog(activity: Activity) {
  AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert)
      .setTitle("Error")
      .setMessage(this@errorDialog)
      .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
      .setIcon(android.R.drawable.ic_dialog_alert).show()
}

fun String?.parseHtml(): Spanned {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
  } else {
    @Suppress("deprecation")
    return Html.fromHtml(this)
  }
}

fun <T : Activity> KClass<T>.start(activity: Activity, finish: Boolean = false) {
  Intent(activity, this.java).apply {
    activity.startActivity(this)
  }
  if (finish) {
    activity.finish()
  }
}