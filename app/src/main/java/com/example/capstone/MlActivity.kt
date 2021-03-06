@file:Suppress("DEPRECATION")

package com.example.capstone

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.capstone.ui.detail.DetailActivity
import java.io.IOException


class MlActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var imageuri: Uri? = null
    private var buclassify: Button? = null
    private var classitext: TextView? = null
    private var tfLiteHelper: TFLiteHelper? = null
    private var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ml)
        imageView = findViewById<View>(R.id.image) as ImageView
        buclassify = findViewById<View>(R.id.classify) as Button
        classitext = findViewById<View>(R.id.classifytext) as TextView
        tfLiteHelper = TFLiteHelper(this)
        tfLiteHelper!!.init()
        imageView!!.setOnClickListener(selectImageListener)
        buclassify!!.setOnClickListener(classifyImageListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            imageuri = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageuri)
                imageView!!.setImageBitmap(bitmap)
                buclassify?.isEnabled = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private var selectImageListener = View.OnClickListener {
        val SELECT_TYPE = "image/*"
        val SELECT_PICTURE = "Select Picture"
        val intent = Intent()
        intent.type = SELECT_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, SELECT_PICTURE), 12)
    }
    private var classifyImageListener = View.OnClickListener {
        if (bitmap != null) {
            tfLiteHelper?.classifyImage(bitmap!!)
            Log.d("Main Activity", tfLiteHelper!!.showresult().toString())
            cocokLabel()
        }
    }

    private fun cocokLabel() {
        var idResult = 0
        var listResult:List<String>? = tfLiteHelper?.showresult()
        if (listResult != null) {
            when{
                listResult.contains("Amaranthus Viridis (Arive-Dantu)") -> idResult=1
                listResult.contains("Basella Alba (Basale)") -> idResult=2
                listResult.contains("Ficus Auriculata (Roxburgh)") -> idResult=3
                listResult.contains("Moringa Oleifera (Drumstick)") -> idResult=4
                listResult.contains("Muntingia Calabura (Jamaica Cherry-Gasagase)") -> idResult=5
                listResult.contains("Murraya Koenigii (Curry)") -> idResult=6
                listResult.contains("Nyctanthes Arbor-tristis (Parijata)") -> idResult=7
                listResult.contains("Ocimum Tenuiflorum (Tulsi)") -> idResult=8
                listResult.contains("Piper Betle (Betel)") -> idResult=9
                listResult.contains("Psidium Guajava (Guava)") -> idResult=10
                else ->{
                    Toast.makeText(applicationContext, "Maaf tidak bisa diklasifikasi,ulangi", Toast.LENGTH_SHORT).show()
                }
            }
            
            if(idResult !=0){
                val intent = Intent(this,DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID,idResult.toString())
                startActivity(intent)
                finish()
            }else{
                /*val intent = Intent(this,DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID,idResult.toString())
                startActivity(intent)
                finish() */
            }
        }
    }

    private fun setLabel(entries: List<String?>?) {
        classitext!!.text = ""
        for (entry in entries!!) {
            classitext!!.append(entry)
        }
    }

}
