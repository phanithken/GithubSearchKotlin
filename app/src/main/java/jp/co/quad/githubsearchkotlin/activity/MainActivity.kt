package jp.co.quad.githubsearchkotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.co.quad.githubsearchkotlin.adapter.CardAdapter
import jp.co.quad.githubsearchkotlin.service.GithubService
import jp.co.quad.githubsearchkotlin.service.ServiceFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter

    var repositories:MutableList<String> = ArrayList()

    private val searchRepositoryServe by lazy {
        GithubService.create()
    }
    lateinit var searchField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchField = findViewById(R.id.search_field) as EditText
        searchField.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length < 2){
                    // clear items in recycler view
                    repositories.clear()
                    recyclerView?.adapter?.notifyDataSetChanged()
                }else{
                    Observable.just(s.toString())
                            .delay(1000, TimeUnit.MILLISECONDS)
                            .debounce(1500, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .filter { it != null }
                            .filter { it.length > 1 }
                            .subscribe{
                                searchRepositoryServe.searchRepositories(it)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                {result ->
                                                    // clear the list and add data for every new query list
                                                    repositories.clear()
                                                    recyclerView?.adapter?.notifyDataSetChanged()
                                                    var i=0
                                                    while (i<result.items.size()){
                                                        repositories.add(result.items[i].asJsonObject.get("full_name").toString().replace("\"", ""))
                                                        recyclerView.adapter = CardAdapter(repositories, this@MainActivity)
                                                        println("nis " + result.items[i].asJsonObject.get("name").toString())
                                                        i++
                                                    }
                                                },
                                                { error ->
                                                    repositories.clear()
                                                    recyclerView?.adapter?.notifyDataSetChanged()
                                                    Toast.makeText(this@MainActivity,
                                                            "You can make 10 query per minute. Please wait!", Toast.LENGTH_SHORT).show()
                                                }
                                        )
                            }
                }
            }
        })
    }
}
