package com.example.myuca_kevin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myuca_kevin.databinding.FragmentFrmInsertarBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FrmInsertar.newInstance] factory method to
 * create an instance of this fragment.
 */
class FrmInsertar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentFrmInsertarBinding
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
        fbinding = FragmentFrmInsertarBinding.inflate(layoutInflater)
        mostarDatePicker()
        validarCampos()
        limpiarCampos()
        return fbinding.root
    }

    private fun validarCampos() {
        with(fbinding){
            fbinding.btnGuardar.setOnClickListener {
                if(etNombresC.text.toString() == "" || etApellidosC.text.toString() == ""
                    || etFechaNacC.text.toString() == "" || etEmailC.text.toString() == ""
                    || etTituloC.text.toString() == "" || etFacultadC.text.toString() ==""){
                    Toast.makeText(activity, "Algunos campos estan vacios y todos los campos son requeridos", Toast.LENGTH_LONG).show()

                }else{
                    insertarCoor()
                }
            }
        }
    }

    private fun limpiarCampos() {
        with(fbinding){
            btnLimpiar.setOnClickListener {
                etNombresC.setText("")
                etFechaNacC.setText("")
                etApellidosC.setText("")
                etEmailC.setText("")
                etFacultadC.setText("")
                etTituloC.setText("")
            }
        }
    }

    private fun insertarCoor() {
       with(fbinding){
           etNombres = etNombresC
           etApellidos = etApellidosC
           etFechaNac = etFechaNacC
           etTitulo=etTituloC
           etEmail= etEmailC
           etFacultad = etFacultadC
           var ip = requireArguments().getString("ip").toString()
           val url = "http://$ip/MyUCA/insertarCoor.php"
           val queue: RequestQueue = Volley.newRequestQueue(activity)
           //Peticion que permite el ingreso de datos en la base de datos
           var resultadoPost = object : StringRequest(Request.Method.POST, url,
               Response.Listener<String> { response ->
                   Toast.makeText(activity, "Coordinador insertado correctamente", Toast.LENGTH_LONG).show()
               }, Response.ErrorListener { error ->
                   Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
               }){
               override fun getParams(): MutableMap<String, String> {
                   val parametros = HashMap<String, String>()
                   parametros.put("nombres", etNombres?.text.toString())
                   parametros.put("apellidos", etApellidos?.text.toString())
                   parametros.put("fechaNac", etFechaNac?.text.toString())
                   parametros.put("titulo", etTitulo?.text.toString())
                   parametros.put("email", etEmail?.text.toString())
                   parametros.put("facultad", etFacultad?.text.toString())

                   return parametros
               }
           }
           queue.add(resultadoPost)


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
         * @return A new instance of fragment FrmInsertar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FrmInsertar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}