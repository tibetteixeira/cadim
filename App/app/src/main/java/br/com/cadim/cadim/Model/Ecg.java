package br.com.cadim.cadim.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ecg implements Parcelable {
    private int ecgId;
    private String ecgFile;
    private String cpfPaciente;
    private String dataHora;
    private double imc;

    public Ecg(int ecgId, String ecgFile, String cpfPaciente, String dataHora, double imc) {
        this.ecgId = ecgId;
        this.ecgFile = ecgFile;
        this.cpfPaciente = cpfPaciente;
        this.dataHora = dataHora;
        this.imc = imc;
    }

    private Ecg(Parcel in) {
        ecgId = in.readInt();
        ecgFile = in.readString();
        cpfPaciente = in.readString();
        dataHora = in.readString();
        imc = in.readDouble();
    }

    public static final Creator<Ecg> CREATOR = new Creator<Ecg>() {
        @Override
        public Ecg createFromParcel(Parcel in) {
            return new Ecg(in);
        }

        @Override
        public Ecg[] newArray(int size) {
            return new Ecg[size];
        }
    };

    public int getEcgId() {
        return ecgId;
    }

    public String getEcgFile() {
        return ecgFile;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public String getDataHora() {
        return dataHora;
    }

    public double getImc() {
        return imc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ecgId);
        parcel.writeString(ecgFile);
        parcel.writeString(cpfPaciente);
        parcel.writeString(dataHora);
        parcel.writeDouble(imc);
    }
}
