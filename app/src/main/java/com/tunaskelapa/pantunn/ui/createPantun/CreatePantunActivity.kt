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
            val bait3 = binding.etInput3.text.toString()
                .lowercase(Locale.getDefault())
                .trim()
            val bait4 = binding.etInput4.text.toString()
                .lowercase(Locale.getDefault())
                .trim()

            val message = bait3+bait4
            Log.d("message", message)
            if ( !TextUtils.isEmpty( message ) ){
                // Tokenize and pad the given input text.
                val tokenizedMessage = classifier.tokenize( message )
                val paddedMessage = classifier.padSequence( tokenizedMessage )

                val results = classifySequence( paddedMessage )
                val class1 = results[0]
                val class2 = results[1]
                val class3 = results[2]
                val class4 = results[3]
                val class5 = results[4]
                val class6 = results[5]
                val class7 = results[6]
                val class8 = results[7]
                val class9 = results[8]
                val class10 = results[9]

                val result = mapOf(
                    "$class1" to "Pantun Adat dan Alam",
                    "$class2" to "Pantun Agama",
                    "$class3" to "Pantun Anak-anak",
                    "$class4" to "Pantun Budi",
                    "$class5" to "Pantun Cinta",
                    "$class6" to "Pantun Jenaka",
                    "$class7" to "Pantun Nasihat",
                    "$class8" to "Pantun Orangtua",
                    "$class9" to "Pantun Pendidikan",
                    "$class10" to "Pantun Teka-Teki"
                )

                val hasil = listOf(class1, class2, class3, class4, class5, class6,
                    class7, class8, class9, class10)
                val max = hasil.maxOrNull() ?: 0
                Log.d("Result", max.toString())
                val theResult = result["$max"]
                binding.result.text = theResult

            }
            else{
                Toast.makeText( this, "Pantun tidak boleh kosong.", Toast.LENGTH_LONG).show()
            }
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