<?php 

define('HOST', 'localhost');   
define('DBNAME', 'id9560428_cadim');   
define('CHARSET', 'utf8');   
define('USER', 'id9560428_cadim');   
define('PASSWORD', 'brasil158');

class DbConnect {
	private static $pdo; 
	
	public static function getInstance() {   
		if (!isset(self::$pdo)) {   
			try {   
				$opcoes = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES UTF8', PDO::ATTR_PERSISTENT => TRUE);   
				self::$pdo = new PDO("mysql:host=" . HOST . "; dbname=" . DBNAME . "; charset=" . CHARSET . ";", USER, PASSWORD, $opcoes);
				self::$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);   
			} catch (PDOException $e) {   
				print "Erro: " . $e->getMessage();   
			}   
		}   
		return self::$pdo;   
	}
}