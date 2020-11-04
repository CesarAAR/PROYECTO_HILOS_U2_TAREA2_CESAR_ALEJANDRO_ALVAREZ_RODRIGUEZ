package com.example.proyecto_hilos_u2_tarea2_cesar_alejandro_alvarez_rodriguez

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var imagenes = arrayOf(R.drawable.imagen7,R.drawable.imagen2,R.drawable.imagen3,R.drawable.imagen4,
        R.drawable.imagen5,R.drawable.imagen6,R.drawable.imagen8,R.drawable.imagen9,R.drawable.imagen1)
    var hilito = Hilo(this)
    var contadorHilo =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonInicio.setOnClickListener {
            try {
                hilito.start()
            }catch (e:Exception){
                AlertDialog.Builder(this).setTitle("ATENCION").setMessage("El hilo no se puede " +
                        "volver a ejecutar").setPositiveButton("ok"){d,i->d.dismiss()}.show()
            }
        }

        buttonPausa.setOnClickListener {
            hilito.PausarHilo()
        }

        buttonTerminar.setOnClickListener {
            hilito.TerminarHilo()
        }

        buttonReiniciar.setOnClickListener {
            hilito = Hilo(this)
            contadorHilo=0
            hilito.start()
            textView.setTextColor(Color.BLACK)
            textView.setText("Estado: ")
        }
    }
}

class Hilo(p:MainActivity) : Thread(){
    /*var imagenes = arrayOf(R.drawable.imagen7,R.drawable.imagen2,R.drawable.imagen3,R.drawable.imagen4,
        R.drawable.imagen5,R.drawable.imagen6,R.drawable.imagen8,R.drawable.imagen9,R.drawable.imagen1)*/
    var puntero = p
    var mantener = true
    var despausar = true


    fun TerminarHilo(){
        mantener= false
    }

    fun PausarHilo(){
        despausar = !despausar
    }

    override fun run() {
        super.run()
        /*Metodo que funciona similar al onTick, es decir, esta siempe en ejecucion, siempre que
        * se cicle, run solo se ejecuta una vez en segundo plano*/
        while (mantener){

            if(despausar == true) {
                if(puntero.contadorHilo>=8){
                    puntero.textView.text = "Estado: REQUIERE REINICIO"
                    puntero.textView.setTextColor(Color.RED)
                    mantener=false
                }else{
                    puntero.contadorHilo++
                    puntero.runOnUiThread() {
                        puntero.imageView.setImageResource(puntero.imagenes[puntero.contadorHilo])
                    }
                }
            }
            //Al HILO LE AFECTA EL ESTRES
            sleep(2000)
        }

    }
}