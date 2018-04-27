package jp.co.quad.githubsearchkotlin.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import jp.co.quad.githubsearchkotlin.R
import jp.co.quad.githubsearchkotlin.ResultDetail
import kotlinx.android.synthetic.main.list_item.view.*

class CardAdapter(items: List<String>, ctx: Context): RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    var context = ctx
    var list = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list.get(position)
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(this.context, ResultDetail::class.java)
            intent.putExtra("repo_name", list.get(position))
            this.context.startActivity(intent)
        })
    }

    class ViewHolder(v:View): RecyclerView.ViewHolder(v){
        val name = v.item_name
    }
}