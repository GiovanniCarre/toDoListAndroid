package fr.sportcooks.giovanni_carre.master1.univangers.td3exercice4version3


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class MyAdapter(private val context: Context) : BaseAdapter() {

    class Task(var level: Int,var task :String)

    private var ma_liste :ArrayList<Task> = ArrayList<Task>()

    override fun getCount(): Int {
        return ma_liste.size
    }

    override fun getItem(position: Int): Task {
        return ma_liste[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, converView: View?, vGroup: ViewGroup?): View {
        var nView: View? = converView
        if(nView==null){
            nView = LayoutInflater.from(context).inflate(R.layout.ligne,vGroup, false)
        }

        val wTask=nView!!.findViewById<TextView>(R.id.tv_task)
        val wLevel=nView!!.findViewById<View>(R.id.v_level)

        val colorResource = when (getItem(position).level) {
            1 -> R.color.color_haute
            2 -> R.color.color_moyenne
            3 -> R.color.color_basse
            else -> R.color.color_haute
        }
        val color = ContextCompat.getColor(context, colorResource)
        wLevel.setBackgroundColor(color)
        wTask.text=getItem(position).task

        return nView
    }

    fun ajouter(task :String, level: Int){
        ma_liste.add(Task(level,task))
        //Refresh to have new elements on screen
        notifyDataSetChanged()
    }

}