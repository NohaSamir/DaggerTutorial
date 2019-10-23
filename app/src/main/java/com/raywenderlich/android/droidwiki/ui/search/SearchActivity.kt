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

package com.raywenderlich.android.droidwiki.ui.search

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.android.droidwiki.R
import com.raywenderlich.android.droidwiki.application.WikiApplication
import com.raywenderlich.android.droidwiki.model.Entry
import com.raywenderlich.android.droidwiki.utils.errorDialog
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : Activity(), EntryView {

    //private val presenter: EntryPresenter = EntryPresenterImpl()

    @Inject
    lateinit var presenter: EntryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        (application as WikiApplication).wikiComponent.inject(this)

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_home)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        results_rv.layoutManager = LinearLayoutManager(this)

        presenter.setView(this)

    }

    // Create the menu entries
    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }

    // Bind menu entries with their actions
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        menu?.findItem(R.id.search)?.let { menuItem ->
            (menuItem.actionView as? SearchView)?.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(query: String) = true
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        presenter.getEntry(query ?: "")
                        return true
                    }
                })

                queryHint = getString(R.string.search_hint)
            }

            menuItem.expandActionView()
        }

        return super.onCreateOptionsMenu(menu)
    }

    // Implementation of EntryView

    override fun displayLoading() {
        wait_progress_bar.post {
            wait_progress_bar.visibility = View.VISIBLE
            results_rv.visibility = View.GONE
        }
    }

    override fun dismissLoading() {
        wait_progress_bar.post {
            wait_progress_bar.visibility = View.GONE
            results_rv.visibility = View.VISIBLE
        }
    }

    override fun displayEntries(results: List<Entry>) {
        results_rv.post {
            results_rv.adapter = EntryAdapter(this, results)
        }
    }

    override fun displayError(error: String?) {
        Log.e("ERROR", error)
        R.string.error.errorDialog(this)
    }
}
