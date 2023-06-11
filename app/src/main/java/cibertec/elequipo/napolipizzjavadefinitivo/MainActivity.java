package cibertec.elequipo.napolipizzjavadefinitivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.Room;
//import cibertec.elequipo.napolipizz.database.AppDatabase;
//import cibertec.elequipo.napolipizz.database.User;
//import cibertec.elequipo.napolipizz.database.UserDao;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private TextView registerTextView;
    //private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        registerTextView = findViewById(R.id.registerTextView);

        // Inicializar base de datos
        /*AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-database")
                .allowMainThreadQueries() // Solo para fines de prueba, no lo uses en producción
                .build();
        userDao = db.userDao();*/

        // Configurar click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Verificar las credenciales en la base de datos
                /*User user = userDao.getUserByEmail(email);
                if (user != null && user.getPassword().equals(password)) {
                    // Credenciales válidas, iniciar la siguiente actividad
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Credenciales inválidas, mostrar mensaje de error
                    Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        // ...
    }
}
