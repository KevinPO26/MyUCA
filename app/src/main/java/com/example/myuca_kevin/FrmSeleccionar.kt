package com.example.myuca_kevin

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap.Config
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myuca_kevin.databinding.FragmentFrmSeleccionarBinding
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FrmSeleccionar.newInstance] factory method to
 * create an instance of this fragment.
 */
class FrmSeleccionar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentFrmSeleccionarBinding
    var etNombres: EditText?=null
    var etApellidos: EditText?=null
    var etFechaNac: EditText?=null
    var etTitulo: EditText?=null
    var etEmail: EditText?=null
    var etFacultad: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentFrmSeleccionarBinding.inflate(layoutInflater)
        mostarDatePicker()
        cargarDatos()
        fbinding.btnEliminar.setOnClickListener {
            validacionEliminar()
        }

        fbinding.btnActualizar.setOnClickListener {
            validacionActualizar()
        }
        return fbinding.root
    }

    private fun validacionActualizar() {
        try {

            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Actualizar Coordinador")
            dialog.setMessage("¿Desea actualizar este Coordinador?")
            dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
                with(fbinding) {
                    actualizarCoor()
                    activity?.onBackPressed()

                }

            })
            dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(activity, "Actualizacion Cancelada", Toast.LENGTH_SHORT).show()

            })
            dialog.show()
        } catch (ex: Exception) {
            Toast.makeText(
                activity, "Error: ${ex.toString()} ",
                Toast.LENGTH_LONG
            ).show()

        }
    }


    private fun actualizarCoor() {
        with(fbinding){
            var idC = requireArguments().getInt("idC").toString()
            var ip = requireArguments().getString("ip").toString()
            val url = "http://$ip/MyUCA/actualizarCoor.php?" +
                    "resource_id=$idC" +
                    "&nombres=${etNombresC.text.toString()}" +
                    "&apellidos=${etApellidosC.text.toString()}" +
                    "&fechaNac=${etFechaNacC.text.toString()}" +
                    "&titulo=${etTituloC.text.toString()}" +
                    "&email=${etEmailC.text.toString()}" +
                    "&facultad=${etFacultadC.text.toString()}"

            val queue: RequestQueue = Volley.newRequestQueue(activity)
            var resultadoPost = object : StringRequest(Request.Method.PUT, url,
                Response.Listener<String> { response ->

                }, Response.ErrorListener { error ->

                }){
                override fun getParams(): MutableMap<String, String> {
                    val parametros = HashMap<String, String>()

                    return parametros
                }
            }
            queue.add(resultadoPost)
        }
    }

    private fun validacionEliminar() {
        try {

            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Eliminar Coordinador")
            dialog.setMessage("¿Desea eliminar este Coordinador?")
            dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
                eliminarCoor()
                activity?.onBackPressed()

            })
            dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(activity, "Eliminación Cancelada", Toast.LENGTH_SHORT).show()

            })
            dialog.show()
        } catch (ex: Exception) {
            Toast.makeText(
                activity, "Error: ${ex.toString()} ",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    private fun eliminarCoor() {
        runBlocking {
            var idC = requireArguments().getInt("idC").toString()
            var ip = requireArguments().getString("ip").toString()
            val queue: RequestQueue= Volley.newRequestQueue(activity)
            val url = "http://$ip/MyUCA/eliminarCoor.php?resource_id=$idC"
            var resultadoPost = object : StringRequest(Request.Method.DELETE, url,
                Response.Listener<String> { response ->

                }, Response.ErrorListener { error ->

                }){

            }
            queue.add(resultadoPost)
        }

    }

    fun cargarDatos(){
        with(fbinding){
            var idC = requireArguments().getInt("idC").toString()
            var ip = requireArguments().getString("ip").toString()
            val queue: RequestQueue = Volley.newRequestQueue(activity)
            val url = "http://$ip/MyUCA/buscarCoor.php?resource_id=$idC"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    etNombresC.setText(response.getString("nombres"))
                    etApellidosC.setText(response.getString("apellidos"))
                    etFechaNacC.setText(response.getString("fechaNac"))
                    etTituloC.setText(response.getString("titulo"))
                    etEmailC.setText(response.getString("email"))
                    etFacultadC.setText(response.getString("facultad"))


                }, { error ->
                    Toast.makeText(activity, "ID no existe", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }

    }

    private fun mostarDatePicker() {
        fbinding.etFechaNacC.setOnClickListener {
            val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }

            datePicker.show(parentFragmentManager, "datePicker")
        }
    }


    private fun onDateSelected(day: Int, month: Int, year: Int) {

        if (day >= 1 && day <= 9 && month > 9) {
            fbinding.etFechaNacC.setText("$year-$month-0$day")
        }else if (day >= 1 && day <= 9 && month >= 1 && month <= 9) {
            fbinding.etFechaNacC.setText("$year-0$month-0$day")

        }else if (month >= 1 && month <= 9 && day >= 10) {
            fbinding.etFechaNacC.setText("$year-0$month-$day")
        }else if (month > 9 && day > 9) {
            fbinding.etFechaNacC.setText("$year-$month-$day")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FrmSeleccionar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FrmSeleccionar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}