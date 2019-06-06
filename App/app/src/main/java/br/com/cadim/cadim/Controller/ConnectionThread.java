package br.com.cadim.cadim.Controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

import br.com.cadim.cadim.View.AquisitionEcgActivity;

public class ConnectionThread extends Thread {

    private BluetoothSocket btSocket;
    private String btDevAddress;
    private boolean running;
    private boolean isConnected;

    public ConnectionThread(String btDevAddress) {

        this.btDevAddress = btDevAddress;
    }

    public void run() {

        this.running = true;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        try {

            BluetoothDevice btDevice = btAdapter.getRemoteDevice(btDevAddress);
            String myUUID = "00001101-0000-1000-8000-00805F9B34FB";
            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID));

            btAdapter.cancelDiscovery();

            if (btSocket != null) {
                btSocket.connect();
            }

        } catch (IOException e) {
            /*  código informando que ocorreu um erro na conexão
             */
            e.printStackTrace();
            toAquisitionEcgActivity("---N".getBytes());
        }

        if (btSocket != null) {

            /*  código informando que a conexão ocorreu com sucesso
             */
            isConnected = true;
            toAquisitionEcgActivity("---S".getBytes());

            try {

                InputStream input = btSocket.getInputStream();

                while (running) {

                    byte[] buffer = new byte[1024];
                    int bytes;
                    int bytesRead = -1;

                    /*  Lê os bytes recebidos e os armazena no buffer até que
                        uma quebra de linha seja identificada. Nesse ponto, assumimos
                        que a mensagem foi transmitida por completo.
                     */
                    do {
                        bytes = input.read(buffer, bytesRead + 1, 32);
                        bytesRead += bytes;
                    } while (buffer[bytesRead] != '\n');

                    toAquisitionEcgActivity(Arrays.copyOfRange(buffer, 0, bytesRead - 1));
                }

            } catch (IOException e) {
                /*  código informando que ocorreu um erro na conexão
                 */
                e.printStackTrace();
                toAquisitionEcgActivity("---N".getBytes());
                isConnected = false;
            }
        }

    }

    /*  Utiliza um handler para enviar um byte array à Activity.
        O byte array é encapsulado em um Bundle e posteriormente em uma Message
        antes de ser enviado.
     */
    private void toAquisitionEcgActivity(byte[] data) {

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putByteArray("data", data);
        message.setData(bundle);
        AquisitionEcgActivity.handler.sendMessage(message);
    }

    /*  Método utilizado pela Activity para encerrar a conexão
     */
    public void cancel() {

        try {
            running = false;
            isConnected = false;
            btSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
        isConnected = false;
    }

}
