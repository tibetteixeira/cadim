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
			$stmt = $this->pdo->prepare("SELECT p_nome, p_data_nasc FROM paciente WHERE p_cpf = ? and p_senha = ? ");
			$stmt->bindValue(1, $cpf);
			$stmt->bindValue(2, $senha);
			$stmt->execute();

			$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
			foreach ($result as $field) {
				$paciente = array();
				$paciente['nome'] = $field['p_nome'];
				$paciente['data_nasc'] = $field['p_data_nasc'];
			}

		} catch(PDOException $e) {
				print "Erro: " . $e->getMessage();   
		}

		return $paciente; 
	}

	function getDiagnosticList($cpf) {
		try {	
			$stmt = $this->pdo->prepare("SELECT d_ecg_id, d_diagnostico_id, d_descricao, d_data_hora_diagnostico, m_nome, m_crm 
										FROM ecg
										INNER JOIN paciente ON (p_cpf = p_paciente_cpf) 
										INNER JOIN diagnostico ON (d_ecg_id = e_ecg_id)
										INNER JOIN medico ON (d_crm = m_crm)
										WHERE p_cpf = ?");
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
	
}


