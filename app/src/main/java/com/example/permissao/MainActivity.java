package com.example.permissao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnMicrofone, btnArmazenamento;
    private static final int CODE_REQUEST = 1;
    private static final String PERMISSION_MICROFONE = Manifest.permission.RECORD_AUDIO;
    private static final String PERMISSION_ARMAZENAMENTO = Manifest.permission.MANAGE_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMicrofone = findViewById(R.id.btnMicrofone);
        btnArmazenamento = findViewById(R.id.btnArmazenamento);


    }

    public void solicitarPermissao(String permissao) {
        int temPermissao = ContextCompat.checkSelfPermission(this, permissao);

        if (temPermissao != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permissao}, CODE_REQUEST);
        } else {
            if (permissao.equals(Manifest.permission.RECORD_AUDIO))
                chamarActivity(Microfone.class);
            else
                chamarActivity(Armazenamento.class);
        }
    }

    private void chamarActivity(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissions[0].equals(Manifest.permission.RECORD_AUDIO))
                    chamarActivity(Microfone.class);
                else
                    chamarActivity(Armazenamento.class);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (permissions[0].equals(Manifest.permission.RECORD_AUDIO)) {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_MICROFONE);
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Atenção")
                            .setMessage("A permissão é necessaria pra acessar o microfone")
                            .setCancelable(false)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSION_MICROFONE}, CODE_REQUEST);
                                }
                            })
                            .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "É necessario a permissao para acessar o microfone!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_ARMAZENAMENTO);
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Atenção")
                            .setMessage("A permissão é necessaria pra acessar o ARMAZENAMENTO")
                            .setCancelable(false)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSION_ARMAZENAMENTO}, CODE_REQUEST);
                                }
                            })
                            .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "É necessario a permissao para acessar o ARMAZENAMENTO!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            } else {
                finish();
            }
        }
    }

    public void clickMicrofone(View view){
        solicitarPermissao(Manifest.permission.RECORD_AUDIO);
    }

    public void clickArmazenamento(View view){
        solicitarPermissao(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
    }
}