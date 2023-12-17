package com.example.chickenquiz

// Importez les classes nécessaires
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PlayerAdapter(private val players: MutableList<PlayerData>, private val onItemClick: (String) -> Unit) : BaseAdapter() {
//classe qui gere le listview des joeurs
    override fun getCount(): Int {
        return players.size
    }

    override fun getItem(position: Int): Any {
        return players[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.item_player, parent, false)

        val playerNameTextView: TextView = view.findViewById(R.id.playerNameTextView)
        val maxLevelTextView: TextView = view.findViewById(R.id.maxLevelTextView)

        val player = getItem(position) as PlayerData
        playerNameTextView.text = player.name
        maxLevelTextView.text = "Max Level: ${player.maxLevel}"

        view.setOnClickListener {
            onItemClick.invoke(player.name)
        }

        return view
    }

    fun clear() {
        players.clear()
    }
}
