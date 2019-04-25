package com.example.emailtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    AutoCompleteTextView dominio;
    Spinner emaildomains;
    ArrayList<String> defalult_domains = new ArrayList<>();
    ArrayList<String> new_domains = new ArrayList<>();
    ArrayList<String> email_create = new ArrayList<>();
    ArrayAdapter<String> adapter_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailcompleto = findViewById(R.id.result);
        testing =  findViewById(R.id.textView);
        emaildomains = findViewById(R.id.domains);
        email = findViewById(R.id.email);
        dominio = findViewById(R.id.email2);

        defalult_domains.addAll(Arrays.asList("gmail.com", "hotmail.com", "yahoo.com", "outlook.es", "outlook.com", "zohomail.eu","Otro..."));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, defalult_domains);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emaildomains.setAdapter(adapter);

        adapter_auto = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new_domains);
        dominio.setAdapter(adapter_auto);

        actualizarDatos(1);
        email.addTextChangedListener(new TextWatcher() {

                                         public void afterTextChanged(Editable s) {
                                         }

                                         public void beforeTextChanged(CharSequence s, int start,
                                                                       int count, int after) {
                                             actualizarDatos(1);
                                         }

                                         public void onTextChanged(CharSequence s, int start,
                                                                   int before, int count) {
                                             actualizarDatos(1);
                                         }
                                     });
        dominio.addTextChangedListener(new TextWatcher() {

                                           public void afterTextChanged(Editable s) {
                                           }

                                           public void beforeTextChanged(CharSequence s, int start,
                                                                         int count, int after) {
                                               actualizarDatos(2);
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
                    actualizarDatos(2);
                    dominio.setVisibility(View.VISIBLE);
                }
                else{
                    actualizarDatos(1);
                    dominio.setVisibility(View.INVISIBLE);

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
            emailcompleto.setText(email.getText().toString() + "@" + dominio.getText().toString());
        }
    }


    public void GuardarEmail(View view) {
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
                new_domains.add(domain.toUpperCase());
            }
            adapter_auto = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new_domains);
            dominio.setAdapter(adapter_auto);
        }
        String test =  "";
        for (int i=0; i < new_domains.size() ; i++){
            test += new_domains.get(i) +"\n";
        }
        testing.setText(test);
        email.setText("");
        dominio.setText("");
        dominio.setVisibility(View.INVISIBLE);
        emaildomains.setSelection(0);
        actualizarDatos(1);
    }
}

