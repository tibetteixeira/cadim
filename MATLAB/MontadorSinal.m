clear all;
fid=fopen('signal_test.txt','r');
aa=fscanf(fid,'%c\n');
jj=1;
find_sync = 0;
count=0;
% Procura o A5, 5A
while find_sync == 0
    
if sum(aa(jj:jj+1) == 'A5') == 2 && sum(aa(jj+2:jj+3) == '5A') == 2
find_sync = 1;
else jj = jj+1;
end

end

jj=1;
count=0;
while jj+3 <= length(aa)
    
if sum(aa(jj:jj+1) == 'A5') == 2 && sum(aa(jj+2:jj+3) == '5A') == 2
count=count+1;
%disp(aa(jj+12:jj+13));
%disp(aa(jj+14:jj+15));
hb = hex2dec(aa(jj+12:jj+13));
lb = hex2dec(aa(jj+14:jj+15));
ecg_sample(count) = bitshift(hb,8)+lb;
if ecg_sample(count) > 1000
    ecg_sample(count) = ecg_sample(count-1);
end
    
%disp(ecg_sample)
%pause;
end
jj=jj+1;
end

fclose(fid);
figure;
plot((0:length(ecg_sample)-1)/128, ecg_sample);
%plot(ecg_sample);
title('Sinal montado no MATLAB');