package com.rohan.moonlightmodelstudent

import android.content.pm.PackageManager
import android.Manifest
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.snackbar.Snackbar
import com.rohan.moonlightmodelstudent.databinding.ActivityScannerBinding
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Scanner : AppCompatActivity() {
    private lateinit var layout: View
    private lateinit var bind: ActivityScannerBinding
    private lateinit var codeScanner: CodeScanner
    private var isallowed = false
    init{
        System.loadLibrary("keys")
    }
    private external fun getsecretkey():String
    private external fun getsecretiv():String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityScannerBinding.inflate(layoutInflater)
        layout = bind.scannerLayout
        setContentView(bind.root)
        onClickRequestPermission(bind.scannerLayout)
    }
    private fun enablecam(){
        codeScanner = CodeScanner(this, bind.scannerview)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                try {
                    val decodeByte: ByteArray = Base64.decode(it.text,Base64.DEFAULT)
                    val iv = IvParameterSpec(getsecretiv().toByteArray())
                    val keyspec = SecretKeySpec(getsecretkey().toByteArray(),"AES")
                    val chiper = Cipher.getInstance("AES/CBC/PKCS5PADDING")
                    chiper.init(Cipher.DECRYPT_MODE,keyspec,iv)
                    val descripted = chiper.doFinal(decodeByte)
                    val desauth = descripted.toString().split(":").toTypedArray()
                    Toast.makeText(this,desauth[0]+desauth[1],Toast.LENGTH_LONG).show()
                }catch (ex:Exception){
                    Toast.makeText(this, "Invalid QR Authorization", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show()
                codeScanner.startPreview()
            }
        }

        bind.scannerview.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                isallowed = true
                enablecam()
            } else {
                SweetAlertDialog(this,
                    SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Permission is Required!")
                    .setContentText("Enable the permission from setting and run the app")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(SweetAlertDialog.OnSweetClickListener {
                        it.dismissWithAnimation()
                        finish()
                    }).show()
                isallowed = false
            }
        }

    fun onClickRequestPermission(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                isallowed = true
                enablecam()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
                layout.showSnackbar(
                    view,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.ok)
                ) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

fun View.showSnackbar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(view, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}

    override fun onResume() {
        super.onResume()
        if (isallowed){
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (isallowed){
            codeScanner.releaseResources()
        }
        super.onPause()
    }

    override fun onDestroy() {
        if (isallowed){
            codeScanner.releaseResources()
        }
        super.onDestroy()
    }
}