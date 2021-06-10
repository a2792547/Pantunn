package com.tunaskelapa.pantunn.ui.createPantun

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tunaskelapa.pantunn.databinding.ActivityCreatePantunBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class CreatePantunActivity : AppCompatActivity() {
    private val MODEL_ASSETS_PATH = "model_5.tflite"
    private val INPUT_MAXLEN = 120
    private var tfLiteInterpreter : Interpreter? = null
    private lateinit var binding : ActivityCreatePantunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePantunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val classifier = Classifier(this, "word_dict.json", INPUT_MAXLEN)
        tfLiteInterpreter = Interpreter(loadModelFile())

        classifier.processVocab(object : Classifier.VocabCallback {
            override fun onVocabProcessed() {
                Log.d("Process", "Proses selesai")
            }
        })

        binding.btnAnalyze.setOnClickListener {
            val bait1 = binding.etInput1.text.toString()
                .lowercase(Locale.getDefault())
                .trim()
            val bait2 = binding.etInput2.text.toString()
                .lowercase(Locale.getDefault())
                .trim()
            val bait3 = binding.etInput3.text.toString()
                .lowercase(Locale.getDefault())
                .trim()
            val bait4 = binding.etInput4.text.toString()
                .lowercase(Locale.getDefault())
                .trim()

            val message = bait3+bait4
            val entry = bait1+bait2+bait3+bait4
            when {
                (TextUtils.isEmpty(entry)) -> Toast.makeText( this, "Pantun tidak boleh kosong.", Toast.LENGTH_LONG).show()
                (bait1 == "") -> Toast.makeText( this, "Bait pertama tidak boleh kosong.", Toast.LENGTH_LONG).show()
                (bait2 == "") -> Toast.makeText( this, "Bait kedua tidak boleh kosong.", Toast.LENGTH_LONG).show()
                (bait3 == "") -> Toast.makeText( this, "Bait ketiga tidak boleh kosong.", Toast.LENGTH_LONG).show()
                (bait4 == "") -> Toast.makeText( this, "Bait keempat tidak boleh kosong.", Toast.LENGTH_LONG).show()
                (!TextUtils.isEmpty(entry)) -> {
                    val tokenizedMessage = classifier.tokenize( message )
                    val paddedMessage = classifier.padSequence( tokenizedMessage )
                    val results = classifySequence( paddedMessage )
                    val result = mapOf(
                        "${results[0]}" to "Pantun Adat dan Alam",
                        "${results[1]}" to "Pantun Agama",
                        "${results[2]}" to "Pantun Anak-anak",
                        "${results[3]}" to "Pantun Budi",
                        "${results[4]}" to "Pantun Cinta",
                        "${results[5]}" to "Pantun Jenaka",
                        "${results[6]}" to "Pantun Nasihat",
                        "${results[7]}" to "Pantun Orangtua",
                        "${results[8]}" to "Pantun Pendidikan",
                        "${results[9]}" to "Pantun Teka-Teki"
                    )

                    val hasil = listOf(results[0], results[1], results[2], results[3], results[4], results[5],
                        results[6], results[7], results[8], results[9])
                    val max = hasil.maxOrNull() ?: 0
                    Log.d("Result", max.toString())
                    val theResult = result["$max"]
                    binding.result.text = theResult
                }
            }
        }
        binding.arrowBack.setOnClickListener {
            finish()
        }
    }

    private fun classifySequence(sequence: IntArray): FloatArray {
        val inputs : Array<FloatArray> = arrayOf( sequence.map { it.toFloat() }.toFloatArray() )
        val outputs : Array<FloatArray> = arrayOf( FloatArray( 10 ) )
        tfLiteInterpreter?.run( inputs , outputs )
        return outputs[0]
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val assetFileDescriptor = assets.openFd(MODEL_ASSETS_PATH)
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}