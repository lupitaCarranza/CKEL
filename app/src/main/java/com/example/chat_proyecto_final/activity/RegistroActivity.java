package com.example.chat_proyecto_final.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_proyecto_final.R;
import com.example.chat_proyecto_final.entidades.usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    EditText txtnombre, txtcorreo, txtcontraseña, txtrepecontraseña;
    Button btnregistrar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtnombre = (EditText) findViewById(R.id.registro_nombre);
        txtcontraseña = (EditText) findViewById(R.id.registro_contraseña);
        txtrepecontraseña = (EditText) findViewById(R.id.registro_contraseñarepe);
        txtcorreo = (EditText) findViewById(R.id.registro_correo);
        btnregistrar = (Button) findViewById(R.id.btn_registrar_registro);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //evento de registro
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = txtcorreo.getText().toString() ;
                String nombre = txtnombre.getText().toString();
                if(isValidEmail(correo) && validadcontraseña() && validarnombre(nombre)){
                    String contraseña = txtcontraseña.getText().toString();
                    mAuth.createUserWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegistroActivity.this, "Su Registro fue exitoso", Toast.LENGTH_SHORT).show();
                                        usuario usuario =  new usuario();
                                        usuario.setCorreo(correo);
                                        usuario.setNombre(nombre);
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        DatabaseReference reference = database.getReference("Usuarios/" + currentUser.getUid());
                                        reference.setValue(usuario);
                                        finish();
                                    } else{
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistroActivity.this, "A ocurrido un error al registarse", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                                }else{
                                    Toast.makeText(RegistroActivity.this, "Validaciones Funcionando", Toast.LENGTH_SHORT).show();
                                }
            }
        });
    } //llave del oncreate

        public boolean isValidEmail(CharSequence target){
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }

        public boolean validadcontraseña(){
        String contraseña;
        String contraseñarepetida;

        contraseña = txtcontraseña.getText().toString();
        contraseñarepetida = txtrepecontraseña.getText().toString();

            if(contraseña.equals(contraseñarepetida)){
                if(contraseña.length()>=6 && contraseña.length()<=16){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }// llave del validar contraseña

        public boolean validarnombre(String nombre){
            return !nombre.isEmpty();
        }

}//llave principal

