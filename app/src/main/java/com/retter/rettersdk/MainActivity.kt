package com.retter.rettersdk


import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.retter.rettersdk.App.Companion.rio
import com.retter.rettersdk.network.*
import com.rettermobile.rio.cloud.RioCallMethodOptions
import com.rettermobile.rio.cloud.RioCloudObjectOptions


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rio.getCloudObject(RioCloudObjectOptions(classId = "Authenticate", instanceId = "01gj4yxz2ax9txgsxjnxd4rhrf") , onSuccess = { cloudObj ->
            cloudObj.call<AuthResponse>(RioCallMethodOptions("generateCustomToken", body = AuthRequest()), onSuccess = {
                rio.authenticateWithCustomToken(it.body!!.data!!.customToken.toString())
            }, onError = {
                Log.e("", "")
            })
        }, onError = { throwable ->
            Log.d("TAG", throwable!!.message.toString())
        })

        rio.getCloudObject(RioCloudObjectOptions(classId = "TodoProject", instanceId = "01gj501gb69yse4sbt0jp61vdq") , onSuccess = { cloudObj ->
            cloudObj.public.subscribe( eventFired = { event ->
                cloudObj.call<GetTodosResponse>(RioCallMethodOptions(
                    method = "GetTodos"
                ), onSuccess = { response ->
                    val todos = response.body!!.todos
                    val listView: ListView = findViewById<View>(R.id.todosListView) as ListView
                    listView.adapter = listViewAdapter(this, todos)
                }, onError = {
                    Log.d("TAG", it!!.message.toString() )
                })
            }, errorFired = { fThrowable ->
                Log.d("TAG", fThrowable!!.message.toString() )
            })
        }, onError = { throwable ->
            Log.d("TAG", throwable!!.message.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val input = EditText(this);
            input.inputType = InputType.TYPE_CLASS_TEXT;
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Add Todo")
            builder.setView(input)
            builder.setNegativeButton("Cancel", null)
            builder.setPositiveButton("Add") { dialogInterface, i ->
                rio.getCloudObject(RioCloudObjectOptions(classId = "TodoProject", instanceId = "01gj501gb69yse4sbt0jp61vdq"), onSuccess = { cloudObj ->
                    cloudObj.call<UpsertTodoResponse>(RioCallMethodOptions(
                        method = "UpsertTodo",
                        body = UpsertTodoRequest(input.text.toString())
                    ), onSuccess = { response ->
                        Log.d("TAG", "succesfully added" )
                    }, onError = {
                        Log.d("TAG", "fError" + it!!.message.toString())
                    })
                }, onError = { throwable ->
                    Log.d("TAG", throwable!!.message.toString())
                })
            }
            builder.show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}

