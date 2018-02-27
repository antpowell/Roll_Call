package com.egmail.anthony.powell.roll_call_2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.Model.Course
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import kotlinx.android.synthetic.main.activity_student_signature.view.*
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

/**
 * Created by Powell on 1/28/2018.
 */

class CourseListAdapter(context: Context) : RecyclerView.Adapter<CourseListCustomViewHolder>() {
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseListCustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.course_item, parent, false)

        return CourseListCustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return Course.codes.count()
    }

    override fun onBindViewHolder(holder: CourseListCustomViewHolder, position: Int) {
        Course.codes.sort()

        val courseCode: String = Course.codes[position]

        val courseImageCode: String = courseCode.replace("([^a-zA-Z]{3}L{1})$|[^a-zA-Z$]{3}".toRegex(), "")

        if (Course.images.containsKey(courseImageCode)) {
            holder.v.course_item_image_view.loadUrl(Course.images.get(courseImageCode))
        } else {
            holder.v.course_item_image_view.loadUrl(Course.images.get("DEFAULT"))
        }
        holder.v.course_item_text_view.text = courseCode

    }

}

class CourseListCustomViewHolder(val v: View) : RecyclerView.ViewHolder(v) {

    init {
        v.setOnClickListener {
            println("Clicked: ${v.course_item_text_view.text}")

            val intent = Intent(v.context.applicationContext, StudentSignature::class.java)
            intent.putExtra("COURSE_TITLE", v.course_item_text_view.text)
            v.context.startActivity(intent)
            println("Moved to Student Signature activity")
        }
    }

}