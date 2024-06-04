package com.example.preexamenc1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText etNombre, etHorasNormales, etHorasExtras;
    private RadioButton rbAuxiliar, rbAlbanil, rbIngObra;
    private Button btnCalcular, btnLimpiar, btnSalir;
    private TextView tvSubtotal, tvImpuesto, tvTotalPagar, tvNumRecibo;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de los elementos de la UI
        etNombre = findViewById(R.id.txtNombre);
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

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularNomina();
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
                finish(); // Cierra la aplicación
            }
        });

    }

    private void calcularNomina() {
        String nombre = etNombre.getText().toString();
        float horasNormales = Float.parseFloat(etHorasNormales.getText().toString());
        float horasExtras = Float.parseFloat(etHorasExtras.getText().toString());
        int puesto = obtenerPuesto();
        float pagoBase = calcularPagoBase(puesto);
        float subtotal = (pagoBase * horasNormales) + (horasExtras * pagoBase * 2);
        float impuesto = subtotal * 0.16f; // 16% de impuesto
        float totalPagar = subtotal - impuesto;

        tvSubtotal.setText(String.format("Subtotal: %.2f", subtotal));
        tvImpuesto.setText(String.format("Impuesto: %.2f", impuesto));
        tvTotalPagar.setText(String.format("Total a Pagar: %.2f", totalPagar));
    }

    private int obtenerPuesto() {
        if (rbAuxiliar.isChecked()) return 1;
        if (rbAlbanil.isChecked()) return 2;
        if (rbIngObra.isChecked()) return 3;
        return 0; // Valor por defecto si no se selecciona ningún puesto
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
        etNombre.setText("");
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

