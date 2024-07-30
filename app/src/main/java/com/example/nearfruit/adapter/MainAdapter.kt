package com.example.nearfruit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.nearfruit.data.model.ModelResults
import com.example.nearfruit.databinding.ListItemMainBinding
import com.example.nearfruit.ui.DetailLocationActivity
import com.example.nearfruit.utils.HitungJarak.getDistance
import im.delight.android.location.SimpleLocation
import java.text.DecimalFormat

class MainAdapter(private val context: Context) :
    RecyclerView.Adapter<MainAdapter.SearchViewHolder>() {

    private val modelResults = ArrayList<ModelResults>()
    lateinit var simpleLocation: SimpleLocation
    var strLatitude = 0.0
    var strLongitude = 0.0

    fun setResultAdapter(items: ArrayList<ModelResults>) {
        modelResults.clear()
        modelResults.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ListItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = modelResults[position]

        simpleLocation = SimpleLocation(context)

        //current Location
        strLatitude = simpleLocation.latitude
        strLongitude = simpleLocation.longitude
        val strPlaceID = modelResults[position].placeId

        //location destination
        val strLat = modelResults[position].modelGeometry?.modelLocation?.lat!!
        val strLong = modelResults[position].modelGeometry?.modelLocation?.lng!!
        val strJarak = getDistance(strLat, strLong, strLatitude, strLongitude)

        holder.tvNamaLokasi.text = item.name
        holder.tvAlamat.text = item.vicinity
        holder.tvJarak.text = DecimalFormat("#.##").format(strJarak) + " KM"

        holder.cvListLocation.setOnClickListener { view: View? ->
            val intent = Intent(context, DetailLocationActivity::class.java)
            intent.putExtra("placeId", strPlaceID)
            intent.putExtra("lat", strLat)
            intent.putExtra("lng", strLong)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return modelResults.size
    }

    class SearchViewHolder(val binding: ListItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        val cvListLocation: CardView
        val tvNamaLokasi: TextView
        val tvAlamat: TextView
        val tvJarak: TextView

        init {
            cvListLocation = binding.cvListLocation
            tvNamaLokasi = binding.tvNamaLokasi
            tvAlamat = binding.tvAlamat
            tvJarak = binding.tvJarak
        }
    }
}