package br.com.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import br.com.converter.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFirstBinding.inflate(inflater, container, false).apply {
        val spinner: Spinner = root.findViewById(R.id.converseType)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.converseType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener()
    }

    private fun listener(){
        binding.btnCalc.setOnClickListener {
            lateinit var phrase: String
            binding.run {
                if(txtFieldEditReal.text?.isNotEmpty() == true){
                    val result = calc(
                        txtFieldEditReal.text.toString(),
                        converseType.selectedItem.toString()
                    )
                    val symbol = coin(
                        converseType.selectedItem.toString()
                    )
                    val value = txtFieldEditReal.text.toString()
                    val formatValue = String.format("%.2f", value.toDouble())
                    phrase = "R$ $formatValue = $symbol $result"
                    tvResult.text = phrase
                    txtError.visibility = View.INVISIBLE
                } else {
                    txtError.visibility = View.VISIBLE
                }
            }
        }

        binding.btnClean.setOnClickListener {
            binding.run {
                txtFieldEditReal.setText("")
                tvResult.text = ""
            }
        }
    }

    private fun calc(vlReal: String, type: String): String {
        lateinit var result: String
        when(type){
            "Dólar Americano (4.744)" -> {
                result = converse(vlReal.toDouble(), 4.744)
            }
            "Euro (5.092)" -> {
                result = converse(vlReal.toDouble(), 5.092)
            }
            "Libra (5.999)" -> {
                result = converse(vlReal.toDouble(), 5.999)
            }
            "Franco (4.962)" -> {
                result = converse(vlReal.toDouble(), 4.962)
            }
            "Peso Argentino (0.039)" -> {
                result = converse(vlReal.toDouble(), 0.039)
            }
            else -> Unit
        }
        return result
    }

    private fun coin(type: String): String {
        lateinit var symbol: String
        when(type){
            "Dólar Americano (4.744)" -> {
                symbol = "U$"
            }
            "Euro (5.092)" -> {
                symbol = "€"
            }
            "Libra (5.999)" -> {
                symbol = "£"
            }
            "Franco (4.962)" -> {
                symbol = "Fr"
            }
            "Peso Argentino (0.039)" -> {
                symbol = "$"
            }
            else -> Unit
        }
        return symbol
    }

    private fun converse(vlReal: Double, vlPrice: Double) : String{
        val result = vlReal / vlPrice
        return String.format("%.2f", result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}