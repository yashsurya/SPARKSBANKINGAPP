package com.example.basicbankingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdaptor(val context: Context, val item: ArrayList<Customer>, entry: String?) :
        RecyclerView.Adapter<CustomerAdaptor.ViewHolder>(){
    private var profile_photo :ByteArray? = null
    private val entry_state = entry
    private val FADE_DURATION: Long = 1000
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdaptor.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_customer_list,parent,false)
        )
    }

    override fun onBindViewHolder(holder: CustomerAdaptor.ViewHolder, position: Int) {
        val model : Customer = item[position]
        holder.UserImage.setImageBitmap(DBbitmapUtility.getImage(model.profile_photo))
        holder.Username.text = model.name.toString()
        if(entry_state == null) {
            holder.SelectButton.setOnClickListener {
                var Intent = Intent(it.context, UserDetailsActivity::class.java)
                Intent.putExtra("name", model.name)
                it.context.startActivity(Intent)
            }
        }
        else if(entry_state != null){
            holder.SelectButton.setOnClickListener {
                var Intent = Intent(it.context, AmountActivity::class.java)
                Intent.putExtra("name1", model.name)
                Intent.putExtra("name",entry_state)
                it.context.startActivity(Intent)
            }
        }
        setFadeAnimation(holder.itemView)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION
        view.startAnimation(anim)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val CardView = view.findViewById<CardView>(R.id.cvUser)
        val UserImage = view.findViewById<ImageView>(R.id.ivUserImage)
        val Username = view.findViewById<TextView>(R.id.tvUsername)
        val SelectButton = view.findViewById<LinearLayout>(R.id.llSelect)
    }




}