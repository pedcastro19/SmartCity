package ipvc.estg.smartcity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import ipvc.estg.smartcity.entities.Nota
import kotlinx.android.synthetic.main.linelayout.view.*


class ListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var notaslist = emptyList<Nota>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val itemViewtexto: TextView = itemView.findViewById(R.id.texto)
        val itemViewtitulo: TextView = itemView.findViewById(R.id.titulo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = inflater.inflate(R.layout.linelayout, parent, false)
        return MyViewHolder(itemView)
        //return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.linelayout, parent, false))
    }

    override fun getItemCount(): Int {
        return notaslist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notaslist[position]
        holder.itemViewtexto.texto.text = currentItem.descricao.toString()
        holder.itemViewtitulo.titulo.text = currentItem.titulo.toString()
        //holder.itemView.id_txt.text = currentItem.id.toString

        holder.itemView.setOnClickListener{
            val descvalor: String = currentItem.descricao
            val titvalor: String  = currentItem.titulo
            val idItem: Int = currentItem.id
            val intent = Intent(holder.itemView.context, editarnota::class.java)
            intent.putExtra("descvalor", descvalor)
            intent.putExtra("titvalor", titvalor)
            intent.putExtra("iddoItem", idItem)

            holder.itemView.context.startActivity(intent)
        }

    }

    fun setData(nota: List<Nota>){
        this.notaslist = nota
        notifyDataSetChanged()
    }

}