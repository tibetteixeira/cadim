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
			$stmt = $this->pdo->prepare("SELECT nome, data_nasc FROM paciente WHERE cpf = ? and senha = ? ");
			$stmt->bindValue(1, $cpf);
			$stmt->bindValue(2, $senha);
			$stmt->execute();

			$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
			foreach ($result as $field) {
				$paciente = array();
				$paciente['nome'] = $field['nome'];
				$paciente['data_nasc'] = $field['data_nasc'];
			}

		} catch(PDOException $e) {
				print "Erro: " . $e->getMessage();   
		}

		return $paciente; 
	}
}


