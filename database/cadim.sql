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
  `d_diagnostico_id` int(11) NOT NULL,
  `d_crm` varchar (10) NOT NULL,
  `d_ecg_id` int(11) NOT NULL,
  `d_descricao` varchar(500) NOT NULL,
  `d_data_hora_diagnostico` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecg`
--

CREATE TABLE `ecg` (
  `e_ecg_id` int(11) NOT NULL,
  `e_paciente_cpf` varchar(11) NOT NULL,
  `e_ecg_file` varchar(100) NOT NULL,
  `e_imc` decimal(4, 2) DEFAULT NULL,
  `e_data_hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecg_remedio`
--

CREATE TABLE `ecg_remedio` (
  `er_ecg_id` int(11) NOT NULL,
  `er_rem_id` int(11) NOT NULL,
  `er_dosagem` float NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `medico`
--

CREATE TABLE `medico` (
  `m_crm` varchar(10) NOT NULL,
  `m_cpf` varchar(11) NOT NULL,
  `m_nome` varchar(100) NOT NULL,
  `m_data_nasc` date NOT NULL,
  `m_email` varchar(50) NOT NULL,
  `m_senha` varchar(20) NOT NULL,
  `m_sexo` varchar(1) NOT NULL,
  `m_telefone` int(11) NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `paciente`
--

CREATE TABLE `paciente` (
  `p_cpf` varchar(11) NOT NULL,
  `p_nome` varchar(100) NOT NULL,
  `p_data_nasc` date DEFAULT NULL,
  `p_email` varchar(50) NOT NULL,
  `p_senha` varchar(20) NOT NULL,
  `p_sexo` varchar(1) DEFAULT NULL,
  `p_altura` smallint DEFAULT NULL,
  `p_peso` decimal(5, 2) NOT NULL,
  `p_telefone` int(11) NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `remedio`
--

CREATE TABLE `remedio` (
  `r_rem_id` int(11) NOT NULL,
  `r_rem_nome` varchar(30) NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `remedio_paciente`
--

CREATE TABLE `remedio_paciente` (
  `rp_paciente_cpf` varchar(11) NOT NULL,
  `rp_rem_id` int(11) NOT NULL,
  `rp_dosagem` float NOT NULL
);

-- --------------------------------------------------------

-- Indexes for dumped tables
--

--
-- Indexes for table `diagnostico`
--
ALTER TABLE diagnostico
ADD INDEX (`d_diagnostico_id`);

--
-- Indexes for table `ecg`
--
ALTER TABLE ecg
ADD INDEX (`e_ecg_id`);

--
-- Indexes for table `ecg_remedio`
--
ALTER TABLE ecg_remedio
ADD INDEX (`er_ecg_id`,`er_rem_id`);

--
-- Indexes for table `medico`
--
ALTER TABLE medico
ADD INDEX (`m_crm`);

--
-- Indexes for table `paciente`
--
ALTER TABLE paciente
ADD INDEX (`p_cpf`);

--
-- Indexes for table `remedio`
--
ALTER TABLE remedio
ADD INDEX (`r_rem_id`);

--
-- Indexes for table `remedio_paciente`
--
ALTER TABLE remedio_paciente
ADD INDEX (`rp_paciente_cpf`,`rp_rem_id`);


-- --------------------------------------------------------

-- Primary keys for dumped tables
--

--
-- Primary key for table `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD PRIMARY KEY (`d_diagnostico_id`);

--
-- Indexes for table `ecg`
--
ALTER TABLE `ecg`
  ADD PRIMARY KEY (`e_ecg_id`);

--
-- Primary key for table `ecg_remedio`
--
ALTER TABLE `ecg_remedio`
  ADD PRIMARY KEY (`er_ecg_id`,`er_rem_id`);

--
-- Primary key for table `medico`
--
ALTER TABLE `medico`
  ADD PRIMARY KEY (`m_crm`);

--
-- Primary key for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`p_cpf`);

--
-- Primary key for table `remedio`
--
ALTER TABLE `remedio`
  ADD PRIMARY KEY (`r_rem_id`);

--
-- Primary key for table `remedio_paciente`
--
ALTER TABLE `remedio_paciente`
  ADD PRIMARY KEY (`rp_paciente_cpf`,`rp_rem_id`);


--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `diagnostico`
--
ALTER TABLE `diagnostico`
  MODIFY `d_diagnostico_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ecg`
--
ALTER TABLE `ecg`
  MODIFY `e_ecg_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `remedio`
--
ALTER TABLE `remedio`
  MODIFY `r_rem_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `medico`
--

ALTER TABLE `medico`
  ADD CONSTRAINT `unique_cpf_medico` UNIQUE KEY (`m_crm`);

--
-- Limitadores para a tabela `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD CONSTRAINT `diag_medico` FOREIGN KEY (`d_crm`) REFERENCES `medico` (`m_crm`),
  ADD CONSTRAINT `diag_ecg` FOREIGN KEY (`d_ecg_id`) REFERENCES `ecg` (`e_ecg_id`);

--
-- Limitadores para a tabela `ecg`
--
ALTER TABLE `ecg`
  ADD CONSTRAINT `ecg_paciente` FOREIGN KEY (`e_paciente_cpf`) REFERENCES `paciente` (`p_cpf`);

--
-- Limitadores para a tabela `ecg_remedio`
--
ALTER TABLE `ecg_remedio`
  ADD CONSTRAINT `ecg_remedio_ecg` FOREIGN KEY (`er_ecg_id`) REFERENCES `ecg` (`e_ecg_id`),
  ADD CONSTRAINT `ecg_remedio_rem` FOREIGN KEY (`er_rem_id`) REFERENCES `remedio` (`r_rem_id`);

--
-- Limitadores para a tabela `remedio_paciente`
--
ALTER TABLE `remedio_paciente`
  ADD CONSTRAINT `remedio_paciente_pac` FOREIGN KEY (`rp_paciente_cpf`) REFERENCES `paciente` (`p_cpf`),
  ADD CONSTRAINT `remedio_paciente_rem` FOREIGN KEY (`rp_rem_id`) REFERENCES `remedio` (`r_rem_id`);


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
