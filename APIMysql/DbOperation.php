<?php

Class DbOperation {

	private $pdo = null; 
	private static $dbOperation = null; 

	private function __construct($conexao) {  
		$this->pdo = $conexao;  
	}  

	public static function getInstance($conexao) {   
		if (!isset(self::$dbOperation)) {    
			self::$dbOperation = new DbOperation($conexao);   
		}

		return self::$dbOperation;    
	} 

	function login($cpf, $senha) {
		try {	
			$stmt = $this->pdo->prepare("SELECT p_cpf, p_nome, p_data_nasc, p_email, p_senha, p_sexo, p_altura, p_peso, p_telefone,
											e_ecg_id, e_ecg_file, e_imc, e_data_hora
										FROM paciente 
										LEFT OUTER JOIN ecg ON (p_cpf = e_paciente_cpf and date(e_data_hora) = CURRENT_DATE)
										WHERE p_cpf = md5(?) and p_senha = md5(?) 
										ORDER BY e_data_hora DESC");
			$stmt->bindValue(1, $cpf);
			$stmt->bindValue(2, $senha);
			$stmt->execute();

			$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$paciente = array();
			$todayEcg = array();
			foreach ($result as $field) {
				$ecg = array();

				$paciente['cpf'] = $field['p_cpf'];
				$paciente['nome'] = $field['p_nome'];
				$paciente['data_nasc'] = $field['p_data_nasc'];
				$paciente['email'] = $field['p_email'];
				$paciente['senha'] = $field['p_senha'];
				$paciente['sexo'] = $field['p_sexo'];
				$paciente['altura'] = $field['p_altura'];
				$paciente['peso'] = $field['p_peso'];
				$paciente['telefone'] = $field['p_telefone'];
				$ecg['ecg_id'] = $field['e_ecg_id'];
				$ecg['ecg_file'] = $field['e_ecg_file'];
				$ecg['imc'] = $field['e_imc'];
				$ecg['data_hora'] = $field['e_data_hora'];

				array_push($todayEcg, $ecg);
			}
			$paciente['ecg'] = $todayEcg;

		} catch(PDOException $e) {
				print "Erro: " . $e->getMessage();   
		}

		return $paciente; 
	}

	function getDiagnosticList($cpf) {
		try {	
			$stmt = $this->pdo->prepare("SELECT d_ecg_id, d_diagnostico_id, d_descricao, d_data_hora_diagnostico, m_nome, m_crm 
										FROM ecg
										INNER JOIN paciente ON (p_cpf = e_paciente_cpf) 
										INNER JOIN diagnostico ON (d_ecg_id = e_ecg_id)
										INNER JOIN medico ON (d_crm = m_crm)
										WHERE p_cpf = ?
										ORDER BY d_data_hora_diagnostico DESC");
			$stmt->bindValue(1, $cpf);
			$stmt->execute();

			$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$diagnosticos = array();
			foreach ($result as $field) {
				$diagnostico = array();
				$diagnostico['ecg_id'] = $field['d_ecg_id'];
				$diagnostico['diagnostico_id'] = $field['d_diagnostico_id'];
				$diagnostico['descricao'] = $field['d_descricao'];
				$diagnostico['data_hora_diagnostico'] = $field['d_data_hora_diagnostico'];
				$diagnostico['nome'] = $field['m_nome'];
				$diagnostico['crm'] = $field['m_crm'];

				array_push($diagnosticos, $diagnostico);
			}

		} catch(PDOException $e) {
				print "Erro: " . $e->getMessage();   
		}

		return $diagnosticos; 
	}

	function getDiagnosticEcgList($ecgId) {
		try {	
			$stmt = $this->pdo->prepare("SELECT d_diagnostico_id, d_crm, d_ecg_id, 
										d_descricao, d_data_hora_diagnostico, m_nome
										FROM ecg
										INNER JOIN diagnostico on (d_ecg_id = e_ecg_id)
										INNER JOIN medico on (d_crm = m_crm)
										WHERE e_ecg_id = ?
										ORDER BY d_data_hora_diagnostico DESC
										LIMIT 1");
			$stmt->bindValue(1, $ecgId);
			$stmt->execute();

			$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$diagnosticoEcg = array();
			foreach ($result as $field) {
				$diagnosticoEcg['diagnostico_id'] = $field['d_diagnostico_id'];
				$diagnosticoEcg['ecg_id'] = $field['d_ecg_id'];
				$diagnosticoEcg['crm'] = $field['d_crm'];
				$diagnosticoEcg['descricao'] = $field['d_descricao'];
				$diagnosticoEcg['data_hora_diagnostico'] = $field['d_data_hora_diagnostico'];
				$diagnosticoEcg['nome'] = $field['m_nome'];
			}

		} catch(PDOException $e) {
				print "Erro: " . $e->getMessage();   
		}

		return $diagnosticoEcg; 
	}

	function getHistoricList($cpf) {
		try {	
			$stmt = $this->pdo->prepare("SELECT e_ecg_id, e_ecg_file, e_imc, e_paciente_cpf, e_data_hora
										FROM ecg
										WHERE e_paciente_cpf = ?
										ORDER BY e_data_hora DESC");
			$stmt->bindValue(1, $cpf);
			$stmt->execute();

			$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$historico = array();
			foreach ($result as $field) {
				$exame = array();
				$exame['ecg_id'] = $field['e_ecg_id'];
				$exame['file'] = $field['e_ecg_file'];
				$exame['imc'] = $field['e_imc'];
				$exame['cpf'] = $field['e_paciente_cpf'];
				$exame['data_hora_historico'] = $field['e_data_hora'];

				array_push($historico, $exame);
			}

		} catch(PDOException $e) {
				print "Erro: " . $e->getMessage();   
		}

		return $historico; 
	}
	
}


