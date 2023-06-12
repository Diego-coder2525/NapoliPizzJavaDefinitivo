package cibertec.elequipo.napolipizzjavadefinitivo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private EditText userEditText;
    private EditText contrasenaEditText;
    private EditText nombreEditText;
    private EditText emailEditText;
    private EditText direccionEditText;
    private EditText telefonoEditText;
    private Button registrarButton;
    private RequestQueue requestQueue;
    private static final String REGISTRO_URL = "http://192.168.1.5/movil/save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar vistas
        contrasenaEditText = findViewById(R.id.contrasenaEditText);
        nombreEditText = findViewById(R.id.nombreEditText);
        emailEditText = findViewById(R.id.emailEditText);
        direccionEditText = findViewById(R.id.direccionEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        registrarButton = findViewById(R.id.registrarButton);

        requestQueue = Volley.newRequestQueue(this);

        // Configurar click listener para el botón de registro
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String contrasena = contrasenaEditText.getText().toString().trim();
                final String nombre = nombreEditText.getText().toString().trim();
                final String email = emailEditText.getText().toString().trim();
                final String direccion = direccionEditText.getText().toString().trim();
                final String telefono = telefonoEditText.getText().toString().trim();

                // Validar campos vacíos
                if (TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(nombre) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(direccion) || TextUtils.isEmpty(telefono)) {
                    Toast.makeText(RegistroActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear la solicitud de registro utilizando StringRequest de Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRO_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Manejar la respuesta del servidor
                                if (response.equals("Registro exitoso")) {
                                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                    // Puedes agregar aquí el código para dirigir al usuario a la siguiente actividad después del registro
                                } else {
                                    Toast.makeText(RegistroActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Manejar el error de la solicitud
                                Toast.makeText(RegistroActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // Pasar los parámetros de registro al servidor
                        Map<String, String> params = new HashMap<>();
                        params.put("Contrasena", contrasena);
                        params.put("Nombre", nombre);
                        params.put("Email", email);
                        params.put("Direccion", direccion);
                        params.put("Telefono", telefono);
                        return params;
                    }
                };

                // Agregar la solicitud a la cola de solicitudes de Volley
                requestQueue.add(stringRequest);
            }
        });
    }
}
