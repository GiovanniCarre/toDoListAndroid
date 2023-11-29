package fr.sportcooks.giovanni_carre.master1.univangers.td3exercice4version3
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter(val context: Context) : RecyclerView.Adapter<RecycleAdapter.TaskViewHolder>() {
    class Task(var level: Int, var task: String?):Parcelable{
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(level)
            parcel.writeString(task)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Task> {
            override fun createFromParcel(parcel: Parcel): Task {
                return Task(parcel)
            }

            override fun newArray(size: Int): Array<Task?> {
                return arrayOfNulls(size)
            }
        }

    }

    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tv_task =itemView.findViewById<TextView>(R.id.tv_task)
        val v_level=itemView.findViewById<View>(R.id.v_level)
    }

    private var ma_liste: ArrayList<Task> = ArrayList<Task>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ligne, parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ma_liste.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item=ma_liste[position]
        holder.tv_task.text=item.task
        val colorResource = when (item.level) {
            1 -> R.color.color_haute
            2 -> R.color.color_moyenne
            3 -> R.color.color_basse
            else -> R.color.color_haute
        }
        val color = ContextCompat.getColor(context, colorResource)
        holder.v_level.setBackgroundColor(color)

        //on sert du tag pour stocker la position de l'élement dans le tableau:
        //cette position nous sera utile lors du swipe le retrouve
        holder.itemView.tag=position

        holder.itemView.setOnClickListener {
            val task = item.task
            val level = item.level
            Toast.makeText(context, "Tâche: $task\nDe priorité $level", Toast.LENGTH_SHORT).show()
        }
    }

    fun ajouter(task :String, level: Int){
        ma_liste.add(Task(level, task))
        //Refresh sans tous rafraichir
        notifyItemInserted(ma_liste.size-1)
    }

    fun supprimer(position : Int){
        ma_liste.removeAt(position)
        notifyItemRemoved(position)

        notifyItemRangeChanged(position,ma_liste.size - position)
    }



}