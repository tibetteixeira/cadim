%%% Implementando Wavelets para time wandering %%%

function[S1] = FiltroECGWandering_Wavelet3_13112017(exame,sfreq,name_filtro)

trecho=exame;

if length(name_filtro) == 3

if sum(name_filtro == 'db1') == 3
    
    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db1.mat');
    Hi_D = teste.Hi_D;
    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db1.mat');
    Lo_D = teste.Lo_D;
    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db1.mat');
    Hi_R = teste.Hi_R;
    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db1.mat');
    Lo_R = teste.Lo_R;
    
    
else if sum(name_filtro == 'db2') == 3
        
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db2.mat');
            Hi_D = teste.Hi_D;
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db2.mat');
            Lo_D = teste.Lo_D;
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db2.mat');
            Hi_R = teste.Hi_R;
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db2.mat');
            Lo_R = teste.Lo_R;
            
        
    else if sum(name_filtro == 'db3') == 3
            
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db3.mat');
            Hi_D = teste.Hi_D;
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db3.mat');
            Lo_D = teste.Lo_D;
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db3.mat');
            Hi_R = teste.Hi_R;
            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db3.mat');
            Lo_R = teste.Lo_R;
            
            
        else if sum(name_filtro == 'db4') == 3
                
                teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db4.mat');
                Hi_D = teste.Hi_D;
                teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db4.mat');
                Lo_D = teste.Lo_D;
                teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db4.mat');
                Hi_R = teste.Hi_R;
                teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db4.mat');
                Lo_R = teste.Lo_R;
                
                
            else if sum(name_filtro == 'db5') == 3
                    
                     teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db5.mat');
                    Hi_D = teste.Hi_D;
                    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db5.mat');
                    Lo_D = teste.Lo_D;
                    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db5.mat');
                    Hi_R = teste.Hi_R;
                    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db5.mat');
                    Lo_R = teste.Lo_R;
                    
                    
                else if sum(name_filtro == 'db6') == 3
                        
                         teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db6.mat');
                        Hi_D = teste.Hi_D;
                        teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db6.mat');
                        Lo_D = teste.Lo_D;
                        teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db6.mat');
                        Hi_R = teste.Hi_R;
                        teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db6.mat');
                        Lo_R = teste.Lo_R;
                        
                    else if sum(name_filtro == 'db7') == 3
                            
                             teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db7.mat');
                            Hi_D = teste.Hi_D;
                            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db7.mat');
                            Lo_D = teste.Lo_D;
                            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db7.mat');
                            Hi_R = teste.Hi_R;
                            teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db7.mat');
                            Lo_R = teste.Lo_R;
                            
                        else if sum(name_filtro == 'db8') == 3
                                
                                 teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db8.mat');
                                Hi_D = teste.Hi_D;
                                teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db8.mat');
                                Lo_D = teste.Lo_D;
                                teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db8.mat');
                                Hi_R = teste.Hi_R;
                                teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db8.mat');
                                Lo_R = teste.Lo_R;
                                
                            else if sum(name_filtro == 'db9') == 3
                                    
                                     teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db9.mat');
                                    Hi_D = teste.Hi_D;
                                    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db9.mat');
                                    Lo_D = teste.Lo_D;
                                    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db9.mat');
                                    Hi_R = teste.Hi_R;
                                    teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db9.mat');
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
    
     teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_D_db10.mat');
     Hi_D = teste.Hi_D;
     teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_D_db10.mat');
     Lo_D = teste.Lo_D;
     teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Hi_R_db10.mat');
     Hi_R = teste.Hi_R;
     teste=load('C:\Users\joaop\OneDrive\Documentos\UNILAB\Orientações\IC\Julito\Códigos\Lo_R_db10.mat');
     Lo_R = teste.Lo_R;
     
     
end
    
                
                    
%[Lo_D,Hi_D,Lo_R,Hi_R]=wfilters(name_filtro);
Lfs = length(Lo_D);



%%% Decomposição 1

%A1 = 0.5*FiltFiltJP(trecho,Lo_D);
A1 = 0.5*filtfilt(Lo_D,1,trecho);
%D1 = 0.5*FiltFiltJP(trecho,Hi_D);
D1 = 0.5*filtfilt(Hi_D,1,trecho);

A1 = [A1(1)*ones(1,sfreq) A1 A1(end)*ones(1,sfreq)];
D1 = [D1(1)*ones(1,sfreq) D1 D1(end)*ones(1,sfreq)];


%%% Subamostragem %%%

ii = 1;
nii = 1;
sA1 = [];
sD1 = [];
sA1 = A1(1:2:end);
sD1 = D1(1:2:end);

cA1 = sA1;
cD1 = sD1;


%%% Decomposição 2

% A2 = 0.5*FiltFiltJP(cA1,Lo_D);
% D2 = 0.5*FiltFiltJP(cA1,Hi_D);

A2 = 0.5*filtfilt(Lo_D,1,cA1);
D2 = 0.5*filtfilt(Hi_D,1,cA1);


A2 = [A2(1)*ones(1,sfreq) A2 A2(end)*ones(1,sfreq)];
D2 = [D2(1)*ones(1,sfreq) D2 D2(end)*ones(1,sfreq)];


%%% Subamostragem %%%

ii = 1;
nii = 1;
sA2 = [];
sD2 = [];

sA2 = A2(1:2:end);
sD2 = D2(1:2:end);


cA2 = sA2;
cD2 = sD2;



%%% Decomposição 3

% A3 = 0.5*FiltFiltJP(cA2,Lo_D);
% D3 = 0.5*FiltFiltJP(cA2,Hi_D);

A3 = 0.5*filtfilt(Lo_D,1,cA2);
D3 = 0.5*filtfilt(Hi_D,1,cA2);

A3 = [A3(1)*ones(1,sfreq) A3 A3(end)*ones(1,sfreq)];
D3 = [D3(1)*ones(1,sfreq) D3 D3(end)*ones(1,sfreq)];

%%% Subamostragem %%%

ii = 1;
nii = 1;
sA3 = [];
sD3 = [];

sA3 = A3(1:2:end);
sD3 = D3(1:2:end);


cA3 = sA3;
cD3 = sD3;



%%% Decomposição 4

% A4 = 0.5*FiltFiltJP(cA3,Lo_D);
% D4 = 0.5*FiltFiltJP(cA3,Hi_D);

A4 = 0.5*filtfilt(Lo_D,1,cA3);
D4 = 0.5*filtfilt(Hi_D,1,cA3);

A4 = [A4(1)*ones(1,sfreq) A4 A4(end)*ones(1,sfreq)];
D4 = [D4(1)*ones(1,sfreq) D4 D4(end)*ones(1,sfreq)];

%%% Subamostragem %%%

ii = 1;
nii = 1;
sA4 = [];
sD4 = [];

sA4 = A4(1:2:end);
sD4 = D4(1:2:end);

cA4 = sA4;
cD4 = sD4;


%%% Decomposição 5

% A5 = 0.5*FiltFiltJP(cA4,Lo_D);
% D5 = 0.5*FiltFiltJP(cA4,Hi_D);

A5 = 0.5*filtfilt(Lo_D,1,cA4);
D5 = 0.5*filtfilt(Hi_D,1,cA4);

A5 = [A5(1)*ones(1,sfreq) A5 A5(end)*ones(1,sfreq)];
D5 = [D5(1)*ones(1,sfreq) D5 D5(end)*ones(1,sfreq)];

%%% Subamostragem %%%

ii = 1;
nii = 1;
sA5 = [];
sD5 = [];

sA5 = A5(1:2:end);
sD5 = D5(1:2:end);


cA5 = sA5;
cD5 = sD5;



%%% Decomposição 6

% A6 = 0.5*FiltFiltJP(cA5,Lo_D);
% D6 = 0.5*FiltFiltJP(cA5,Hi_D);

A6 = 0.5*filtfilt(Lo_D,1,cA5);
D6 = 0.5*filtfilt(Hi_D,1,cA5);

A6 = [A6(1)*ones(1,sfreq) A6 A6(end)*ones(1,sfreq)];
D6 = [D6(1)*ones(1,sfreq) D6 D6(end)*ones(1,sfreq)];

%%% Subamostragem %%%

ii = 1;
nii = 1;
sA6 = [];
sD6 = [];

sA6 = A6(1:2:end);
sD6 = D6(1:2:end);


cA6 = sA6;
cD6 = sD6;



% %%% Decomposição 7
% 
% % A7 = 0.5*FiltFiltJP(cA6,Lo_D);
% % D7 = 0.5*FiltFiltJP(cA6,Hi_D);
% 
% A7 = 0.5*filtfilt(Lo_D,1,cA6);
% D7 = 0.5*filtfilt(Hi_D,1,cA6);
% 
% A7 = [A7(1)*ones(1,sfreq) A7 A7(end)*ones(1,sfreq)];
% D7 = [D7(1)*ones(1,sfreq) D7 D7(end)*ones(1,sfreq)];
% 
% 
% 
% %%% Subamostragem %%%
% 
% ii = 1;
% nii = 1;
% sA7 = [];
% sD7 = [];
% 
% sA7 = A7(1:2:end);
% sD7 = D7(1:2:end);
% 
% 
% cA7 = sA7;
% cD7 = sD7;



% %%% Decomposição 8
% 
% A8 = 0.5*FiltFiltJP(cA7,Lo_D);
% D8 = 0.5*FiltFiltJP(cA7,Hi_D);
% 
% A8 = [A8(1)*ones(1,sfreq) A8 A8(end)*ones(1,sfreq)];
% D8 = [D8(1)*ones(1,sfreq) D8 D8(end)*ones(1,sfreq)];
% 
% 
% %%% Subamostragem %%%
% 
% ii = 1;
% nii = 1;
% sA8 = [];
% sD8 = [];
% 
% for ii=1:length(A8);
%     if mod(ii,2) == 1
%         sA8(nii) = A8(ii);
%         sD8(nii) = D8(ii);
%         nii = nii+1;
%     end
% end
% 
% cA8 = sA8;
% cD8 = sD8;



%%%% FASE DE RECONSTRUÇÃO %%%%



% %%% Superamostragem  cA8 ---> A8r %%%
% 
% nii = 1;
% sA8 = [];
% sD8 = [];
% 
% for ii = 1:length(cA8)
%     
%     sA8 = [sA8 cA8(ii)];
%     sA8 = [sA8 0];
%     sD8 = [sD8 cD8(ii)];
%     sD8 = [sD8 0];
%     
%        
% end
% 
% %% Avaliar a possibilidade de não eliminar este 0 %%%
% 
% if length(sA8) > length(A8)
%     sA8 = sA8(1:end-1);
%     sD8 = sD8(1:end-1);
% end
% 
% %%% Reconstrução cA7r = A8r + D8r %%%
% 
% A8r1 = FiltFiltJP(sA8,Lo_R);
% A8r = A8r1(sfreq+1:length(A8r1)-sfreq);
% 
% 
% 
% 
% cA7r = A8r;


% %%% Superamostragem cA7r ----> A7r %%%
% 
% nii = 1;
% scA7r = [];
% scD7 = [];
% 
% scA7r = zeros(1,2*length(cA7));
% scA7r(1:2:end-1) = cA7;
% 
% scD7 = zeros(1,2*length(cD7));
% scD7(1:2:end-1) = cD7;
% 
% 
% % for ii = 1:length(cA7)
% %     
% %     scA7r = [scA7r cA7(ii)];
% %     scA7r = [scA7r 0];
% %     scD7 = [scD7 cD7(ii)];
% %     scD7 = [scD7 0];
% %     
% %     
% %     
% % end
% 
% if length(scA7r) > length(A7)
%   scA7r = scA7r(1:end-1);
%   scD7 = scD7(1:end-1);
% end
% 
% 
% %%% Reconstrução cA6r = A7r + D7r %%%
% 
% A7r1 = filtfilt(Lo_R,1,scA7r);
% A7r = A7r1(sfreq+1:length(A7r1)-sfreq);
% 
% cA6r = A7r;
% 

%%% Superamostragem cA6r ----> A6r %%%

nii = 1;
scA6r = [];
scD6 = [];

scA6r = zeros(1,2*length(cA6));
scA6r(1:2:end-1) = cA6;

scD6 = zeros(1,2*length(cD6));
scD6(1:2:end-1) = cD6;

% for ii = 1:length(cA6r)
%     
%     scA6r = [scA6r cA6r(ii)];
%     scA6r = [scA6r 0];
%     scD6 = [scD6 cD6(ii)];
%     scD6 = [scD6 0];
%     
%     
%     
% end

if length(scA6r) > length(A6)
    scA6r = scA6r(1:end-1);
    scD6 = scD6(1:end-1);
end

%%% Reconstrução cA5r = A6r + D6r %%%

A6r1 = filtfilt(Lo_R,1,scA6r);
A6r = A6r1(sfreq+1:length(A6r1)-sfreq);



cA5r = A6r;


%%% Superamostragem cA5r ----> A5r %%%

nii = 1;
scA5r = [];
scD5 = [];

scA5r = zeros(1,2*length(cA5r));
scA5r(1:2:end-1) = cA5r;

scD5 = zeros(1,2*length(cD5));
scD5(1:2:end-1) = cD5;


% for ii = 1:length(cA5r)
%     
%     scA5r = [scA5r cA5r(ii)];
%     scA5r = [scA5r 0];
%     scD5 = [scD5 cD5(ii)];
%     scD5 = [scD5 0];
%     
%     
%     
% end

if length(scA5r) > length(A5)
    scA5r = scA5r(1:end-1);
    scD5 = scD5(1:end-1);
end

%%% Reconstrução cA4r = A5r + D5r %%%

A5r1 = filtfilt(Lo_R,1,scA5r);
A5r = A5r1(sfreq+1:length(A5r1)-sfreq);


cA4r = A5r;


%%% Superamostragem cA4r ----> A4r %%%


nii = 1;
scA4r = [];
scD4 = [];

scA4r = zeros(1,2*length(cA4r));
scA4r(1:2:end-1) = cA4r;

scD4 = zeros(1,2*length(cD4));
scD4(1:2:end-1) = cD4;


% for ii = 1:length(cA4r)
%     
%     scA4r = [scA4r cA4r(ii)];
%     scA4r = [scA4r 0];
%     scD4 = [scD4 cD4(ii)];
%     scD4 = [scD4 0];
%     
% end

if length(scA4r) > length(A4)
    scA4r = scA4r(1:end-1);
    scD4 = scD4(1:end-1);
end



%%% Reconstrução cA3r = A4r + D4r %%%

A4r1 = filtfilt(Lo_R,1,scA4r);
A4r = A4r1(sfreq+1:length(A4r1)-sfreq);


cA3r = A4r;


%%% Superamostragem cA3r ----> A3r %%%

nii = 1;
scA3r = [];
scD3 = [];

scA3r = zeros(1,2*length(cA3r));
scA3r(1:2:end-1) = cA3r;

scD3 = zeros(1,2*length(cD3));
scD3(1:2:end-1) = cD3;


% for ii = 1:length(cA3r)
%     
%     scA3r = [scA3r cA3r(ii)];
%     scA3r = [scA3r 0];
%     scD3 = [scD3 cD3(ii)];
%     scD3 = [scD3 0];
%     
% end

if length(scA3r) > length(A3)
 scA3r = scA3r(1:end-1);
 scD3 = scD3(1:end-1);
end



%%% Reconstrução cA2r = A3r + D3r %%%

A3r1 = filtfilt(Lo_R,1,scA3r);
A3r = A3r1(sfreq+1:length(A3r1)-sfreq);


cA2r = A3r;


%%% Superamostragem cA2r ----> A2r %%%

nii = 1;
scA2r = [];
scD2 = [];

scA2r = zeros(1,2*length(cA2r));
scA2r(1:2:end-1) = cA2r;

scD2 = zeros(1,2*length(cD2));
scD2(1:2:end-1) = cD2;


% for ii = 1:length(cA2r)
%     
%     scA2r = [scA2r cA2r(ii)];
%     scA2r = [scA2r 0];
%     scD2 = [scD2 cD2(ii)];
%     scD2 = [scD2 0];
%     
% end

if length(scA2r) > length(A2)
    scA2r = scA2r(1:end-1);
    scD2 = scD2(1:end-1);
end


%%% Reconstrução cA1r = A2r + D2r %%%

A2r1 = filtfilt(Lo_R,1,scA2r);
A2r = A2r1(sfreq+1:length(A2r1)-sfreq);


cA1r = A2r;


%%% Superamostragem cA1r ----> A1r %%%

nii = 1;
scA1r = [];
scD1 = [];

scA1r = zeros(1,2*length(cA1r));
scA1r(1:2:end-1) = cA1r;

scD1 = zeros(1,2*length(cD1));
scD1(1:2:end-1) = cD1;

% for ii = 1:length(cA1r)
%     
%     scA1r = [scA1r cA1r(ii)];
%     scA1r = [scA1r 0];
%     scD1 = [scD1 cD1(ii)];
%     scD1 = [scD1 0];
%     
% end

if length(scA1r) > length(A1)
    scA1r = scA1r(1:end-1);
    scD1 = scD1(1:end-1);
end



%%% Reconstrução Sr = A1r + D1r %%%

A1r1 = filtfilt(Lo_R,1,scA1r);
A1r = A1r1(sfreq+1:length(A1r1)-sfreq);


Sr = A1r;
S1=trecho-Sr;



