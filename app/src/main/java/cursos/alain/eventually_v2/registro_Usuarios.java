package cursos.alain.eventually_v2;

        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;

        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.RetryPolicy;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;


        import java.net.URL;
        import java.util.HashMap;
        import java.util.Map;




        import java.util.HashMap;
        import java.util.Map;


public class registro_Usuarios extends AppCompatActivity {

    EditText Txt_UserName, Txt_Email, Txt_Contra, Txt_Contra_c;
    Button Btn_Registrar;
    RequestQueue requestQueue; //Definimos este request aquí ya que varios objetos lo pueden utilizar.




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__usuarios);

        Txt_UserName = (EditText) findViewById(R.id.Txt_UserName);
        Txt_Email = (EditText) findViewById(R.id.Txt_Email);
        Txt_Contra = (EditText) findViewById(R.id.Txt_Contra);
        Txt_Contra_c = (EditText) findViewById(R.id.Txt_Contra_c);
        Btn_Registrar = (Button) findViewById(R.id.Btn_Registrar);

        Btn_Registrar.setOnClickListener(new View.OnClickListener() { //Llamamos el botón para registrar usuarios.
            @Override
            public void onClick(View v) {

                //ejecutarServicio("https://eventually02.000webhostapp.com/registrar_usuario.php");
                ejecutarServicio("http://192.168.1.67/Eventually_01/Registrar_Usuario.php");
                //ejecutarServicio("http://192.168.1.65/Eventually_01/Registrar_Usuario.php");
                //ejecutarServicio("http://192.168.1.56/Eventually_01/Registrar_Usuario.php");

            }
        });

    }

    public void Login(View view){
        Intent Atras = new Intent(this, MainActivity.class);
        startActivity(Atras);
    }

    private void ejecutarServicio(String URL) { //Meotodo que enviará las peticiones al servidor.

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() { //Declararemos una petición declarando el tipo de llamada (POST como en el WS).

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registrado exitosamente ^^", Toast.LENGTH_SHORT).show();
                Log.d("cadena", response);
                Txt_UserName.setText("");
                Txt_Email.setText("");
                Txt_Contra.setText("");
                Txt_Contra_c.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Algo ha salido mal :c " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            //Generamos el metodo Getparams para definir los parametros que enviaremos a el servidor para lo que haremos uso de un objeto Math.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                //Meidante el método put, definimos los datos que vamos a enviar.

                parametros.put("UserName", Txt_UserName.getText().toString());
                parametros.put("E_Mail", Txt_Email.getText().toString());
                parametros.put("Contraseña", Txt_Contra.getText().toString());
                parametros.put("Confirmar_Contraseña", Txt_Contra_c.getText().toString());

                return parametros;

            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Aquí procesaremos las peticiones hechas por nuestra app para que la libreria se encargue de ejecutarlas.
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); //Aquí enviamos la solicitud agregando el objeto string request.

    }

}
