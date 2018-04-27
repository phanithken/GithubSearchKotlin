package jp.co.quad.githubsearchkotlin

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.base.BaseActivity
import jp.co.quad.githubsearchkotlin.adapter.CardAdapter
import jp.co.quad.githubsearchkotlin.di.DaggerMainActivityComponent
import jp.co.quad.githubsearchkotlin.di.MainActivityModule
import jp.co.quad.githubsearchkotlin.model.RepoItem
import jp.co.quad.githubsearchkotlin.presenter.MainPresenter
import jp.co.quad.githubsearchkotlin.presenter.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onActivityInject() {
        DaggerMainActivityComponent.builder().appComponent(getAppcomponent())
                .mainActivityModule(MainActivityModule())
                .build()
                .inject(this)
        presenter.attachView(this)
    }

    override fun onSearchResponse(list: List<RepoItem>?) {
        repositories.clear()
        recycler_view.adapter.notifyDataSetChanged()
        for (i in list!!){
            repositories.add(i.fullName)
            recycler_view.adapter = CardAdapter(repositories, this)
        }
    }

    override fun noResult() {
        repositories.clear()
        recycler_view.adapter.notifyDataSetChanged()
    }

    private var disposable: Disposable? = null
    private lateinit var cardAdapter: CardAdapter

    var repositories:MutableList<String> = ArrayList()

    lateinit var searchField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)

        searchField = findViewById(R.id.search_field) as EditText
        searchField.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length < 2){
                    // clear items in recycler view
                    repositories.clear()
                    recycler_view.adapter?.notifyDataSetChanged()
                }else{
                    Observable.just(s.toString())
                            .delay(1000, TimeUnit.MILLISECONDS)
                            .debounce(1500, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .filter { it != null }
                            .filter { it.length > 1 }
                            .subscribe{
                                presenter.getRepos(it)
                            }
                }
            }
        })
    }
}
