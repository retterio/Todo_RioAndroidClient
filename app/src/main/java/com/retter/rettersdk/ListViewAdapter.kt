package com.retter.rettersdk

import android.app.Activity
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.retter.rettersdk.App.Companion.rio
import com.retter.rettersdk.network.*
import com.rettermobile.rio.cloud.RioCallMethodOptions
import com.rettermobile.rio.cloud.RioCloudObjectOptions

class listViewAdapter(private val context: Activity, private val todos: ArrayList<Todos>)
    : ArrayAdapter<Todos>(context, R.layout.listview_layout, todos) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.listview_layout, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val editButton = rowView.findViewById(R.id.editTodoBtn) as ImageButton
        val removeButton = rowView.findViewById(R.id.removeTodoBtn) as ImageButton

        val selectedTodo = todos[position].todo
        val selectedId = todos[position].id

        titleText.text = selectedTodo


        editButton.setOnClickListener {
            val input = EditText(context);
            input.inputType = InputType.TYPE_CLASS_TEXT;
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Edit Todo")
            builder.setView(input)
            builder.setNegativeButton("Cancel", null)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                rio.getCloudObject(RioCloudObjectOptions(
                    classId = "TodoProject",
                    instanceId = "01gj501gb69yse4sbt0jp61vdq"
                ), onSuccess = { cloudObj ->
                    cloudObj.call<UpsertTodoResponse>(
                        RioCallMethodOptions(
                            method = "UpsertTodo",
                            body = EditTodoRequest(selectedId, input.text.toString())
                        ), onSuccess = { response ->
                            Log.d("TAG", "Succesfully edited." )
                        }, onError = {
                            Log.d("TAG", "fError" + it!!.message.toString())
                        })
                }, onError = { throwable ->
                    Log.d("TAG", throwable!!.message.toString())
                })

            }
            builder.show()
        }

        removeButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Remove Todo")
            builder.setNegativeButton("Cancel", null)
            builder.setPositiveButton("Remove") { dialogInterface, i ->
                rio.getCloudObject(RioCloudObjectOptions(
                    classId = "TodoProject",
                    instanceId = "01gj501gb69yse4sbt0jp61vdq"
                ), onSuccess = { cloudObj ->
                    cloudObj.call<RemoveTodoResponse>(
                        RioCallMethodOptions(
                            method = "RemoveTodo",
                            body = RemoveTodoRequest(selectedId)
                        ), onSuccess = { response ->
                            Log.d("TAG", "Succesfully removed." )
                        }, onError = {
                            Log.d("TAG", "fError" + it!!.message.toString())
                        })
                }, onError = { throwable ->
                    Log.d("TAG", throwable!!.message.toString())
                })
            }
            builder.show()
        }
        return rowView
    }
}