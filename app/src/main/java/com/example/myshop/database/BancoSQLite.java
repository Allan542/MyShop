package com.example.myshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myshop.modelos.Cliente;
import com.example.myshop.modelos.Produto;
import com.example.myshop.modelos.Vendedor;

public class BancoSQLite extends SQLiteOpenHelper {

    // Banco de dados Cliente e Vendedor
    private static final String NOME_BANCO = "Dados_MyShop.db";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String EMAIL = "email";
    private static final String DOCUMENTO = "documento";
    private static final String TELEFONE = "telefone";
    private static final String SENHA = "senha";
    private static final String CEP = "cep";
    private static final String ENDERECO = "endereco";
    private static final String BAIRRO = "bairro";
    private static final String CIDADE = "cidade";
    private static final String ESTADO = "estado";

    private static final String TABELA_CLIENTE = "cliente";
    private static final String TABELA_VENDEDOR = "vendedor";
    private static final int VERSAO = 1;

    // Banco de dados Produto
    private static final String CODIGODEBARRAS = "codigodebarras";
    private static final String NOMEPRODUTO = "nomeproduto";
    private static final String CATEGORIA = "categoria";
    private static final String QUANTIDADE = "documento";
    private static final String DESCRICAO = "descricao";
    private static final String MARCA = "marca";
    private static final String PRECOCUSTO = "precocusto";
    private static final String PRECOVENDA = "precovenda";
    private static final String IMAGEM = "imagem";

    private static final String TABELA_PRODUTO = "produto";

    public BancoSQLite(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // Criando tabela Cliente
        String CREATE_CLIENTE_TABLE = "CREATE TABLE " + TABELA_CLIENTE + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME + " TEXT, " +
                EMAIL + " TEXT UNIQUE, " +
                DOCUMENTO + " TEXT UNIQUE, " +
                TELEFONE + " TEXT, " +
                SENHA + " TEXT, " +
                CEP + " TEXT, " +
                ENDERECO + " TEXT, " +
                BAIRRO + " TEXT, " +
                CIDADE + " TEXT, " +
                ESTADO + " TEXT )";

        // Criando tabela Vendedor
        String CREATE_VENDEDOR_TABLE = "CREATE TABLE " + TABELA_VENDEDOR + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME + " TEXT, " +
                EMAIL + " TEXT UNIQUE, " +
                DOCUMENTO + " TEXT UNIQUE, " +
                TELEFONE + " TEXT, " +
                SENHA + " TEXT, " +
                CEP + " TEXT, " +
                ENDERECO + " TEXT, " +
                BAIRRO + " TEXT, " +
                CIDADE + " TEXT, " +
                ESTADO + " TEXT )";

        // Criando tabela Produto
        String CREATE_PRODUTO_TABLE = "CREATE TABLE " + TABELA_PRODUTO + " ( " +
        ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CODIGODEBARRAS + " INTEGER UNIQUE, " +
                NOMEPRODUTO + " TEXT, " +
                CATEGORIA + " TEXT, " +
                QUANTIDADE + " TEXT, " +
                DESCRICAO + " VARCHAR, " +
                MARCA + " TEXT, " +
                PRECOCUSTO + " DOUBLE, " +
                PRECOVENDA + " DOUBLE, " +
                IMAGEM + " INTEGER )";

        // Execução da criação das tabelas
        db.execSQL(CREATE_CLIENTE_TABLE);
        db.execSQL(CREATE_VENDEDOR_TABLE);
        db.execSQL(CREATE_PRODUTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_VENDEDOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTO);
        onCreate(db);
    }

    // Insert e Select do Cliente
    public boolean inserirCliente(Cliente c){
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME, c.getNome());
        values.put(EMAIL, c.getEmail());
        values.put(DOCUMENTO, c.getDocumento());
        values.put(TELEFONE, c.getTelefone());
        values.put(SENHA, c.getSenha());
        values.put(CEP, c.getCep());
        values.put(ENDERECO, c.getEndereco());
        values.put(BAIRRO, c.getBairro());
        values.put(CIDADE, c.getCidade());
        values.put(ESTADO, c.getEstado());
        result = db.insert(TABELA_CLIENTE, null, values);
        db.close();

        if(result == -1) return false;
        else return true;
    }

    // Login
    public Cliente selecionarCliente(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_CLIENTE,
                new String[]{ID, EMAIL, SENHA},
                EMAIL + " = ?",
                new String[]{ String.valueOf(email)},null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Cliente cli = new Cliente(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2));

            return (Cliente) cli.clone();
        }
        else return null;
    }

    // Exibir no perfil
    public Cliente selecionarInformacoesCliente(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_CLIENTE,
                new String[]{ID, NOME, EMAIL, DOCUMENTO, TELEFONE, CEP, ENDERECO, BAIRRO, CIDADE, ESTADO},
                ID + " = ?",
                new String[]{ String.valueOf(id) },null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Cliente cli = new Cliente(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9));

            return (Cliente) cli.clone();
        }
        else return null;
    }


    // Insert e Select do Vendedor
    public boolean inserirVendedor(Vendedor v){
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME, v.getNome());
        values.put(EMAIL, v.getEmail());
        values.put(DOCUMENTO, v.getDocumento());
        values.put(TELEFONE, v.getTelefone());
        values.put(SENHA, v.getSenha());
        values.put(CEP, v.getCep());
        values.put(ENDERECO, v.getEndereco());
        values.put(BAIRRO, v.getBairro());
        values.put(CIDADE, v.getCidade());
        values.put(ESTADO, v.getEstado());
        result = db.insert(TABELA_VENDEDOR, null, values);
        db.close();

        if (result == -1) return false;
        else return true;
    }

    // Login
    public Vendedor selecionarVendedor(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_VENDEDOR,
                new String[]{ID, EMAIL, SENHA},
                EMAIL + " = ?",
                new String[]{ String.valueOf(email)},null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Vendedor ven = new Vendedor(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2));

            return (Vendedor) ven.clone();
        }
        else return null;
    }

    // Exibir no Perfil
    public Vendedor selecionarInformacoesVendedor(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_VENDEDOR,
                new String[]{ID, NOME, EMAIL, DOCUMENTO, TELEFONE, CEP, ENDERECO, BAIRRO, CIDADE, ESTADO},
                ID + " = ?",
                new String[]{ String.valueOf(id) },null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Vendedor ven = new Vendedor(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9));

            return (Vendedor) ven.clone();
        }
        else return null;
    }



    // Insert e Select do Produto

    public boolean inserirProduto(Produto p){
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODIGODEBARRAS, p.getCodigodebarras());
        values.put(NOMEPRODUTO, p.getNomeproduto());
        values.put(CATEGORIA, p.getCategoria());
        values.put(QUANTIDADE, p.getQuantidade());
        values.put(DESCRICAO, p.getDescricao());
        values.put(MARCA, p.getMarca());
        values.put(PRECOCUSTO, p.getPrecoCusto());
        values.put(PRECOVENDA, p.getPrecoVenda());
        values.put(IMAGEM, p.getImagem());
        result = db.insert(TABELA_PRODUTO, null, values);
        db.close();

        if(result == -1) return false;
        else return true;
    }

    // Exibir na main
    public Produto selecionarProduto(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_PRODUTO,
                new String[]{ID, NOMEPRODUTO, PRECOVENDA, IMAGEM},
                ID + " = ?",
                new String[]{ String.valueOf(id)},null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Produto pro = new Produto(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Double.parseDouble(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)));

            return (Produto) pro.clone();
        }
        else return null;
    }

    // Exibir na tela de produto selecionado e outras relacionadas a compra
    public Produto selecionaProdutoSelecionado(int id){
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(
                        TABELA_PRODUTO,
                        new String[]{ID, NOMEPRODUTO, PRECOVENDA,DESCRICAO, IMAGEM},
                        ID + " = ?",
                        new String[]{ String.valueOf(id)},null, null, null, null);
                if(cursor != null){
                    cursor.moveToFirst();

                    Produto pro = new Produto(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            Double.parseDouble(cursor.getString(2)),
                            cursor.getString(3),
                            Integer.parseInt(cursor.getString(4)));

            return (Produto) pro.clone();
        }
        else return null;
    }

    public Produto selecionaProdutoPorCategoria(String categoria){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_PRODUTO,
                new String[]{ID, PRECOVENDA, IMAGEM},
                CATEGORIA + " = ?",
                new String[]{ String.valueOf(categoria) },null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Produto pro = new Produto(
                    Integer.parseInt(cursor.getString(0)),
                    Double.parseDouble(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)));

            return (Produto) pro.clone();
        }
        else return null;
    }

    public Produto selecionaProdutoPorNome(String nomeproduto){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_PRODUTO,
                new String[]{ID, PRECOVENDA, IMAGEM},
                NOMEPRODUTO + " = ?",
                new String[]{ nomeproduto },null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Produto pro = new Produto(
                    Integer.parseInt(cursor.getString(0)),
                    Double.parseDouble(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)));

            return (Produto) pro.clone();
        }
        else return null;
    }

    // Selecionar produtos cadastrados
    public Produto selecionaProdutosCadastrados(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABELA_PRODUTO,
                new String[]{ID, CODIGODEBARRAS, CATEGORIA, NOMEPRODUTO, QUANTIDADE, MARCA, PRECOCUSTO, PRECOVENDA, IMAGEM},
                ID + " = ?",
                new String[]{ String.valueOf(id)},null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();

            Produto pro = new Produto(
                    Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    cursor.getString(2),
                    cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    Double.parseDouble(cursor.getString(6)),
                    Double.parseDouble(cursor.getString(7)),
                    Integer.parseInt(cursor.getString(8)));

            return (Produto) pro.clone();
        }
        else return null;
    }

}
