package com.goncalorod.dinosaurs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LeaderboardActivity : AppCompatActivity() {

    var scores  = arrayListOf<Score>()

    var scoresAdapter = ScoresAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        val db = Firebase.firestore
        db.collection("scores").orderBy("score").get().addOnSuccessListener {

            for (document in it.documents){
                scores.add(Score(document.getString("player_name")!!, document.getLong("score")!!.toInt()))
            }

        }

        val listViewScore = findViewById<ListView>(R.id.listView_leaderboard)
        listViewScore.adapter = scoresAdapter
        scoresAdapter.notifyDataSetChanged()
    }


    inner class ScoresAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return scores.count()
        }

        override fun getItem(position: Int): Any {
            return scores[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.score_entry, parent, false)
            val textViewPosition = rowView.findViewById<TextView>(R.id.textView_pos)
            val textViewName = rowView.findViewById<TextView>(R.id.textView_name)
            val textViewScore = rowView.findViewById<TextView>(R.id.textView_score)
            textViewPosition.text = "#${position + 1}"
            textViewName.text = scores[position].name
            textViewScore.text = scores[position].score.toString()
            rowView.isClickable = false
            return rowView
        }


    }
}