package com.example.financeview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.etEmail);
        EditText senha = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // 🔹 Quando clicar em "Entrar"
        btnLogin.setOnClickListener(v -> {
            String user = email.getText().toString();
            String pass = senha.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔹 Simula login local
            if (user.equals("teste@teste.com") && pass.equals("123")) {
                Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                // 🔹 Chama API antes de ir pra próxima tela
                testarConexaoAPI();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 🔹 Função que testa comunicação com uma API pública
    private void testarConexaoAPI() {
        new Thread(() -> {
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/users/1");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    // Exibe o resultado da API no Toast (thread principal)
                    runOnUiThread(() ->
                            Toast.makeText(this, "Conexão bem-sucedida com a API!", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Erro na API: código " + responseCode, Toast.LENGTH_SHORT).show()
                    );
                }

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Falha na conexão: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}
