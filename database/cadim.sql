-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 28-Abr-2019 às 23:54
-- Versão do servidor: 10.1.38-MariaDB
-- versão do PHP: 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `BDcadim`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `diagnostico`
--

CREATE TABLE `diagnostico` (
  `diagnostico_id` int(11) NOT NULL,
  `crm` varchar (10) NOT NULL,
  `ecg_id` int(11) NOT NULL,
  `descricao` varchar(500) NOT NULL,
  `data_hora_diagnostico` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecg`
--

CREATE TABLE `ecg` (
  `ecg_id` int(11) NOT NULL,
  `paciente_cpf` varchar(11) NOT NULL,
  `ecg_file` varchar(100) NOT NULL,
  `imc` double DEFAULT NULL,
  `data_hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecg_remedio`
--

CREATE TABLE `ecg_remedio` (
  `ecg_id` int(11) NOT NULL,
  `rem_id` int(11) NOT NULL,
  `dosagem` double NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `medico`
--

CREATE TABLE `medico` (
  `crm` varchar(10) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `data_nasc` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `sexo` varchar(1) NOT NULL,
  `telefone` int(11) NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `paciente`
--

CREATE TABLE `paciente` (
  `cpf` varchar(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `data_nasc` date DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `sexo` varchar(1) DEFAULT NULL,
  `altura` int(3) DEFAULT NULL,
  `peso` double NOT NULL,
  `telefone` int(11) NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `remedio`
--

CREATE TABLE `remedio` (
  `rem_id` int(11) NOT NULL,
  `rem_nome` varchar(30) NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `remedio_paciente`
--

CREATE TABLE `remedio_paciente` (
  `paciente_cpf` varchar(11) NOT NULL,
  `rem_id` int(11) NOT NULL,
  `dosagem` double NOT NULL
);

-- --------------------------------------------------------

-- Indexes for dumped tables
--

--
-- Indexes for table `diagnostico`
--
ALTER TABLE diagnostico
ADD INDEX (`diagnostico_id`);

--
-- Indexes for table `ecg`
--
ALTER TABLE ecg
ADD INDEX (`ecg_id`);

--
-- Indexes for table `ecg_remedio`
--
ALTER TABLE ecg_remedio
ADD INDEX (`ecg_id`,`rem_id`);

--
-- Indexes for table `medico`
--
ALTER TABLE medico
ADD INDEX (`crm`);

--
-- Indexes for table `paciente`
--
ALTER TABLE paciente
ADD INDEX (`cpf`);

--
-- Indexes for table `remedio`
--
ALTER TABLE remedio
ADD INDEX (`rem_id`);

--
-- Indexes for table `remedio_paciente`
--
ALTER TABLE remedio_paciente
ADD INDEX (`paciente_cpf`,`rem_id`);


-- --------------------------------------------------------

-- Primary keys for dumped tables
--

--
-- Primary key for table `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD PRIMARY KEY (`diagnostico_id`);

--
-- Indexes for table `ecg`
--
ALTER TABLE `ecg`
  ADD PRIMARY KEY (`ecg_id`);

--
-- Primary key for table `ecg_remedio`
--
ALTER TABLE `ecg_remedio`
  ADD PRIMARY KEY (`ecg_id`,`rem_id`);

--
-- Primary key for table `medico`
--
ALTER TABLE `medico`
  ADD PRIMARY KEY (`crm`);

--
-- Primary key for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`cpf`);

--
-- Primary key for table `remedio`
--
ALTER TABLE `remedio`
  ADD PRIMARY KEY (`rem_id`);

--
-- Primary key for table `remedio_paciente`
--
ALTER TABLE `remedio_paciente`
  ADD PRIMARY KEY (`paciente_cpf`,`rem_id`);


--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `diagnostico`
--
ALTER TABLE `diagnostico`
  MODIFY `diagnostico_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ecg`
--
ALTER TABLE `ecg`
  MODIFY `ecg_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `remedio`
--
ALTER TABLE `remedio`
  MODIFY `rem_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `medico`
--

ALTER TABLE `medico`
  ADD CONSTRAINT `unique_cpf_medico` UNIQUE KEY (`crm`);

--
-- Limitadores para a tabela `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD CONSTRAINT `diag_medico` FOREIGN KEY (`crm`) REFERENCES `medico` (`crm`),
  ADD CONSTRAINT `diag_ecg` FOREIGN KEY (`ecg_id`) REFERENCES `ecg` (`ecg_id`);

--
-- Limitadores para a tabela `ecg`
--
ALTER TABLE `ecg`
  ADD CONSTRAINT `ecg_paciente` FOREIGN KEY (`paciente_cpf`) REFERENCES `paciente` (`cpf`);

--
-- Limitadores para a tabela `ecg_remedio`
--
ALTER TABLE `ecg_remedio`
  ADD CONSTRAINT `ecg_remedio_ecg` FOREIGN KEY (`ecg_id`) REFERENCES `ecg` (`ecg_id`),
  ADD CONSTRAINT `ecg_remedio_rem` FOREIGN KEY (`rem_id`) REFERENCES `remedio` (`rem_id`);

--
-- Limitadores para a tabela `remedio_paciente`
--
ALTER TABLE `remedio_paciente`
  ADD CONSTRAINT `remedio_paciente_pac` FOREIGN KEY (`paciente_cpf`) REFERENCES `paciente` (`cpf`),
  ADD CONSTRAINT `remedio_paciente_rem` FOREIGN KEY (`rem_id`) REFERENCES `remedio` (`rem_id`);


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
