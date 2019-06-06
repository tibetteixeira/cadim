clear all;
fid=fopen('ECG-2019_05_14-15_34_27.txt','r');
aa=fscanf(fid,'%d');

fclose(fid);

figure; plot((0:length(aa)-1)/256, aa);

%figure; plot(aa);
title('Byte ecg cell');
%title('Sinal montado no celular (offline)');
%title('Sinal montado em tempo real');