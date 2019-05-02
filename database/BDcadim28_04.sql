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
-- Estrutura da tabela `cardiopatia`
--

CREATE TABLE `cardiopatia` (
  `card_id` int(11) NOT NULL,
  `card_descricao` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `diagnostico`
--

CREATE TABLE `diagnostico` (
  `diagnostico_id` int(11) NOT NULL,
  `descricao` varchar(400) NOT NULL,
  `ecg_id` int(11) NOT NULL,
  `crm` int(11) NOT NULL,
  `telefone` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecg`
--

CREATE TABLE `ecg` (
  `ecg_id` int(11) NOT NULL,
  `pac_id` int(11) NOT NULL,
  `ecg_file` varchar(100) NOT NULL,
  `imc` double DEFAULT NULL,
  `marcapasso` char(1) DEFAULT NULL,
  `pressao_sistolica` int(11) DEFAULT NULL,
  `cancer` char(1) DEFAULT NULL,
  `pressao_diastolica` int(11) DEFAULT NULL,
  `tabagismo` char(1) DEFAULT NULL,
  `alcoolismo` char(1) DEFAULT NULL,
  `sincope` char(1) DEFAULT NULL,
  `sedentarismo` char(1) DEFAULT NULL,
  `data_hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fibrilacao_flutter` char(1) DEFAULT NULL,
  `avc` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecg_cardiopatia`
--

CREATE TABLE `ecg_cardiopatia` (
  `ecg_id` int(11) NOT NULL,
  `card_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecg_remedio`
--

CREATE TABLE `ecg_remedio` (
  `ecg_id` int(11) NOT NULL,
  `rem_id` int(11) NOT NULL,
  `dosagem` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `medico`
--

CREATE TABLE `medico` (
  `crm` int(10) NOT NULL,
  `cpf` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `data_nasc` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `genero` varchar(1) NOT NULL,
  `telefone` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `pac`
--

CREATE TABLE `pac` (
  `pac_id` int(11) NOT NULL,
  `altura` int(11) NOT NULL,
  `cpf` int(11) NOT NULL,
  `data_nasc` date DEFAULT NULL,
  `data_obito` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `genero` varchar(255) NOT NULL,
  `naturalidade` varchar(255) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `peso` int(11) DEFAULT NULL,
  `senha` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `pac`
--

INSERT INTO `pac` (`pac_id`, `altura`, `cpf`, `data_nasc`, `data_obito`, `email`, `genero`, `naturalidade`, `nome`, `peso`, `senha`, `telefone`) VALUES
(1, 160, 123, NULL, NULL, 'pp@gmail', 'M', NULL, 'perere', 25, 'pp123', '12345678');

-- --------------------------------------------------------

--
-- Estrutura da tabela `paciente`
--

CREATE TABLE `paciente` (
  `pac_id` int(10) NOT NULL,
  `cpf` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `data_nasc` date DEFAULT NULL,
  `data_obito` date DEFAULT NULL,
  `naturalidade` varchar(2) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `genero` varchar(1) NOT NULL,
  `altura` int(4) NOT NULL,
  `peso` int(4) NOT NULL,
  `telefone` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `paciente`
--

INSERT INTO `paciente` (`pac_id`, `cpf`, `nome`, `data_nasc`, `data_obito`, `naturalidade`, `email`, `senha`, `genero`, `altura`, `peso`, `telefone`) VALUES
(1, 1674254, 'san diego', '2019-04-01', '2019-04-27', 'CE', '123456', '123456', 'm', 123, 70, '123456654');

-- --------------------------------------------------------

--
-- Estrutura da tabela `remedio`
--

CREATE TABLE `remedio` (
  `rem_id` int(9) NOT NULL,
  `rme_nome` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `remedio_paciente`
--

CREATE TABLE `remedio_paciente` (
  `pac_id` int(11) NOT NULL,
  `rem_id` int(11) NOT NULL,
  `dosagem` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cardiopatia`
--
ALTER TABLE `cardiopatia`
  ADD PRIMARY KEY (`card_id`);

--
-- Indexes for table `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD PRIMARY KEY (`diagnostico_id`),
  ADD KEY `ConstraintDiagnostico_ecg_id` (`ecg_id`),
  ADD KEY `ConstraintDiagnostico_crm` (`crm`);

--
-- Indexes for table `ecg`
--
ALTER TABLE `ecg`
  ADD PRIMARY KEY (`ecg_id`),
  ADD KEY `ConstEcg_pacient_id` (`pac_id`);

--
-- Indexes for table `ecg_cardiopatia`
--
ALTER TABLE `ecg_cardiopatia`
  ADD PRIMARY KEY (`ecg_id`,`card_id`),
  ADD KEY `ConstraintEcgCard_pac_id` (`card_id`);

--
-- Indexes for table `ecg_remedio`
--
ALTER TABLE `ecg_remedio`
  ADD PRIMARY KEY (`ecg_id`,`rem_id`),
  ADD KEY `ConstraintEcgRem_rem_id` (`rem_id`);

--
-- Indexes for table `medico`
--
ALTER TABLE `medico`
  ADD PRIMARY KEY (`crm`),
  ADD UNIQUE KEY `cpf` (`cpf`);

--
-- Indexes for table `pac`
--
ALTER TABLE `pac`
  ADD PRIMARY KEY (`pac_id`);

--
-- Indexes for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`pac_id`),
  ADD UNIQUE KEY `cpf` (`cpf`);

--
-- Indexes for table `remedio`
--
ALTER TABLE `remedio`
  ADD PRIMARY KEY (`rem_id`);

--
-- Indexes for table `remedio_paciente`
--
ALTER TABLE `remedio_paciente`
  ADD PRIMARY KEY (`pac_id`,`rem_id`),
  ADD KEY `ConstraintRemPac_rem_id` (`rem_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cardiopatia`
--
ALTER TABLE `cardiopatia`
  MODIFY `card_id` int(11) NOT NULL AUTO_INCREMENT;

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
-- AUTO_INCREMENT for table `pac`
--
ALTER TABLE `pac`
  MODIFY `pac_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `paciente`
--
ALTER TABLE `paciente`
  MODIFY `pac_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `remedio`
--
ALTER TABLE `remedio`
  MODIFY `rem_id` int(9) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `diagnostico`
--
ALTER TABLE `diagnostico`
  ADD CONSTRAINT `ConstraintDiagnostico_crm` FOREIGN KEY (`crm`) REFERENCES `medico` (`crm`),
  ADD CONSTRAINT `ConstraintDiagnostico_ecg_id` FOREIGN KEY (`ecg_id`) REFERENCES `ecg` (`ecg_id`);

--
-- Limitadores para a tabela `ecg`
--
ALTER TABLE `ecg`
  ADD CONSTRAINT `ConstEcg_pacient_id` FOREIGN KEY (`pac_id`) REFERENCES `paciente` (`pac_id`);

--
-- Limitadores para a tabela `ecg_cardiopatia`
--
ALTER TABLE `ecg_cardiopatia`
  ADD CONSTRAINT `ConstraintEcgCard_ecg_id` FOREIGN KEY (`ecg_id`) REFERENCES `ecg` (`ecg_id`),
  ADD CONSTRAINT `ConstraintEcgCard_pac_id` FOREIGN KEY (`card_id`) REFERENCES `cardiopatia` (`card_id`);

--
-- Limitadores para a tabela `ecg_remedio`
--
ALTER TABLE `ecg_remedio`
  ADD CONSTRAINT `ConstraintEcgRem_ecg_id` FOREIGN KEY (`ecg_id`) REFERENCES `ecg` (`ecg_id`),
  ADD CONSTRAINT `ConstraintEcgRem_rem_id` FOREIGN KEY (`rem_id`) REFERENCES `remedio` (`rem_id`);

--
-- Limitadores para a tabela `remedio_paciente`
--
ALTER TABLE `remedio_paciente`
  ADD CONSTRAINT `ConstraintRemPac_pac_id` FOREIGN KEY (`pac_id`) REFERENCES `paciente` (`pac_id`),
  ADD CONSTRAINT `ConstraintRemPac_rem_id` FOREIGN KEY (`rem_id`) REFERENCES `remedio` (`rem_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
