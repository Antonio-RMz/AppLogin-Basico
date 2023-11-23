package com.example.applogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    //elementos xml
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    //elemento para firebase de la documentacion
    FirebaseAuth mAuth;
    //elemento circulo progreso
    ProgressBar progressBar;
    //elemento texto para registrarse o iniciar sesion
    TextView textView;

//metodo obtenmido de la documentacion de firebase para > Cuando inicialices tu actividad, verifica si el usuario ya accedió
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //AQUI SE INDICA QUE SI PASO EL LOGEO ACCEDA A LA VENTANA PRINCIPAL (comprobacion de firebase )
            Intent intent= new Intent(getApplicationContext(),ActividadInicio.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail= findViewById(R.id.email);
        editTextPassword = findViewById(R.id.contraseña);
        buttonLogin= findViewById(R.id.btn_login);
        progressBar=findViewById(R.id.circuloProgreso);
        textView= findViewById(R.id.registerNow);


//accion para cuando se de clic en el texto
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login es el nombre de la clase
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });

        //////////////////////////////////////////////////////////////////

        //oyente al hacer clic en el boton
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //agregar la visibilidad del circulo de progreso
                progressBar.setVisibility(View.VISIBLE);
                //aqui se le la informacion
                String email, contraseña;
                //ahora se tiene que leer la contrasenia y el email
                email= editTextEmail.getText().toString();
                contraseña= editTextPassword.getText().toString();

//verificar si el correo es correcto
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(login.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(contraseña)){
                    Toast.makeText(login.this, "Enter contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, contraseña)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //barra de progreso
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    // si se entro con exito mostrar
                                    Toast.makeText(getApplicationContext(), "Inicio se sesion correcto", Toast.LENGTH_SHORT).show();

                                    //AQUI SE INDICA QUE SI PASO EL LOGEO ACCEDA A LA VENTANA PRINCIPAL
                                    Intent intent= new Intent(getApplicationContext(),ActividadInicio.class);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });



            }
        });
    }
}