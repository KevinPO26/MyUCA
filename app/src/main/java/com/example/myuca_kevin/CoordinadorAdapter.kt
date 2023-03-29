package com.example.myuca_kevin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myuca_kevin.databinding.RccoorBinding

class CoordinadorAdapter(val listCoor: List<Coordinador>, private val onClickListener: (Coordinador) -> Unit

) :
    RecyclerView.Adapter<CoordinadorAdapter.EstudianteHolder>() {
    inner class EstudianteHolder(val binding: RccoorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun cargar(
            coordinador: Coordinador, onClickListener: (Coordinador) -> Unit

        ) {
            with(binding) {
                tvIDC.text = coordinador.id.toString()
                tvNombresC.text = coordinador.nombres
                tvApellidosC.text = coordinador.apellidos
                tvFechaNacC.text = coordinador.fechaNat.toString()
                tvTituloC.text = coordinador.titulo
                tvEmailC.text= coordinador.email
                tvFacultadC.text= coordinador.facultad
                itemView.setOnClickListener { onClickListener(coordinador) }
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteHolder {
        val binding = RccoorBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return EstudianteHolder(binding)
    }

    override fun onBindViewHolder(holder: EstudianteHolder, position: Int) {
        holder.cargar(listCoor[position], onClickListener)


    }


    override fun getItemCount(): Int = listCoor.size



}