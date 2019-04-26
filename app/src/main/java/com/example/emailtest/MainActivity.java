package com.example.emailtest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView emailcompleto,testing;
    EditText email;
    TextView finaldo;
    Spinner emaildomains;
    ArrayList<String> defalult_domains = new ArrayList<>();
    ArrayList<String> new_domains = new ArrayList<>();
    ArrayList<String> email_create = new ArrayList<>();
    ArrayAdapter<String> adapter_auto;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailcompleto = findViewById(R.id.result);
        testing =  findViewById(R.id.textView);
        emaildomains = findViewById(R.id.domains);
        email = findViewById(R.id.email);
        finaldo = findViewById(R.id.email2);

        defalult_domains.addAll(Arrays.asList("gmail.com", "hotmail.com", "yahoo.com", "outlook.es", "outlook.com", "zohomail.eu","Otro..."));
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, defalult_domains);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emaildomains.setAdapter(adapter);

        actualizarDatos(1);
        email.addTextChangedListener(new TextWatcher() {

                                         public void afterTextChanged(Editable s) {
                                         }

                                         public void beforeTextChanged(CharSequence s, int start,
                                                                       int count, int after) {
                                         }

                                         public void onTextChanged(CharSequence s, int start,
                                                                   int before, int count) {
                                             if(defalult_domains.get(emaildomains.getSelectedItemPosition()).equals("Otro...")){
                                                 actualizarDatos(2);
                                             }
                                             else {
                                                 actualizarDatos(1);
                                             }
                                         }
                                     });
        finaldo.addTextChangedListener(new TextWatcher() {

                                           public void afterTextChanged(Editable s) {
                                           }

                                           public void beforeTextChanged(CharSequence s, int start,
                                                                         int count, int after) {
                                           }

                                           public void onTextChanged(CharSequence s, int start,
                                                                     int before, int count) {
                                               actualizarDatos(2);
                                           }
                                       });
        emaildomains.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(defalult_domains.get(position).equals("Otro...")){
                    showChangeLangDialog();
                    actualizarDatos(2);
                    defalult_domains.add("Editar Otro...");
                }
                else if(defalult_domains.size() == 8){
                    if(defalult_domains.get(position).equals("Editar Otro...")){
                        showChangeLangDialog();
                        actualizarDatos(2);
                    }
                    else if(position!=6){
                        actualizarDatos(1);
                        finaldo.setVisibility(View.INVISIBLE);
                        defalult_domains.remove(7);
                        defalult_domains.set(6,"Otro...");
                        adapter.notifyDataSetChanged();

                    }
                }
                else{
                    actualizarDatos(1);
                    finaldo.setVisibility(View.INVISIBLE);
                    if(defalult_domains.size()==8) {
                        defalult_domains.remove(7);
                        defalult_domains.set(6,"Otro...");
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }


    public void actualizarDatos(int tipo){
        if(tipo==1) {
            emailcompleto.setText(email.getText().toString() + "@" + defalult_domains.get(emaildomains.getSelectedItemPosition()));
        }
        else{
            emailcompleto.setText(email.getText().toString() + "@" + defalult_domains.get(6));
        }
    }


    public void guardarEmail_buton(View view) {
        guardarEmail();
    }
    public void cambioDominio(View view) {
        showChangeLangDialog();
    }

    public void guardarEmail(){
        email_create.add(emailcompleto.getText().toString());

        if(emaildomains.getSelectedItemPosition() == 6) {
            int pos_arroba =  emailcompleto.getText().toString().indexOf("@");
            String domain =  emailcompleto.getText().toString().substring(pos_arroba+1);
            Boolean existe = false;
            for (int i=0 ; i < new_domains.size();i++){
                if(new_domains.get(i).equals(domain)){
                    existe = true;
                }
            }
            if(!existe){
                new_domains.add(domain.toLowerCase());
            }
        }
        String test =  "";
        for (int i=0; i < new_domains.size() ; i++){
            test += new_domains.get(i) +"\n";
        }
        testing.setText(test);
        email.setText("");
        finaldo.setText("");
        finaldo.setVisibility(View.INVISIBLE);
        emaildomains.setVisibility(View.VISIBLE);
        emaildomains.setSelection(0);
        defalult_domains.set(6,"Otro...");
        adapter.notifyDataSetChanged();
        actualizarDatos(1);
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final AutoCompleteTextView dominio = dialogView.findViewById(R.id.edit1);
        adapter_auto = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new_domains);
        dominio.setAdapter(adapter_auto);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, defalult_domains);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(!defalult_domains.get(6).equals("Otro..."))
        dominio.setText(defalult_domains.get(6));


        dialogBuilder.setTitle("Otro dominio:");
        dialogBuilder.setMessage("Escribe el dominio de tu Correo Electronico");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(dominio.getText().toString().length()>0) {
                    //finaldo.setText(dominio.getText().toString());
                    //finaldo.setVisibility(View.VISIBLE);
                    //emaildomains.setVisibility(View.INVISIBLE);
                    defalult_domains.set(6,dominio.getText().toString());
                    emaildomains.setAdapter(adapter);
                    emaildomains.setSelection(6);
                    actualizarDatos(2);

                }
                else{
                    finaldo.setText("");
                    finaldo.setVisibility(View.INVISIBLE);
                    emaildomains.setVisibility(View.VISIBLE);

                    emaildomains.setSelection(0);
                    actualizarDatos(1);
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finaldo.setText("");
                finaldo.setVisibility(View.INVISIBLE);
                emaildomains.setVisibility(View.VISIBLE);
                emaildomains.setSelection(0);
                actualizarDatos(1);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


}

