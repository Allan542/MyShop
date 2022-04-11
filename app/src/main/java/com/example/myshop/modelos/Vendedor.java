package com.example.myshop.modelos;

public class Vendedor implements Cloneable {
    private int id;
    private String nome;
    private String email;
    private String documento;
    private String telefone;
    private String senha;
    private String cep;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;

    public Vendedor(){}


    // Construtor de login
    public Vendedor(int id, String email, String senha){
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    // Construtor para cadastro
    public Vendedor (String nome, String email, String documento, String telefone, String senha,
                    String cep, String endereco, String bairro, String cidade, String estado){
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.telefone = telefone;
        this.senha = senha;
        this.cep = cep;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Construtor para exibir no perfil
    public Vendedor (int id, String nome, String email, String documento, String telefone,
                    String cep, String endereco, String bairro, String cidade, String estado){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.telefone = telefone;
        this.cep = cep;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Construtor de inserção
    public Vendedor(Vendedor v){
        this.id = v.id;
        this.nome = v.nome;
        this.email = v.email;
        this.documento = v.documento;
        this.telefone = v.telefone;
        this.senha = v.senha;
        this.cep = v.cep;
        this.endereco = v.endereco;
        this.bairro = v.bairro;
        this.cidade = v.cidade;
        this.estado = v.estado;
    }

    // Getters e Setters
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getDocumento(){
        return this.documento;
    }

    public void setDocumento(String documento){
        this.documento = documento;
    }

    public String getTelefone(){
        return this.telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getCep(){
        return this.cep;
    }

    public void setCep(String cep){
        this.cep = cep;
    }

    public String getEndereco(){
        return this.endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public String getBairro(){
        return this.bairro;
    }

    public void setBairro(String bairro){
        this.bairro = bairro;
    }

    public String getCidade(){
        return this.cidade;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String getEstado(){
        return this.estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    @Override
    public Object clone(){
        Vendedor clone = new Vendedor(this);
        return clone;
    }
}
