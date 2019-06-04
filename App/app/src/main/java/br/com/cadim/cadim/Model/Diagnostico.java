package br.com.cadim.cadim.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Diagnostico implements Parcelable {
    private int diagnosticoId;
    private int ecgId;
    private String descricao;
    private String crm;
    private String nomeMedico;
    private String dataHora;

    public Diagnostico(int diagnosticoId, int ecgId, String descricao, String crm, String nomeMedico, String dataHora) {
        this.diagnosticoId = diagnosticoId;
        this.ecgId = ecgId;
        this.descricao = descricao;
        this.crm = crm;
        this.nomeMedico = nomeMedico;
        this.dataHora = dataHora;
    }

    protected Diagnostico(Parcel in) {
        diagnosticoId = in.readInt();
        ecgId = in.readInt();
        descricao = in.readString();
        crm = in.readString();
        nomeMedico = in.readString();
        dataHora = in.readString();
    }

    public static final Creator<Diagnostico> CREATOR = new Creator<Diagnostico>() {
        @Override
        public Diagnostico createFromParcel(Parcel in) {
            return new Diagnostico(in);
        }

        @Override
        public Diagnostico[] newArray(int size) {
            return new Diagnostico[size];
        }
    };

    public int getDiagnosticoId() {
        return diagnosticoId;
    }

    public int getEcgId() {
        return ecgId;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCrm() {
        return crm;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public String getDataHora() {
        return dataHora;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(diagnosticoId);
        parcel.writeInt(ecgId);
        parcel.writeString(descricao);
        parcel.writeString(crm);
        parcel.writeString(nomeMedico);
        parcel.writeString(dataHora);
    }
}
