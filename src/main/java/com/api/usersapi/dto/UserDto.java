package com.api.usersapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.UUID;

public class UserDto {

    @Null
    private UUID id;

    @NotNull
    @Max(3)
    private String login;

    @NotNull
    @Max(3)
    private String senha;

    @NotNull
    @Max(3)
    private String nome;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
