package com.example.applogin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActividadInicio extends AppCompatActivity {
FirebaseAuth auth;
Button button;
TextView textViewEgmail;

FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_inicio);
        auth= FirebaseAuth.getInstance();
        button= findViewById(R.id.cerrarSesion);
        textViewEgmail=findViewById(R.id.textEmail);


        ///se iniciara al usuario actual
        user = auth.getCurrentUser();
        if (user == null){
            //condicion que manda al logueo si no hay usuario, se finaliza la actividad y se manda a login class
            Intent intent= new Intent(getApplicationContext(),login.class);
            startActivity(intent);
            finish();
        }else {
            //esto configura el correo elctronico del usuario
            textViewEgmail.setText(user.getEmail());


        }
        //detector al hacer click al boton cerrar sesio
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //aqui se cierra sesion
                FirebaseAuth.getInstance().signOut();
                //cerrar la actividad actual y abrir la de inicio se sesion
                //condicion que manda al logueo si no hay usuario, se finaliza la actividad y se manda a login class

                Intent intent= new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}