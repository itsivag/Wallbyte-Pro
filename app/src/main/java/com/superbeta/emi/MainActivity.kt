package com.superbeta.emi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.superbeta.emi.navigation.EmiNavHost
import com.superbeta.emi.ui.theme.EmiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmiNavHost()
                }
            }
        }
//        readDb(this)
    }
}

fun readDb(context: Context) {
    val database = Firebase.database
    val myRef = database.getReference("Trending Categories")

    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val value = dataSnapshot.getValue<String>()
            Log.d("db", "Value is: $value")
            Toast.makeText(context, value, Toast.LENGTH_LONG).show()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("db", "Failed to read value.", error.toException())
        }
    })

}