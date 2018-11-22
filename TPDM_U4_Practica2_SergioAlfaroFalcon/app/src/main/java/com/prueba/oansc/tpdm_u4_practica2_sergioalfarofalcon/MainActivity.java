package com.prueba.oansc.tpdm_u4_practica2_sergioalfarofalcon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView casilla00, casilla01, casilla02, casilla10, casilla11, casilla12, casilla20, casilla21, casilla22, casillaActual;
    TextView ganador;
    Button reiniciar;
    boolean esElTurnoDelJugador;
    int[][] tablero;
    Thread hilo;
    int filaActual, columnaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casilla00 = findViewById(R.id.I00);
        casilla01 = findViewById(R.id.I01);
        casilla02 = findViewById(R.id.I02);
        casilla10 = findViewById(R.id.I10);
        casilla11 = findViewById(R.id.I11);
        casilla12 = findViewById(R.id.I12);
        casilla20 = findViewById(R.id.I20);
        casilla21 = findViewById(R.id.I21);
        casilla22 = findViewById(R.id.I22);

        reiniciar = findViewById(R.id.reiniciar);

        esElTurnoDelJugador = true;

        tablero = new int[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++){
                tablero[i][j] = 0;
            }
        }

        ganador = findViewById(R.id.etiqueta);

        casilla00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(0,0, v);
            }
        });

        casilla01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(0,1, v);
            }
        });

        casilla02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(0,2, v);
            }
        });

        casilla10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(1,0, v);
            }
        });

        casilla11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(1,1, v);
            }
        });

        casilla12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(1,2, v);
            }
        });

        casilla20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(2,0, v);
            }
        });

        casilla21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(2,1, v);
            }
        });

        casilla22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillaSeleccionada(2,2, v);
            }
        });

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciar();
            }
        });

    }

    private void casillaSeleccionada(int renglon, int columna, View objeto){
        if (esElTurnoDelJugador) {
            if (tablero[renglon][columna] == 0) {
                ImageView a = (ImageView) objeto;
                a.setImageResource(R.drawable.equis);
                tablero[renglon][columna] = 1;
                esElTurnoDelJugador = !esElTurnoDelJugador;
                if (hayGanador()) {
                    ganador.setText("Ganaste");
                    return;
                }
                inicializarHilo();
                hilo.start();
            }
        }
    }

    private void inicializarHilo() {
        hilo = new Thread() {
            public void run() {
                pensarJugada();
                ubicarCasilla();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        casillaActual.setImageResource(R.drawable.circulo);
                    }
                });
                if (hayGanador()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ganador.setText("Perdiste");
                        }
                    });
                    return;
                }
                esElTurnoDelJugador = !esElTurnoDelJugador;
            }
        };
    }

    private void reiniciar () {
        esElTurnoDelJugador = true;
        tablero = new int[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++){
                tablero[i][j] = 0;
            }
        }
        casilla00.setImageResource(R.drawable.blanco);
        casilla01.setImageResource(R.drawable.blanco);
        casilla02.setImageResource(R.drawable.blanco);
        casilla10.setImageResource(R.drawable.blanco);
        casilla11.setImageResource(R.drawable.blanco);
        casilla12.setImageResource(R.drawable.blanco);
        casilla20.setImageResource(R.drawable.blanco);
        casilla21.setImageResource(R.drawable.blanco);
        casilla22.setImageResource(R.drawable.blanco);
        ganador.setText(".");
    }

    private void ubicarCasilla () {
        if (filaActual == 0 && columnaActual == 0){
            casillaActual = casilla00;
            return;
        }
        if (filaActual == 0 && columnaActual == 1){
            casillaActual = casilla01;
            return;
        }
        if (filaActual == 0 && columnaActual == 2){
            casillaActual = casilla02;
            return;
        }
        if (filaActual == 1 && columnaActual == 0){
            casillaActual = casilla10;
            return;
        }
        if (filaActual == 1 && columnaActual == 1){
            casillaActual = casilla11;
            return;
        }
        if (filaActual == 1 && columnaActual == 2){
            casillaActual = casilla12;
            return;
        }
        if (filaActual == 2 && columnaActual == 0){
            casillaActual = casilla20;
            return;
        }
        if (filaActual == 2 && columnaActual == 1){
            casillaActual = casilla21;
            return;
        }
        casillaActual = casilla22;
    }

    private void pensarJugada() {
        for (int i = 0; i < 3; i++) {
            if (puedoGanarEnLaFila(i)) {
                obtenerYMarcarEspacioLibreEnLaFila(i);
                return;
            }
            if (puedoGanarEnLaColumna(i)) {
                obtenerYMarcarEspacioLibreEnLaColumna(i);
                return;
            }
        }
        if(puedoGanarEnLaPrimeraDiagonal()) {
            obtenerYMarcarEspacioLibreEnLaPrimeraDiagonal();
            return;
        }
        if (puedoGanarEnLaSegundaDiagonal()) {
            obtenerYMarcarEspacioLibreEnLaSegundaDiagonal();
            return;
        }
        for (int i = 0; i < 3; i++) {
            if (mePuedenGanarEnLaFila(i)) {
                obtenerYMarcarEspacioLibreEnLaFila(i);
                return;
            }
            if (mePuedenGanarEnLaColumna(i)) {
                obtenerYMarcarEspacioLibreEnLaColumna(i);
                return;
            }
        }
        if(mePuedenGanarEnLaPrimeraDiagonal()) {
            obtenerYMarcarEspacioLibreEnLaPrimeraDiagonal();
            return;
        }
        if (mePuedenGanarEnLaSegundaDiagonal()) {
            obtenerYMarcarEspacioLibreEnLaSegundaDiagonal();
            return;
        }
        if (hayEsquinaLibre()) {
            obtenerYMarcarEspacioLibreEnLasEsquinas();
            return;
        }
        obtenerYMarcarCualquierEspacioLibre ();
    }

    private boolean hayGanador () {
        for (int i = 0; i < 3; i++) {
            if (hayGanadorEnLaFila(i)) {
                return true;
            }
            if (hayGanadorEnLaColumna(i)) {
                return true;
            }
        }
        if (hayGanadorEnLaPrimeraDiagonal()) {
            return true;
        }
        if (hayGanadorEnLaSegundaDiagonal()) {
            return true;
        }
        return false;
    }

    private int totalEnFila (int fila) {
        return tablero[fila][0] + tablero[fila][1] + tablero[fila][2];
    }

    private int totalEnColumna (int columna) {
        return tablero[0][columna] + tablero[1][columna] + tablero[2][columna];
    }

    private int totalEnPrimeraDiagonal () {
        return tablero[0][0] + tablero[1][1] + tablero[2][2];
    }

    private int totalEnSegundaDiagonal() {
        return tablero[0][2] + tablero[1][1] + tablero[2][0];
    }

    private boolean hayGanadorEnLaFila(int fila) {
        return totalEnFila(fila) == 3 || totalEnFila(fila) == 12;
    }

    private boolean hayGanadorEnLaColumna(int columna) {
        return totalEnColumna(columna) == 3 || totalEnColumna(columna) == 12;
    }

    private boolean hayGanadorEnLaPrimeraDiagonal () {
        return totalEnPrimeraDiagonal() == 3 || totalEnPrimeraDiagonal() == 12;
    }

    private boolean hayGanadorEnLaSegundaDiagonal () {
        return totalEnSegundaDiagonal() == 3 || totalEnSegundaDiagonal() == 12;
    }

    private boolean puedoGanarEnLaFila (int fila) {
        return totalEnFila(fila) == 8;
    }

    private boolean puedoGanarEnLaColumna (int columna) {
        return totalEnColumna(columna) == 8;
    }

    private boolean puedoGanarEnLaPrimeraDiagonal () {
        return totalEnPrimeraDiagonal() == 8;
    }

    private boolean puedoGanarEnLaSegundaDiagonal () {
        return totalEnSegundaDiagonal() == 8;
    }

    private boolean mePuedenGanarEnLaFila (int fila) {
        return totalEnFila(fila) == 2;
    }

    private boolean mePuedenGanarEnLaColumna (int columna) {
        return totalEnColumna(columna) == 2;
    }

    private boolean mePuedenGanarEnLaPrimeraDiagonal () {
        return totalEnPrimeraDiagonal() == 2;
    }

    private boolean mePuedenGanarEnLaSegundaDiagonal () {
        return totalEnSegundaDiagonal() == 2;
    }

    private boolean hayEsquinaLibre () {
        return tablero[0][0] == 0 || tablero[0][2] == 0 || tablero[2][0] == 0 || tablero[2][2] == 0;
    }

    private void obtenerYMarcarEspacioLibreEnLaFila (int fila) {
        for (int i = 0; i < 3; i++) {
            if (tablero[fila][i] == 0) {
                tablero[fila][i] = 4;
                filaActual = fila;
                columnaActual = i;
                break;
            }
        }
    }

    private void obtenerYMarcarEspacioLibreEnLaColumna (int columna) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][columna] == 0) {
                tablero[i][columna] = 4;
                filaActual = i;
                columnaActual = columna;
                break;
            }
        }
    }

    private void obtenerYMarcarEspacioLibreEnLaPrimeraDiagonal () {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][i] == 0) {
                tablero[i][i] = 4;
                filaActual = i;
                columnaActual = i;
                break;
            }
        }
    }

    private void obtenerYMarcarEspacioLibreEnLaSegundaDiagonal () {
        if (tablero[2][0] == 0) {
            tablero[2][0] = 4;
            filaActual = 2;
            columnaActual = 0;
        } else {
            if (tablero[1][1] == 0) {
                tablero[1][1] = 4;
                filaActual = 1;
                columnaActual = 1;
            } else {
                tablero[0][2] = 4;
                filaActual = 0;
                columnaActual = 2;
            }
        }
    }

    private void obtenerYMarcarEspacioLibreEnLasEsquinas () {
        if (tablero[0][0] == 0) {
            tablero[0][0] = 4;
            filaActual = 0;
            columnaActual = 0;
        } else {
            if (tablero[0][2] == 0) {
                tablero[0][2] = 4;
                filaActual = 0;
                columnaActual = 2;
            } else {
                if (tablero[2][0] == 0) {
                    tablero[2][0] = 4;
                    filaActual = 2;
                    columnaActual = 0;
                } else {
                    tablero[2][2] = 4;
                    filaActual = 2;
                    columnaActual = 2;
                }
            }
        }
    }

    private void obtenerYMarcarCualquierEspacioLibre () {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == 0) {
                    tablero[i][j] = 4;
                    filaActual = i;
                    columnaActual = j;
                }
            }
        }
    }

}
