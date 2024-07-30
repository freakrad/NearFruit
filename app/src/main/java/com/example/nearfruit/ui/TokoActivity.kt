package com.example.nearfruit.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nearfruit.adapter.MainAdapter
import com.example.nearfruit.data.model.ModelResults
import com.example.nearfruit.databinding.ActivityLocationBinding
import com.example.nearfruit.viewmodel.ListResultViewModel
import im.delight.android.location.SimpleLocation

class TokoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding
    lateinit var simpleLocation: SimpleLocation
    lateinit var strLokasi: String
    lateinit var progressDialog: ProgressDialog
    lateinit var mainAdapter: MainAdapter
    lateinit var listResultViewModel: ListResultViewModel
    var strLatitude = 0.0
    var strLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLocationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.let {
            setSupportActionBar(it)
            assert(supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Sedang menampilkan lokasi")

        //set Library Location
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }

        //get Location
        strLatitude = simpleLocation.latitude
        strLongitude = simpleLocation.longitude

        //set location lat long
        strLokasi = "$strLatitude, $strLongitude"

        //set title
        binding.tvTitle.setText("Toko Buah Terdekat")

        //set data adapter
        mainAdapter = MainAdapter(this)
        binding.rvListResult.setLayoutManager(LinearLayoutManager(this))
        binding.rvListResult.setAdapter(mainAdapter)
        binding.rvListResult.setHasFixedSize(true)

        //viewmodel
        listResultViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ListResultViewModel::class.java)
        listResultViewModel.setTokoLocation(strLokasi)
        progressDialog.show()
        listResultViewModel.getTokoLocation()
            .observe(this) { modelResults: ArrayList<ModelResults> ->
                if (modelResults.size != 0) {
                    Log.d("TokoActivity", "ModelResults size: ${modelResults.size}")
                    mainAdapter.setResultAdapter(modelResults)
                    progressDialog.dismiss()
                } else {
                    Log.d("TokoActivity", "ModelResults size: 0")
                    Toast.makeText(
                        this,
                        "Oops, lokasi Toko buah tidak ditemukan!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}