package rango1.vku.sqlite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItems:((StudentModel)->Unit)? = null

    fun addItems(items: ArrayList<StudentModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItems(callback: (StudentModel)->Unit){
        this.onClickItems =callback

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent, false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
       val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItems?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

   class StudentViewHolder(view: View): RecyclerView.ViewHolder(view){
       private var id = view.findViewById<TextView>(R.id.tvID)
       private var name = view.findViewById<TextView>(R.id.tvName)
       private var email = view.findViewById<TextView>(R.id.tvEmail)
       private var contact = view.findViewById<TextView>(R.id.tvContact)
       private var address = view.findViewById<TextView>(R.id.tvAddress)
       private var btnDelete = view.findViewById<TextView>(R.id.btnDelete)


       fun bindView(std:StudentModel) {
           id.text = std.id.toString()
           name.text = std.name
           email.text = std.email
           contact.text = std.contact
           address.text = std.address
       }
   }


}