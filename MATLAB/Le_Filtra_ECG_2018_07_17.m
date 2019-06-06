fclose(s);
clc;
clear all;

Inicio_Espera_Buffer = [];
Fim_Espera_Buffer = [];
Inicio_ReadSave_Buffer = [];
Fim_ReadSave_Buffer = [];
Inicio_Filtra_Buffer = [];
Fim_Filtra_Buffer = [];

tic;
tstart = tic;
Inicio_Espera_Buffer = [Inicio_Espera_Buffer tstart];
s = serial('COM5');
s.InputBufferSize = 2*16384;
s.BaudRate = 57600;
fopen(s);
parou = 0;
buffer = 0;
time = 0;
sfreq = 256;


while s.BytesAvailable < s.InputBufferSize
    time = time+1;
end
tending = toc(tstart)
Fim_Espera_Buffer = [Fim_Espera_Buffer tending];
tic;
tstart2 = tic;
Inicio_ReadSave_Buffer = [Inicio_ReadSave_Buffer tstart2];
out = fread(s,s.BytesAvailable,'char');

find_sync = 0;
jj=1;
while find_sync == 0;
    sync0 = dec2hex(out(jj));
    sync1 = dec2hex(out(jj+1));
    if sum(sync0 == 'A5') == 2 && sum(sync1 == '5A') == 2
        find_sync = 1;
    else jj = jj+1;
    end
end

% index_hb_ch1 = jj+14:17:length(out);
% index_lb_ch1 = jj+15:17:length(out);

index_hb_ch2 = jj+4:6:length(out);
index_lb_ch2 = jj+5:6:length(out);

hb = out(index_hb_ch2);
lb = out(index_lb_ch2);
Lmin = min(length(hb),length(lb));

ECGch_2 = bitshift(hb(1:Lmin),8) + lb(1:Lmin);
%ECGch_2 = ECGch_2-mean(ECGch_2);
save('ECG_Tibet_20180717','ECGch_2');
tending2 = toc(tstart2)
Fim_ReadSave_Buffer = [Fim_ReadSave_Buffer tending2];

tstart3 = tic;
Inicio_Filtra_Buffer = [Inicio_Filtra_Buffer tstart3];
%sinalf1 = FiltroECGMuscular_WaveletFs256_TesteArduino(ECGch_2',sfreq);
sinalf1 = FiltroECGMuscular_WaveletFs256_TesteArduino(ECGch_2',sfreq,'db10');
tending3 = toc(tstart3)
Fim_Filtra_Buffer = [Fim_Filtra_Buffer tending3];

tic
tstart = tic;
Inicio_Espera_Buffer = [Inicio_Espera_Buffer tstart];
Antigo_Buffer = 0;
Novo_Buffer = 0;
t = 0 ;
k = 1;
x = 0 ;
y = 0 ;
startSpot = 0;
interv = length(ECGch_2)/sfreq; % considering 1000 samples
step = 1/sfreq ; % lowering step has a number of cycles and then acquire more data
figure;
Esperando_Buffer = 0;


while ( t < length(ECGch_2)/sfreq )
    b = ECGch_2(k);
    c = sinalf1(k);
    x = [ x, b ];
    y = [ y, c ];
    tt = (0:1:length(x)-1)/sfreq;
    plot(tt,x,'b',tt,y,'r') ;
    %plot(x) ;
      if ((t/step)-1000 < 0)
          startSpot = 0;
      else
          startSpot = (t/step)-500;
      end
      axis([ startSpot/sfreq, (t/step+50)/sfreq, min(1.2*min(x),0) , 1.2*max(x) ]);
      %axis([ startSpot, t/step+50, 1.2*min(ECGch_2) , 1.2*max(ECGch_2) ]);
      grid
      t = t + step;
      k = k + 1;
      drawnow;
      if s.BytesAvailable < s.InputBufferSize
          time = time + 1;
      else 
          
          tending = toc(tstart)
          Fim_Espera_Buffer = [Fim_Espera_Buffer tending];
          tic;
          tstart2 = tic;
          Inicio_ReadSave_Buffer = [Inicio_ReadSave_Buffer tstart2];
          out = fread(s,s.BytesAvailable,'char');

          find_sync = 0;
          jj=1;
          while find_sync == 0;
            sync0 = dec2hex(out(jj));
            sync1 = dec2hex(out(jj+1));
            if sum(sync0 == 'A5') == 2 && sum(sync1 == '5A') == 2
                find_sync = 1;
            else jj = jj+1;
            end
          end
          
%           index_hb_ch1 = jj+14:17:length(out);
%           index_lb_ch1 = jj+15:17:length(out);

         index_hb_ch2 = jj+4:6:length(out);
         index_lb_ch2 = jj+5:6:length(out);

         hb = out(index_hb_ch2);
         lb = out(index_lb_ch2);
         Lmin = min(length(hb),length(lb));

         Novo_Buffer = bitshift(hb(1:Lmin),8) + lb(1:Lmin);
         %Novo_Buffer = Novo_Buffer-mean(Novo_Buffer);
         ECGch_2 = [ECGch_2; Novo_Buffer];
         save('ECG_Tibet_20180717','ECGch_2');
        tending2 = toc(tstart2)
        Fim_ReadSave_Buffer = [Fim_ReadSave_Buffer tending2];
        
        tstart3 = tic;
        Inicio_Filtra_Buffer = [Inicio_Filtra_Buffer tstart3];
        %Novo_Buffer_f = FiltroECGMuscular_WaveletFs256_TesteArduino(Novo_Buffer',sfreq);
        Novo_Buffer_f = FiltroECGMuscular_WaveletFs256_TesteArduino(Novo_Buffer',sfreq,'db10');
        tending3 = toc(tstart3)
        Fim_Filtra_Buffer = [Fim_Filtra_Buffer tending3];
        sinalf1 = [sinalf1 Novo_Buffer_f];
        
        tic;
        tstart = tic;
        Inicio_Espera_Buffer = [Inicio_Espera_Buffer tstart];
         time = 0;
      end

      %pause(step)
end
  
fclose(s);