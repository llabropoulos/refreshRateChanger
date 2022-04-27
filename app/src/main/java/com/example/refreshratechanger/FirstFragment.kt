package com.example.refreshratechanger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.refreshratechanger.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import java.io.BufferedReader
import java.io.InputStreamReader

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
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lockTo90Button.setOnClickListener {
            var cmd = "settings put global oneplus_screen_refresh_rate 1"
            runCommand(cmd)
            val snack = Snackbar.make(view,cmd,Snackbar.LENGTH_LONG)
            snack.show()
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.resetButton.setOnClickListener {
            var cmd = "settings put global oneplus_screen_refresh_rate 0"
            runCommand(cmd)
            val snack = Snackbar.make(view,cmd,Snackbar.LENGTH_LONG)
            snack.show()
        }
        //other devices:
        //adb shell settings put system peak_refresh_rate 1
        //adb shell settings put system min_refresh_rate 1
        binding.lockToPeakButton.setOnClickListener {
            var cmd = "settings put global oneplus_screen_refresh_rate 0"
            runCommand(cmd)

            var cmdTwo = "settings put global oneplus_screen_refresh_rate 0"
            runCommand(cmdTwo)

            val snack = Snackbar.make(view,cmd + "\n" + cmdTwo,Snackbar.LENGTH_LONG)
            snack.show()
        }

    }

    private fun runCommand(cmd: String): String {
        Log.d("runCommand", cmd)

        val cmdOut = StringBuffer()
        val process: Process
        try {
            process = Runtime.getRuntime().exec(cmd)
            val r = InputStreamReader(process.inputStream)
            val bufReader = BufferedReader(r)
            val buf = CharArray(4096)
            var nRead = 0
            while (bufReader.read(buf).also { nRead = it } > 0) {
                cmdOut.append(buf, 0, nRead)
            }
            bufReader.close()
            try {
                process.waitFor()
            } catch (e: InterruptedException) {
                Log.d("catch", "1")
                e.printStackTrace()
            }
        } catch (e: Exception) {
            Log.d("catch", "2")
            e.printStackTrace()
        }
        Log.d("runCommand", cmdOut.toString())
        return cmdOut.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}