package com.example.gmail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

data class Email(
    val sender: String,
    val subject: String,
    val preview: String,
    val time: String
)

class EmailAdapter(private val emails: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtSender: TextView = itemView.findViewById(R.id.txtSender)
        val txtSubject: TextView = itemView.findViewById(R.id.txtSubject)
        val txtPreview: TextView = itemView.findViewById(R.id.txtPreview)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emails[position]
        holder.txtSender.text = email.sender
        holder.txtSubject.text = email.subject
        holder.txtPreview.text = email.preview
        holder.txtTime.text = email.time
    }

    override fun getItemCount(): Int = emails.size
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Dữ liệu giả
        val emailList = listOf(
            Email("Alice", "Meeting Reminder", "Don’t forget our meeting tomorrow", "10:45 AM"),
            Email("Bob", "Your Invoice", "Invoice for your recent purchase", "9:15 AM"),
            Email("Carol", "Discount Offer", "Get 50% off on your next purchase!", "Yesterday"),
            Email("Dave", "Welcome to our service", "Thank you for signing up!", "Yesterday"),
            Email("Eve", "New updates available", "Check out the latest updates", "2 days ago")
        )

        // Gán adapter và layout cho RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EmailAdapter(emailList)
    }
}