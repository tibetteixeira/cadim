%%% Implementando Wavelets para remoção de ruído de alta frequência %%%

function[S1] = FiltroECGMuscular_WaveletFs256_TesteArduino(sinal,sfreq,name_filtro)

trecho=sinal;
%[Lo_D,Hi_D,Lo_R,Hi_R]=wfilters('db4');

% Lo_D = [-0.0105974018 0.0328830117 0.0308413818 -0.1870348117 0.0279837694 0.6308807679 0.7148465706 0.2303778133];
% Hi_D = [-0.2303778133 0.7148465706 -0.6308807679 -0.0279837694 0.1870348117 0.0308413818 -0.0328830117 -0.0105974018];
% Lo_R = [0.2303778133 0.7148465706 0.6308807679 -0.0279837694 -0.1870348117 0.0308413818 0.0328830117 -0.0105974018];
% Hi_R = [-0.0105974018 -0.0328830117 0.0308413818 0.1870348117 -0.0279837694 -0.6308807679 0.7148465706 -0.2303778133];

path = 'C:\projetos\cadim\MATLAB\Filtros';



if length(name_filtro) == 3

if sum(name_filtro == 'db1') == 3
    
    teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db1.mat');
    Hi_D = teste.Hi_D;
    teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db1.mat');
    Lo_D = teste.Lo_D;
    teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db1.mat');
    Hi_R = teste.Hi_R;
    teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db1.mat');
    Lo_R = teste.Lo_R;
    
else if sum(name_filtro == 'db2') == 3
        
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db2.mat');
            Hi_D = teste.Hi_D;
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db2.mat');
            Lo_D = teste.Lo_D;
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db2.mat');
            Hi_R = teste.Hi_R;
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db2.mat');
            Lo_R = teste.Lo_R;
            
        
    else if sum(name_filtro == 'db3') == 3
            
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db3.mat');
            Hi_D = teste.Hi_D;
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db3.mat');
            Lo_D = teste.Lo_D;
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db3.mat');
            Hi_R = teste.Hi_R;
            teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db3.mat');
            Lo_R = teste.Lo_R;
            
            
        else if sum(name_filtro == 'db4') == 3
                
                teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db4.mat');
                Hi_D = teste.Hi_D;
                teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db4.mat');
                Lo_D = teste.Lo_D;
                teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db4.mat');
                Hi_R = teste.Hi_R;
                teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db4.mat');
                Lo_R = teste.Lo_R;
                
                
            else if sum(name_filtro == 'db5') == 3
                    
                     teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db5.mat');
                    Hi_D = teste.Hi_D;
                    teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db5.mat');
                    Lo_D = teste.Lo_D;
                    teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db5.mat');
                    Hi_R = teste.Hi_R;
                    teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db5.mat');
                    Lo_R = teste.Lo_R;
                    
                    
                else if sum(name_filtro == 'db6') == 3
                        
                         teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db6.mat');
                        Hi_D = teste.Hi_D;
                        teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db6.mat');
                        Lo_D = teste.Lo_D;
                        teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db6.mat');
                        Hi_R = teste.Hi_R;
                        teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db6.mat');
                        Lo_R = teste.Lo_R;
                        
                    else if sum(name_filtro == 'db7') == 3
                            
                             teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db7.mat');
                            Hi_D = teste.Hi_D;
                            teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db7.mat');
                            Lo_D = teste.Lo_D;
                            teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db7.mat');
                            Hi_R = teste.Hi_R;
                            teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db7.mat');
                            Lo_R = teste.Lo_R;
                            
                        else if sum(name_filtro == 'db8') == 3
                                
                                 teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db8.mat');
                                Hi_D = teste.Hi_D;
                                teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db8.mat');
                                Lo_D = teste.Lo_D;
                                teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db8.mat');
                                Hi_R = teste.Hi_R;
                                teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db8.mat');
                                Lo_R = teste.Lo_R;
                                
                            else if sum(name_filtro == 'db9') == 3
                                    
                                     teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db9.mat');
                                    Hi_D = teste.Hi_D;
                                    teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db9.mat');
                                    Lo_D = teste.Lo_D;
                                    teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db9.mat');
                                    Hi_R = teste.Hi_R;
                                    teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db9.mat');
                                    Lo_R = teste.Lo_R;
                                    
                                    
                                end
                                
                            end
                            
                            
                        end
                        
                    end
                    
                end
                
            end
            
        end
        
    end
    
end

else
    
     teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_D_db10.mat');
     Hi_D = teste.Hi_D;
     teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_D_db10.mat');
     Lo_D = teste.Lo_D;
     teste=load('C:\projetos\cadim\MATLAB\Filtros\Hi_R_db10.mat');
     Hi_R = teste.Hi_R;
     teste=load('C:\projetos\cadim\MATLAB\Filtros\Lo_R_db10.mat');
     Lo_R = teste.Lo_R;
     
     
end


Lfs = 2*length(Lo_D);


%%% Decomposição 1

A1 = filtfilt(Lo_D,1,trecho);
D1 = filtfilt(Hi_D,1,trecho);

A1 = [A1(1)*ones(1,Lfs) A1 A1(end)*ones(1,Lfs)];
D1 = [D1(1)*ones(1,Lfs) D1 D1(end)*ones(1,Lfs)];


%%% Subamostragem %%%



sA1 = A1(1:2:end);
sD1 = D1(1:2:end);

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

sA2 = A2(1:2:end);
sD2 = D2(1:2:end);

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

scA2r = zeros(1,2*length(cA2));
scA2r(1:2:end-1) = cA2;

scD2 = zeros(1,2*length(cD2));
scD2(1:2:end-1) = cD2;

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

scA1r = zeros(1,2*length(cA1r));
scA1r(1:2:end-1) = cA1r;

scD1 = zeros(1,2*length(cD1));
scD1(1:2:end-1) = cD1;


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



