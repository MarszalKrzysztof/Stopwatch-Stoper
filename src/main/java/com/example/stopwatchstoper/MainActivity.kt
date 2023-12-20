// Krzysztof Marszał
// Nr albumu:      161950
// Rok akademicki: 2023/24
// Grupa:          U2
// Rodzaj studiow: PUW
// Semestr:        1

package com.example.stopwatchstoper

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Switch
import android.widget.ImageView


class MainActivity : ComponentActivity() {

    // Zmienne przechowujące stan stopera
    private var seconds = 0
    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ustaw widok interfejsu użytkownika na ten zdefiniowany w pliku XML
        setContentView(R.layout.activity_stopwatch)

        // Zmiana rozmiaru obrazu na szerokość i wysokość 300 pikseli
        val imageView: ImageView = findViewById(R.id.ahe_logo)
        val layoutParams: ViewGroup.LayoutParams = imageView.layoutParams
        layoutParams.width = 300 // Ustaw szerokość na 300 pikseli
        layoutParams.height = 300 // Ustaw wysokość na 300 pikseli
        imageView.layoutParams = layoutParams

        // Jeżeli istnieje stan wcześniej zapisany, przywróć go
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
        }

        // Uruchom funkcję odmierzającą czas
        runTimer()

        // Ustawienie obsługi przycisku start
        val startButton: Button = findViewById(R.id.start_button)
        startButton.setOnClickListener {
            running = true
        }

        // Ustawienie obsługi przycisku stop
        val stopButton: Button = findViewById(R.id.stop_button)
        stopButton.setOnClickListener {
            running = false
        }

        // Ustawienie obsługi przycisku reset
        val resetButton: Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener {
            running = false
            seconds = 0
        }

        // Ustawienie obsługi przełącznika trybu ciemnego
        val switchDarkMode: Switch = findViewById(R.id.switch_dark_mode)
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Włącz tryb ciemny
                window.decorView.setBackgroundColor(getColor(R.color.colorBackgroundDark))
                switchDarkMode.setTextColor(getColor(R.color.colorTextLight))
            } else {
                // Włącz tryb jasny
                window.decorView.setBackgroundColor(getColor(R.color.colorBackgroundLight))
                switchDarkMode.setTextColor(getColor(R.color.colorTextDark))
            }
        }
    }

    // Zapisz aktualny stan stopera przed zniszczeniem aktywności
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
    }

    // Funkcja odmierzająca czas i aktualizująca interfejs użytkownika
    private fun runTimer() {
        val timeView: TextView = findViewById(R.id.time_view)
        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60

                // Formatowanie i wyświetlenie aktualnego czasu
                val time = String.format("%02d:%02d:%02d", hours, minutes, secs)
                timeView.text = time

                // Inkrementacja czasu, jeżeli stoper jest włączony
                if (running) {
                    seconds++
                }

                // Aktualizacja co sekundę
                handler.postDelayed(this, 1000)
            }
        })
    }
}