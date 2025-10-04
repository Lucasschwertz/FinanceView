package com.example.financeview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGastos = findViewById(R.id.btnConsultarGastos);
        Button btnReceitas = findViewById(R.id.btnConsultarReceitas);
        Button btnRelatorios = findViewById(R.id.btnRelatorios);
        TextView tvResultado = findViewById(R.id.tvResultadoAPI);

        btnGastos.setOnClickListener(v ->
                Toast.makeText(this, "Listando gastos (exemplo genérico)", Toast.LENGTH_SHORT).show()
        );

        btnReceitas.setOnClickListener(v ->
                Toast.makeText(this, "Exibindo receitas (exemplo genérico)", Toast.LENGTH_SHORT).show()
        );

        btnRelatorios.setOnClickListener(v -> {
            tvResultado.setText("Consultando API...");
            new Thread(() -> {
                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    runOnUiThread(() -> tvResultado.setText("API OK:\n" + response));

                } catch (Exception e) {
                    runOnUiThread(() -> tvResultado.setText("Erro na API: " + e.getMessage()));
                }
            }).start();
        });
    }
}
