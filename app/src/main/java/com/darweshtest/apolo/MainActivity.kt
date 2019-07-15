package com.darweshtest.apolo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.darwesh.apollo.sample.AddQuery
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var MyArr = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Welcome test", Toast.LENGTH_LONG).show()


        Do.setOnClickListener {
            getAll()
        }
    }


    fun getAll() {
        MyClientApollo.getMyClient()
            .query(
                AddQuery.builder().build()
            ).enqueue(object : ApolloCall.Callback<AddQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Toast.makeText(this@MainActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(response: Response<AddQuery.Data>) {
                    runOnUiThread {
                        for (hero in response.data()!!.heros!!.iterator()) {
                            val id = hero.id()
                            val name = hero.name()
                            MyArr.add(Hero(id, name))
                        }
                        RecyclerV.setHasFixedSize(true)
                        RecyclerV.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                        RecyclerV.adapter = Adapter(this@MainActivity, MyArr)
                    }
                }

            })

    }


    inner class Adapter(var conx: Context, var list: ArrayList<Hero>) : RecyclerView.Adapter<Adapter.MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val myView = LayoutInflater.from(conx).inflate(R.layout.card, parent, false)
            return MyHolder(myView)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.id.text = list[position].id.toString()
            holder.name.text = list[position].Name
        }

        inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
            var id = view.findViewById<TextView>(R.id.HeroId)
            var name = view.findViewById<TextView>(R.id.HeroName)
        }

    }


}
