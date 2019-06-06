<?php 

require_once 'DbOperation.php';
require_once 'DbConnect.php';

$op = DbOperation::getInstance(DbConnect::getInstance());

function isTheseParametersAvailable($params) {
	//assuming all parameters are available 
	$available = true; 
	$missingparams = ""; 

	foreach($params as $param) {
		if(!isset($_POST[$param]) || strlen($_POST[$param])<=0) {
			$available = false; 
			$missingparams = $missingparams . ", " . $param; 
		}
	}

	if(!$available) {
		$response = array(); 
		$response['error'] = true; 
		$response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';

		//displaying error
		echo json_encode($response);

		//stopping further execution
		die();
	}
}

$response = array();

if(isset($_GET['apicall'])) {

	switch($_GET['apicall']) {

		case 'login':
			isTheseParametersAvailable(array('cpf', 'senha'));
			$response['error'] = false; 
			$response['message'] = 'Login realizado com sucesso';
			$response['login'] = $op->login($_POST['cpf'], $_POST['senha']);
			break; 

		case 'diagnosticList':
			isTheseParametersAvailable(array('cpf'));
			$response['error'] = false;
			$response['message'] = 'Diagnósticos listados com sucesso';
			$response['diagnostic'] = $op->getDiagnosticList($_POST['cpf']);
			break;
	}

} else {
	$response['error'] = true; 
	$response['message'] = 'Invalid API Call';
}

echo json_encode($response);