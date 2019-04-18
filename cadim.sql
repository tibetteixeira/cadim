CREATE DATABASE cadim;


CREATE TABLE medico (
	crm INTEGER NOT NULL ,
	cpf INTEGER NOT NULL,
	nome VARCHAR (255) NOT NULL,
	email VARCHAR (255) NOT NULL,
	senha VARCHAR (255) NOT NULL,
	data_nascimento DATE NOT NULL,
	sexo VARCHAR (1) NOT NULL,
	telefone INTEGER NOT NULL,
	PRIMARY KEY (crm)
);

CREATE TABLE paciente (
	cpf INTEGER NOT NULL,
	nome VARCHAR (255) NOT NULL,
	email VARCHAR (255) NOT NULL,
	senha VARCHAR (255) NOT NULL,
	data_nascimento DATE NOT NULL,
	sexo VARCHAR (1) NOT NULL,
	telefone INTEGER NOT NULL,
	peso DECIMAL (5),
	altura SMALLINT,
	medico INTEGER, 
	PRIMARY KEY (cpf),
	FOREIGN KEY (medico) REFERENCES medico (crm)
	ON UPDATE CASCADE
	ON DELETE NO ACTION
);

CREATE TABLE exame (
	codigo_exame INTEGER NOT NULL,
	data_hora_exame TIMESTAMP NOT NULL,
	sinal_ecg TEXT NOT NULL,
	paciente INTEGER NOT NULL,
	PRIMARY KEY (codigo_exame),
	FOREIGN KEY (paciente) REFERENCES paciente (cpf)
	ON UPDATE CASCADE
	ON DELETE NO ACTION
);

CREATE TABLE diagnostico (
	codigo_diagnostico INTEGER NOT NULL,
	exame INTEGER NOT NULL,
	data_hora_diagnostico TIMESTAMP NOT NULL,
	descricao TEXT NOT NULL,
	medico INTEGER NOT NULL,
	PRIMARY KEY (codigo_diagnostico),
	FOREIGN KEY (medico) REFERENCES medico (crm)
	ON UPDATE CASCADE
	ON DELETE NO ACTION, 
	FOREIGN KEY (exame) REFERENCES exame (codigo_exame)
	ON UPDATE CASCADE
	ON DELETE NO ACTION
);
