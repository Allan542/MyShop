package com.example.myshop;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Cliente;
import com.example.myshop.modelos.Vendedor;


public class CadastroUsuarioActivity extends AppCompatActivity {

    ImageButton ImgBtn_Voltar_CadastroUsuario;
    Button Btn_Cadastrar_CadastroUsuario;
    EditText Edt_Nome_CadastroUsuario, Edt_Email_CadastroUsuario, Edt_Documento_CadastroUsuario, Edt_Telefone_CadastroUsuario,
            Edt_Senha_CadastroUsuario, Edt_Cep_CadastroUsuario, Edt_Endereco_CadastroUsuario, Edt_Bairro_CadastroUsuario,
            Edt_Cidade_CadastroUsuario, Edt_Estado_CadastroUsuario;
    RadioButton Rdo_RegistraCliente_CadastroUsuario, Rdo_RegistraVendedor_CadastroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        procuraIDs();
        eventoClique();
    }

    private void procuraIDs() {
        ImgBtn_Voltar_CadastroUsuario = findViewById(R.id.imgbtn_Voltar_CadastroUsuario);
        Rdo_RegistraCliente_CadastroUsuario = findViewById(R.id.rdo_RegistraCliente_CadastroUsuario);
        Rdo_RegistraVendedor_CadastroUsuario = findViewById(R.id.rdo_RegistraVendedor_CadastroUsuario);
        Edt_Nome_CadastroUsuario = findViewById(R.id.edt_Nome_CadastroUsuario);
        Edt_Email_CadastroUsuario = findViewById(R.id.edt_Email_CadastroUsuario);
        Edt_Documento_CadastroUsuario = findViewById(R.id.edt_Documento_CadastroUsuario);
        Edt_Telefone_CadastroUsuario = findViewById(R.id.edt_Telefone_CadastroUsuario);
        Edt_Senha_CadastroUsuario = findViewById(R.id.edt_Senha_CadastroUsuario);
        Edt_Cep_CadastroUsuario = findViewById(R.id.edt_Cep_CadastroUsuario);
        Edt_Endereco_CadastroUsuario = findViewById(R.id.edt_Endereco_CadastroUsuario);
        Edt_Bairro_CadastroUsuario = findViewById(R.id.edt_Bairro_CadastroUsuario);
        Edt_Cidade_CadastroUsuario = findViewById(R.id.edt_Cidade_CadastroUsuario);
        Edt_Estado_CadastroUsuario = findViewById(R.id.edt_Estado_CadastroUsuario);
        Btn_Cadastrar_CadastroUsuario = findViewById(R.id.btn_Cadastrar_CadastroUsuario);
    }

    private void eventoClique() {
        Btn_Cadastrar_CadastroUsuario.setOnClickListener(view -> verificaClienteVendedor());
        ImgBtn_Voltar_CadastroUsuario.setOnClickListener(view -> carregaTela(LoginActivity.class));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Se o radio selecionado for cliente, faz cadastro no banco de dados do cliente. Senão, faz cadastro no banco de vendedor.
    // Antes de fazer a etapa acima, verifica se os campos foram preenchidos.
    // Não irá cadastrar se os campos com cláusula UNIQUE no banco forem preenchidos com valores que já existem
    private void verificaClienteVendedor(){
        BancoSQLite db = new BancoSQLite(this);
        boolean vazio = verificaVazio();
        if(vazio) Toast.makeText(this, "Não foi realizar o cadastro, pois há campo(s) vazio(s)", Toast.LENGTH_SHORT).show();
        else {
            if (Rdo_RegistraCliente_CadastroUsuario.isChecked()) {
                if (db.inserirCliente(new Cliente(
                        Edt_Nome_CadastroUsuario.getText().toString(),
                        Edt_Email_CadastroUsuario.getText().toString(),
                        Edt_Documento_CadastroUsuario.getText().toString(),
                        Edt_Telefone_CadastroUsuario.getText().toString(),
                        Edt_Senha_CadastroUsuario.getText().toString(),
                        Edt_Cep_CadastroUsuario.getText().toString(),
                        Edt_Endereco_CadastroUsuario.getText().toString(),
                        Edt_Bairro_CadastroUsuario.getText().toString(),
                        Edt_Cidade_CadastroUsuario.getText().toString(),
                        Edt_Estado_CadastroUsuario.getText().toString()
                ))) {
                    Toast.makeText(this, "Cliente Cadastrado com sucessso!", Toast.LENGTH_SHORT).show();
                    carregaTela(LoginActivity.class);
                } else
                    Toast.makeText(this, "Não foi possível realizar o cadastro do cliente!", Toast.LENGTH_SHORT).show();
            } else if (Rdo_RegistraVendedor_CadastroUsuario.isChecked()) {
                if (db.inserirVendedor(new Vendedor(
                        Edt_Nome_CadastroUsuario.getText().toString(),
                        Edt_Email_CadastroUsuario.getText().toString(),
                        Edt_Documento_CadastroUsuario.getText().toString(),
                        Edt_Telefone_CadastroUsuario.getText().toString(),
                        Edt_Senha_CadastroUsuario.getText().toString(),
                        Edt_Cep_CadastroUsuario.getText().toString(),
                        Edt_Endereco_CadastroUsuario.getText().toString(),
                        Edt_Bairro_CadastroUsuario.getText().toString(),
                        Edt_Cidade_CadastroUsuario.getText().toString(),
                        Edt_Estado_CadastroUsuario.getText().toString()
                ))) {
                    Toast.makeText(this, "Vendedor Cadastrado com sucessso!", Toast.LENGTH_SHORT).show();
                    carregaTela(LoginActivity.class);
                } else
                    Toast.makeText(this, "Não foi possível realizar o cadastro do vendedor!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean verificaVazio(){
        return Edt_Nome_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Email_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Documento_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Telefone_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Senha_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Cep_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Endereco_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Bairro_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Cidade_CadastroUsuario.getText().toString().isEmpty() ||
                Edt_Estado_CadastroUsuario.getText().toString().isEmpty();
    }
}