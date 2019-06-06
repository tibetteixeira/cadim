package br.com.cadim.cadim.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Paciente implements Parcelable {
    private String cpf;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;
    private String sexo;
    private int altura;
    private double peso;
    private int telefone;

    public Paciente(String cpf, String nome, String dataNascimento,
                    String email, String senha, String sexo,
                    int altura, double peso, int telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.sexo = sexo;
        this.altura = altura;
        this.peso = peso;
        this.telefone = telefone;
    }

    protected Paciente(Parcel in) {
        cpf = in.readString();
        nome = in.readString();
        dataNascimento = in.readString();
        email = in.readString();
        senha = in.readString();
        sexo = in.readString();
        altura = in.readInt();
        peso = in.readDouble();
        telefone = in.readInt();
    }

    public static final Creator<Paciente> CREATOR = new Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getSexo() {
        return sexo;
    }

    public int getAltura() {
        return altura;
    }

    public double getPeso() {
        return peso;
    }

    public int getTelefone() {
        return telefone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cpf);
        parcel.writeString(nome);
        parcel.writeString(dataNascimento);
        parcel.writeString(email);
        parcel.writeString(senha);
        parcel.writeString(sexo);
        parcel.writeInt(altura);
        parcel.writeDouble(peso);
        parcel.writeInt(telefone);
    }
}