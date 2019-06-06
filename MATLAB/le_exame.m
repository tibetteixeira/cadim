function[Exame]=le_exame(Nome,tempo,hora,lead)

fid1=fopen(Nome,'r');
if fid1 ~= -1
    [TMP,COUNT] = fscanf(fid1,'%s',15);
    ct = 1;
    op = 1;
    disp('Inicio da Aquisiçao');

    Exame = [];
    tempo=1*60*1000;
    tempo=tempo/4;
    [A,count] = fscanf(fid1,'%d',[13,inf]);
    Exame = A;
    disp('Exame Lido');
    Exame = Exame(2,:);
    fclose(fid1);
    Fam=250;
    Exame = Exame(1:length(Exame));
    exame = Exame';

    save exame exame;

    disp('Exame Processado');

else path = 'C:\Users\João Paulo\Documents\UNILAB\Co-orientação DC_UFC\Normal Sinus Rhythm';
    
     cab = lerHEA_QT(Nome,path);
     tempo = tempo*60;
     tamanho = tempo*cab.sfreq;
     tamanho = cab.tamanho;
     %hora = 0;keyboard;
     if (cab.tamanho/cab.sfreq)/3600 >= 23
         

         %hora = str2double(get(handles.v24h,'String'));
         [exame,t] = lerDAT24h(Nome,path,cab,(hora+1)*60*1*60*cab.sfreq);
         %hora = hora + 1;
         %keyboard;
         %set(handles.v24h,'String',hora);    
    else
    [exame,t] = lerDAT(Nome,path,cab,tamanho);
    end
    %exame=exame(:,1);
    freq = cab.sfreq;
    anot = lerATR(Nome,path);
    tipo = anot.tipo;
    anot = anot.indice;
    sfreq = cab.sfreq;
    upanot = find(abs(anot-length(exame)) == min(abs(anot-length(exame))));
    anot = anot(1:upanot);
    save sfreq sfreq; 
    save exame exame;
    save anot anot;
    save tipo tipo;
    
end