package com.example.preexamenc1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import java.text.NumberFormat;
import java.util.Locale;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText  etHorasNormales, etHorasExtras;
    private RadioButton rbAuxiliar, rbAlbanil, rbIngObra;
    private Button btnCalcular, btnLimpiar, btnSalir;
    private TextView  tvNombreTrabajador, tvSubtotal, tvImpuesto, tvTotalPagar, tvNumRecibo;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNombreTrabajador = findViewById(R.id.txtombreTrabajador);
        etHorasNormales = findViewById(R.id.txtHorasNormales);
        etHorasExtras = findViewById(R.id.txtHorasExtras);
        rbAuxiliar = findViewById(R.id.rdbAuxiliar);
        rbAlbanil = findViewById(R.id.rdbAlbanil);
        rbIngObra = findViewById(R.id.rdbIngObra);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnSalir = findViewById(R.id.btnSalir);
        tvSubtotal = findViewById(R.id.lblSubtotal);
        tvImpuesto = findViewById(R.id.lblImpuesto);
        tvTotalPagar = findViewById(R.id.lblTotalPagar);
        tvNumRecibo = findViewById(R.id.lblNumRecibo);

        int numRecibo = random.nextInt(999999);
        tvNumRecibo.setText(String.format("Número de Recibo: %06d", numRecibo));

        String nombreTrabajador = getIntent().getStringExtra("nombreTrabajador");
        tvNombreTrabajador.setText(String.format("Nombre del Trabajador: %s", nombreTrabajador));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularNomina(currencyFormat);
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void calcularNomina(NumberFormat currencyFormat) {

        if (etHorasNormales.getText().toString().isEmpty() || etHorasExtras.getText().toString().isEmpty()) {
            // Mostrar un mensaje de advertencia
            Toast.makeText(MainActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!rbAuxiliar.isChecked() && !rbAlbanil.isChecked() && !rbIngObra.isChecked()) {
            Toast.makeText(MainActivity.this, "Por favor, seleccione un puesto.", Toast.LENGTH_LONG).show();
            return;
        }


        float horasNormales = Float.parseFloat(etHorasNormales.getText().toString());
        float horasExtras = Float.parseFloat(etHorasExtras.getText().toString());
        int puesto = obtenerPuesto();
        float pagoBase = calcularPagoBase(puesto);
        float subtotal = (pagoBase * horasNormales) + (horasExtras * pagoBase * 2);
        float impuesto = subtotal * 0.16f;
        float totalPagar = subtotal - impuesto;

        tvSubtotal.setText(String.format("Subtotal: %s", currencyFormat.format(subtotal)));
        tvImpuesto.setText(String.format("Impuesto: %s", currencyFormat.format(impuesto)));
        tvTotalPagar.setText(String.format("Total a Pagar: %s", currencyFormat.format(totalPagar)));
    }

    private int obtenerPuesto() {
        if (rbAuxiliar.isChecked()) return 1;
        if (rbAlbanil.isChecked()) return 2;
        if (rbIngObra.isChecked()) return 3;
        return 0;
    }

    private float calcularPagoBase(int puesto) {
        float pagoBase = 200;
        switch (puesto) {
            case 1:
                return pagoBase * 1.20f; // Incremento del 20%
            case 2:
                return pagoBase * 1.50f; // Incremento del 50%
            case 3:
                return pagoBase * 2.00f; // Incremento del 100%
            default:
                return pagoBase; // Pago base sin incremento
        }
    }

    private void limpiarCampos() {
        etHorasNormales.setText("");
        etHorasExtras.setText("");
        rbAuxiliar.setChecked(false);
        rbAlbanil.setChecked(false);
        rbIngObra.setChecked(false);
        tvSubtotal.setText("");
        tvImpuesto.setText("");
        tvTotalPagar.setText("");
    }
}

