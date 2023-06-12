package cibertec.elequipo.napolipizzjavadefinitivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class PizzaActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edNombrePizza,edDescripcion,edPrecio,edId,edBuscar;
    RequestQueue requestQueue;
    private static final String URL = "http://192.168.56.1/NapoliPizzAndroid/save.php";
    Button btnCrear, btnBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_crud);

        requestQueue = Volley.newRequestQueue(this);
        //UI
        inicializarUI();

        btnCrear.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
    }



    private void inicializarUI() {
        edNombrePizza = findViewById(R.id.edNombrePizza);
        edDescripcion = findViewById(R.id.edDescripcion);
        edPrecio = findViewById(R.id.edPrecio);
        edId = findViewById(R.id.edId);
        edBuscar = findViewById(R.id.ed_buscar);
        //
        btnCrear = findViewById(R.id.btn_actualizar);
        btnBuscar = findViewById(R.id.btn_eliminar);
    }


    @Override
    public void onClick(View view) {
        //Identificador de la vista
        int id = view.getId();

        if(id == R.id.btn_actualizar){
            String nombrePizza = edNombrePizza.getText().toString().trim();
            String descripcion = edDescripcion.getText().toString().trim();
            String precio = edPrecio.getText().toString().trim();
            //String Id = edNombrePizza.getText().toString().trim();

            crearPizza(nombrePizza,descripcion,precio);
        }else if(id == R.id.btn_eliminar){
            Intent intent = new Intent(PizzaActivity.this,PizzaBuscarActivity.class);
            intent.putExtra("id",edBuscar.getText().toString().trim());
            startActivity(intent);
        }
    }

    private void crearPizza(String nombrePizza, String descripcion, String precio) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PizzaActivity.this,"Correcto!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("NombrePizza",nombrePizza);
                params.put("Descripcion",descripcion);
                params.put("Precio",precio);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
