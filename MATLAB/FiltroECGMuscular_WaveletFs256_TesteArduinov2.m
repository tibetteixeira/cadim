%%% Implementando Wavelets para remoção de ruído de alta frequência %%%

function[S1] = FiltroECGMuscular_WaveletFs256_TesteArduino_v2(sinal,sfreq)

trecho=sinal;
[Lo_D,Hi_D,Lo_R,Hi_R]=wfilters('db4');
Lfs = 2*length(Lo_D);


%%% Decomposição 1

A1 = filtfilt(Lo_D,1,trecho);
D1 = filtfilt(Hi_D,1,trecho);

A1 = [A1(1)*ones(1,Lfs) A1 A1(end)*ones(1,Lfs)];
D1 = [D1(1)*ones(1,Lfs) D1 D1(end)*ones(1,Lfs)];


%%% Subamostragem %%%


sA1 = dyaddown(A1,1);
sD1 = dyaddown(D1,1);

cA1 = sA1;
cD1 = sD1;

%% Limiarização do coeficiente cD1 %%
% 
% 
% 
% lim1 = std(cD1)*sqrt(2*log(length(cD1)));
% posz = find(abs(cD1)<=lim1);
% cD1(posz)=0;
% posnz1 = find(cD1>lim1);
% cD1(posnz1) = cD1(posnz1) - lim1;
% posnz2 = find(cD1<-1*lim1);
% cD1(posnz2) = cD1(posnz2) + lim1;



%%% Decomposição 2

A2 = filtfilt(Lo_D,1,cA1);
D2 = filtfilt(Hi_D,1,cA1);

A2 = [A2(1)*ones(1,Lfs) A2 A2(end)*ones(1,Lfs)];
D2 = [D2(1)*ones(1,Lfs) D2 D2(end)*ones(1,Lfs)];


%%% Subamostragem %%%

sA2 = dyaddown(A2,1);
sD2 = dyaddown(D2,1);

cA2 = sA2;
cD2 = sD2;

%% Limiarização do coeficiente cD2 %%


% cD2h = cD2.^2;
% swin = 3;
% limsig = std(cD2h);
% vbeg = [];
% vend = [];
% ini = 0;
% fim = 0;
% vthr = [];
% jj = 1;
% 
% while jj <= length(cD2h)-swin
%     avgs = mean(cD2h(jj:jj+swin-1));
%     if avgs > limsig & ini == 0
%         vbeg = [vbeg jj-5];
%         ini = 1;
%     end
%     
%     if avgs < limsig & ini == 1
%         ini = 0;
%         vend = [vend jj+5];
%         jj = jj+swin;
%     end
%     jj = jj+1;
% end
% 
% for ii = 1:length(vend) - 1
%     vthr(ii) = max(abs(cD2(vend(ii):vbeg(ii+1))));
% end
% v4max = zeros(1,5);
% 
% 
% 
% 
% for jj = 1:length(v4max)
%     pmax_atual = find(vthr == max(vthr));
%     v4max(jj) = vthr(pmax_atual);
%     vthr(pmax_atual) = 0;
% end
% 
% lim_def = 0.5*mean(v4max);
% 
% figure;plot(cD2);
% hold on;plot(lim_def*ones(1,length(cD2)),'k');
% plot(-lim_def*ones(1,length(cD2)),'k');
% 
% keyboard;



% posz = find(abs(cD2)<=lim_def);
% cD2(posz)=0;
% posnz1 = find(cD2>lim_def);
% cD2(posnz1) = cD2(posnz1) - lim_def;
% posnz2 = find(cD2<-1*lim_def);
% cD2(posnz2) = cD2(posnz2) + lim_def;


%% Fase de Reconstrução

%%% Superamostragem cA2 ----> A2r %%%

scA2r = dyadup(cA2,2);
scD2 = dyadup(cD2,2);
scA2r = [scA2r 0];
scD2 = [scD2 0];

if length(scA2r) > length(A2)
    scA2r = scA2r(1:end-1);
    scD2 = scD2(1:end-1);
end



A2r1 = filtfilt(Lo_R,1,scA2r);
A2r = 0.5*A2r1(Lfs+1:length(A2r1)-Lfs);

D2r1 = filtfilt(Hi_R,1,scD2);
D2r = 0.5*D2r1(Lfs+1:length(D2r1)-Lfs);

%cA1r = A2r + D2r;
cA1r = A2r;



%%% Superamostragem cA1r ----> A1r %%%

scA1r = dyadup(cA1r,2);
scA1r = [scA1r 0];
scD1 = dyadup(cD1,2);
scD1 = [scD1 0];


if length(scA1r) > length(A1)
    scA1r = scA1r(1:end-1);
    scD1 = scD1(1:end-1);
end




%%% Reconstrução Sr = A1r + D1r %%%

A1r1 = filtfilt(Lo_R,1,scA1r);
A1r = 0.5*A1r1(Lfs+1:length(A1r1)-Lfs);

D1r1 = filtfilt(Hi_R,1,scD1);
D1r = 0.5*D1r1(Lfs+1:length(D1r1)-Lfs);


%S1 = A1r + D1r;
S1 = A1r;




% figure;
% subplot(2,1,1);
% plot((1:length(trecho))/sfreq,trecho);
% hold on;
% plot((1:length(S1))/sfreq,S1,'r');
% 
% subplot(2,1,2);
% plot((1:length(Sr))/sfreq,Sr,'r');
% 
% 
% F1 = abs(fft(trecho));
% F1 = fftshift(F1);
% 
% F2 = abs(fft(S1));
% F2 = fftshift(F2);
% 
% F3 = abs(fft(trechof));
% F3 = fftshift(F3);
% 
% xf = linspace(-sfreq/2,sfreq/2,length(F1));
% 
% figure;
% 
% plot(xf,F1);hold on;
% 
% plot(xf,F2,'r');
% plot(xf,F3,'k');
% 
% figure;
% subplot(3,1,1);
% plot((1:length(trecho))/sfreq,trecho);
% subplot(3,1,2);
% plot((1:length(S1))/sfreq,S1,'r');
% subplot(3,1,3);
% plot((1:length(trechof))/sfreq,trechof,'k');
% 
% 
% 
% 
% keyboard;



