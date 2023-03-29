package com.example.myuca_kevin

import android.os.Build
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myuca_kevin.databinding.FragmentFrmMostrarCoorBinding
import org.json.JSONException
import java.net.Inet4Address
import java.net.NetworkInterface
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FrmMostrarCoor.newInstance] factory method to
 * create an instance of this fragment.
 */
class FrmMostrarCoor : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentFrmMostrarCoorBinding
    var listaCoor = ArrayList<Coordinador>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentFrmMostrarCoorBinding.inflate(layoutInflater)
        mostrarCoor()
        mostrarFrm()
        return fbinding.root
    }

    private fun mostrarFrm() {
        fbinding.btnInsertar.setOnClickListener {
            var ip = requireArguments().getString("ip").toString()
            Navigation.findNavController(fbinding.root).navigate(R.id.action_frmMostrarCoor_to_frmInsertar, Bundle().apply {
                putString("ip", ip)
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mostrarCoor() {

        var ip = requireArguments().getString("ip").toString()
        with(fbinding) {
            var coordinador: Coordinador
            //Peticion que se le hace a la api
            var queue: RequestQueue = Volley.newRequestQueue(activity)
            var url = "http://$ip/MyUCA/mostrarCoor.php"
            listaCoor.clear() //limpiar la lista para evitar repetición luego de insertar
            //peticion json que devolvera los valores de la tabla en la base datos
            var jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try{
                        var jsonArray= response.getJSONArray("data")
                        for (i in 0 until jsonArray.length()){
                            var jsonObject = jsonArray.getJSONObject(i)
                            var id=jsonObject.getString("idC").toInt()
                            val nombres = jsonObject.getString("nombres")
                            val apellidos = jsonObject.getString("apellidos")
                            val fechaNac = jsonObject.getString("fechaNac")
                            val titulo = jsonObject.getString("titulo")
                            val email = jsonObject.getString("email")
                            val facultad = jsonObject.getString("facultad")
                            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                            val date = LocalDate.parse(fechaNac, formatter)
                            coordinador = Coordinador(id, nombres, apellidos, date, titulo,email,facultad)

                            listaCoor.add(coordinador)


                        }
                        //Ingresar lista al recyclerview
                        rcvLista.layoutManager = LinearLayoutManager(activity)
                        rcvLista.adapter = CoordinadorAdapter(listaCoor, {coordinador -> onItemSelected(coordinador)})
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }, {  })

            queue.add(jsonObjectRequest)


        }

    }

    //Funcion que permite pulsar un coordinador dentro del recycleview
    private fun onItemSelected(coordinador: Coordinador) {
        var idCoor = coordinador.id
        var ip = requireArguments().getString("ip").toString()
        Navigation.findNavController(fbinding.root).navigate(R.id.action_frmMostrarCoor_to_frmSeleccionar, Bundle().apply {
            putInt("idC", idCoor)
            putString("ip", ip)
        })
    }
    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FrmMostrarCoor.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FrmMostrarCoor().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}