package cibertec.elequipo.napolipizzjavadefinitivo;

import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PizzaBuscarActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnEliminar, btnActualizar;
    EditText edNombrePizza,edDescripcion,edPrecio,edId;
    RequestQueue requestQueue;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_buscar);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("id");
        }

        requestQueue = Volley.newRequestQueue(this);
        //UI
        inicializarUI();
        leerPizza();
        btnEliminar.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);

        }

    private void inicializarUI() {
        edNombrePizza = findViewById(R.id.edNombrePizza);
        edDescripcion = findViewById(R.id.edDescripcion);
        edPrecio = findViewById(R.id.edPrecio);
        edId = findViewById(R.id.edId);

        //
        btnEliminar = findViewById(R.id.btn_eliminar);
        btnActualizar = findViewById(R.id.btn_actualizar);
    }

    private void leerPizza() {
        String URL = "http://192.168.56.1/NapoliPizzAndroid/listxid.php?ID=" + id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Obtener el primer objeto JSON del JSONArray
                            JSONObject pizza = response.getJSONObject(0);

                            String nombrePizza = pizza.getString("NombrePizza");
                            String descripcion = pizza.getString("Descripcion");
                            String precio = pizza.getString("Precio");

                            Log.d("DatosPizza", "NombrePizza: " + nombrePizza);
                            Log.d("DatosPizza", "Descripcion: " + descripcion);
                            Log.d("DatosPizza", "Precio: " + precio);

                            edNombrePizza.setText(nombrePizza);
                            edDescripcion.setText(descripcion);
                            edPrecio.setText(precio);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btn_actualizar){
            String nombrePizza = edNombrePizza.getText().toString().trim();
            String Descripcion = edDescripcion.getText().toString().trim();
            String Precio = edPrecio.getText().toString().trim();

            actualizar(nombrePizza,Descripcion,Precio);
        }
        else if(id == R.id.btn_eliminar){
            String idEl = edId.getText().toString().trim();
            eliminarPizza(idEl);
        }
    }

    private void actualizar(String nombrePizza, String descripcion, String precio) {
        String URL = "http://192.168.56.1/NapoliPizzAndroid/update.php" ;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PizzaBuscarActivity.this,"Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ID",id);
                params.put("NombrePizza",nombrePizza);
                params.put("Descripcion",descripcion);
                params.put("Precio",precio);
                return params;
            }
        };


    }

    private void eliminarPizza(String idEl) {
        String URL = "http://192.168.56.1/NapoliPizzAndroid/delete.php?ID=" + idEl;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
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
                params.put("ID",idEl);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
