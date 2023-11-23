package com.example.applogin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Register extends AppCompatActivity {
//inicializacion de objetos
    //elementos xml
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
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

        //////////////////////////////////////////////////////
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
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        editTextEmail= findViewById(R.id.email);
        editTextPassword = findViewById(R.id.contraseña);
        buttonReg= findViewById(R.id.btn_register);
        progressBar=findViewById(R.id.circuloProgreso);
        textView= findViewById(R.id.loginNow);


//accion para cuando se de clic en el texto
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login es el nombre de la clase
            Intent intent = new Intent(getApplicationContext(),login.class);
            startActivity(intent);
            finish();
            }
        });
    buttonReg.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(contraseña)){
            Toast.makeText(Register.this, "Enter contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
//se crea el usuario
        mAuth.createUserWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //cuando el proceso de ee circulo de proceso termine se quita la visibilidad
                        progressBar.setVisibility(view.GONE);
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "Si se pudo pa",
                                    Toast.LENGTH_SHORT).show();
                            //login es el nombre de la clase
                            Intent intent = new Intent(getApplicationContext(),login.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }
});
    }
}