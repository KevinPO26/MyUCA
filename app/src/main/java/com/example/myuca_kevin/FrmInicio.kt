package com.example.myuca_kevin

import android.net.InetAddresses
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.example.myuca_kevin.databinding.FragmentFrmInicioBinding
import java.net.Inet4Address

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FrmInicio.newInstance] factory method to
 * create an instance of this fragment.
 */
class FrmInicio : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentFrmInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentFrmInicioBinding.inflate(layoutInflater)
        cargarMostraCoor()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun cargarMostraCoor() {
        fbinding.btnCoor.setOnClickListener {
            //validar si el dato ingresado tiene el formato de una IPV4
            if(validarIP(fbinding.etIP.text.toString())==true){
                //Navegar a otro fragmento, con el envio de un dato de este fragmento
                Navigation.findNavController(fbinding.root).navigate(R.id.action_frmInicio_to_frmMostrarCoor, Bundle().apply {
                    putString("ip", fbinding.etIP.text.toString())
                })
            }else{
                Toast.makeText(activity, "Formato de IP incorrecto", Toast.LENGTH_LONG).show()
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun validarIP(ip: String): Boolean{
        return InetAddresses.isNumericAddress(ip)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FrmInicio.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FrmInicio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}