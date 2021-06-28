package com.example.chat_proyecto_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chat_proyecto_final.R;
import com.example.chat_proyecto_final.adatermensajes;
import com.example.chat_proyecto_final.entidades.enviarmensaje;
import com.example.chat_proyecto_final.entidades.recibirmensaje;
import com.example.chat_proyecto_final.entidades.usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private CircleImageView foto_perfil;
    private TextView nombre;
    private RecyclerView rvmensajes;
    private EditText txtmensaje;
    private Button btnenviar, cerrarsesion;
    private ImageButton btnenviarfoto;

    private adatermensajes adapter;

    private FirebaseDatabase database;
    private DatabaseReference databasereference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoperfilcadena;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private FirebaseAuth mAuth;
    private String NOMBRE_USUARIO; //llama el nombre de la bdd

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foto_perfil = (CircleImageView) findViewById(R.id.foto_perfil);
        nombre = (TextView) findViewById(R.id.nombre);
        rvmensajes = (RecyclerView) findViewById(R.id.rvmensajes);
        txtmensaje = (EditText) findViewById(R.id.txtmensaje);
        btnenviar = (Button) findViewById(R.id.btnenviar);
        btnenviarfoto = (ImageButton) findViewById(R.id.btnenviarfoto);
        cerrarsesion = (Button) findViewById(R.id.cerrarsesion);
        fotoperfilcadena = "";


        database = FirebaseDatabase.getInstance();
        databasereference = database.getReference("chat_new");//sala de chat v2

        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();


        adapter = new adatermensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvmensajes.setLayoutManager(l);
        rvmensajes.setAdapter(adapter);

        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adapter.agregarmensaje(new mensaje(txtmensaje.getText().toString(), nombre.getText().toString(),"","1","00:00"));
                databasereference.push().setValue(new enviarmensaje(txtmensaje.getText().toString(), NOMBRE_USUARIO, fotoperfilcadena, "1", ServerValue.TIMESTAMP));
                txtmensaje.setText("");
            }
        });

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                returnLogin();
            }
        });

        btnenviarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), PHOTO_SEND);
            }
        });


        //cambiar la foto de perfil
        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una foto de perfil"), PHOTO_PERFIL);
            }
        });


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setActionBar();
            }
        });

        databasereference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                recibirmensaje m = snapshot.getValue(recibirmensaje.class);
                adapter.agregarmensaje(m);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        verifyStoragePermissions(this);
    }//OnCrete

    private void setActionBar() {
        rvmensajes.scrollToPosition(adapter.getItemCount() - 1);
    }

    //los permisos para las imagenes y cel
    public static boolean verifyStoragePermissions(Activity activity){
        String[] PERMISSIONS_STORAGE ={
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int REQUEST_EXTERNAL_STORAGE =1;
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_del_chat");
            final StorageReference fotoreferencia = storageReference.child(u.getLastPathSegment());
            fotoreferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri u = taskSnapshot.getUploadSessionUri(); //aqui deberia descargar la urt getDownloadUrl()
                    enviarmensaje m = new enviarmensaje(NOMBRE_USUARIO + " te a enviado una foto", u.toString(), NOMBRE_USUARIO, fotoperfilcadena, "2", ServerValue.TIMESTAMP);
                    databasereference.push().setValue(m);
                }
            });
        } else if (requestCode == PHOTO_PERFIL && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("foto_perfil");
            final StorageReference fotoreferencia = storageReference.child(u.getLastPathSegment());
            fotoreferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri u = taskSnapshot.getUploadSessionUri(); //aqui deberia descargar la urt getDownloadUrl()
                    fotoperfilcadena = u.toString();
                    enviarmensaje m = new enviarmensaje(NOMBRE_USUARIO + " actualizo su foto de perfil", u.toString(), NOMBRE_USUARIO, fotoperfilcadena, "2", ServerValue.TIMESTAMP);
                    databasereference.push().setValue(m);
                    Glide.with(MainActivity.this).load(u.toString()).into(foto_perfil);
                }
            });
        }
    }// del onactivityresult

    @Override
    protected void onResume() {
        super.onResume();
        // hay que reclamar los datos de login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            btnenviar.setEnabled(false);//inhabilitado sin los datos
            DatabaseReference reference = database.getReference("Usuarios/" +  currentUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot snapshot) {
                    usuario usuario = snapshot.getValue(usuario.class);
                    NOMBRE_USUARIO = usuario.getNombre();
                    //Toast.makeText(MainActivity.this, "Nombre" +  usuario.getNombre(), Toast.LENGTH_SHORT).show();
                    nombre.setText(NOMBRE_USUARIO);
                    btnenviar.setEnabled(true); //ya tiene datos se habilita
                }

                @Override
                public void onCancelled( DatabaseError error) {

                }
            });
        }else{
            returnLogin();
        }
    }

    private void returnLogin(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
} // clase principal