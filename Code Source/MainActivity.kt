package fr.sportcooks.giovanni_carre.master1.univangers.td3exercice4version3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    class Task(val todo: String?, val color: Int) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(todo)
            parcel.writeInt(color)
        }

        override fun describeContents() = 0

        companion object CREATOR : Parcelable.Creator<Task> {
            override fun createFromParcel(parcel: Parcel): Task {
                return Task(parcel)
            }

            override fun newArray(size: Int): Array<Task?> {
                return arrayOfNulls(size)
            }
        }
    }
    var Tasks=ArrayList<Task>()

    //val adapter = MyAdapter(this)
    val adapter = RecycleAdapter(this)

    val childActivityRes = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val intent = result.data
                val ma_chaine = intent?.getStringExtra("Champ_Un").toString()
                val mon_entier = intent?.getIntExtra("Champ_Deux", 0)!!
                Toast.makeText(
                    this@MainActivity,
                    "Tâche : $ma_chaine\n De priorité $mon_entier ",
                    Toast.LENGTH_LONG
                ).show()
                adapter.ajouter(ma_chaine, mon_entier)
                Tasks.add(Task(ma_chaine, mon_entier))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState!=null){
            Tasks=savedInstanceState.getParcelableArrayList<Task>("MESTASKS")!!
            for (t: Task in Tasks) {
                t.todo?.let { adapter.ajouter(it,t.color) }
            }
        }else{
            for (s : String in FakeData.get_tasks()){
                val ts = Task(s.substring(s.lastIndexOf('>')+2), s.substring(s.indexOfFirst { it.isDigit() },s.indexOfFirst { it.isDigit() }+1).toInt());
                Tasks.add(ts);
                adapter.ajouter(ts.todo.toString(), ts.color);
            }
        }

        //val lv=findViewById<ListView>(R.id.lv_todo)
        val lv = findViewById<RecyclerView>(R.id.lv_todo)

        lv.layoutManager=LinearLayoutManager(this)

        //lv.setAdapter(adapter)
        lv.adapter=adapter

        /*lv.setOnItemClickListener{ parent,view,position,id ->
            val item=adapter.getItem(position)
            Toast.makeText(this@MainActivity,item.task+" "+item.level,Toast.LENGTH_LONG).show()
        }*/

        var item_touch_helper_callback=object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.itemView.tag as Int
                adapter.supprimer(position)
                Tasks.removeAt(position)
            }

        }

        val item_touch_helper=ItemTouchHelper(item_touch_helper_callback)
        item_touch_helper.attachToRecyclerView(lv)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ajoutTache -> {
                // Create the intent for launching the child activity
                val childIntent = Intent(this@MainActivity, childActivity::class.java)
                childActivityRes.launch(childIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("MESTASKS",Tasks)
    }

}

