<?php
require_once('define.php');
class dbManager  {
	private $host 		= DB_HOST;
	private $user 		= DB_USER;
	private $password 	= DB_PASS;
	private $dbname 	= DB_NAME;
	private $con		= null;
	// The construct function initializes class attributes accordingly to its declaration order and depending on the number of arguments given when calling it.
	// Important, only 4 parameters should be sent .
	public function __construct (){
            $vars = get_class_vars('dbManager');
            if (count($vars)<func_num_args()) throw new Exception ('Too many arguments. Only four arguments needed: Host, Username, Password and DBName');
            for($i=0; $i<func_num_args();$i++) $this->${$vars[$i]} = func_get_arg($i);
	}
	
	private function select_db(){
            return mysql_select_db($this->dbname, $this->con);
	}
	
	public function connect() {
            $this->con = mysql_pconnect($this->host, $this->user, $this->password);
            if (!is_resource($this->con)) throw new Exception ('Error connecting to database.');
            if (!$this->select_db()) throw new Exception ('Error selecting database');
	}

	public function disconnect() {
            mysql_close($this->con);
	}
	
	function escape($string){
            return mysql_escape_string($string);
	}

        function startTransaction(){
            try {
                if (!$this->con) $this->connect();
            }
            catch (Exception $e) {
                print($e->getMessage());
            }
            $idQuery = @mysql_query("START TRANSACTION", $this->con);
        }

        function commit(){
            try {
                if (!$this->con) $this->connect();
            }
            catch (Exception $e) {
                print($e->getMessage());
            }
            $idQuery = @mysql_query("COMMIT", $this->con);
        }

        function rollback(){
            try {
                if (!$this->con) $this->connect();
            }
            catch (Exception $e) {
                print($e->getMessage());
            }
            $idQuery = @mysql_query("ROLLBACK", $this->con);
        }


	// This function is used when launching SELECT queries (SELECT, SHOW...).
	function launchQuery($sql) {
            try {
                if (!$this->con) $this->connect();
            }
            catch (Exception $e) {
                print($e->getMessage());
            }
            @mysql_query("SET NAMES utf8");
            $idQuery = @mysql_query($sql, $this->con);
            if(!$idQuery) return null;
            while($row = mysql_fetch_assoc($idQuery)) $res[] = $row;
            return $res;
	}

	// This function is used when launching UPDATE and DELETE queries.
	function modifyRow($sql) {
            try{
                if (!$this->con) $this->connect();
                $result = @mysql_query($sql);
                $error = mysql_error($this->con);
            }
            catch (Exception $e) {
                print($e->getMessage());
            }
            if($error != '') return false;
            return $result;
	}

        function numRows(){
            return mysql_affected_rows($this->con);
        }
	function lastInsertId(){
            return mysql_insert_id($this->con);
        }
	// This function is used when launching INSERT queries.
	function insertRow($sql) {
          
                try{
			if (!$this->con) $this->connect();
			$result = @mysql_query($sql);
			$error = mysql_error($this->con);
                        print($error);
		}
		catch (Exception $e) {
			print($e->getMessage());
		}
		if($error != '') return false;
                else{  //print($sql);
                    $aux = $this->lastInsertId();
                    if ($aux==0) $aux=1;
                    return $aux;
                }
	}
	
        // This fucntion describes the given table name
        public function describe ($tableName){
            return $this->db->launchQuery("DESCRIBE $tableName");
        }

	// This function allows to retrieve the content of a table (full content or just a set of columns).
	function listTable($tableName, $orderBy=null){
		$numColumns = func_num_args();
		$sql = 'SELECT ';
		if ($numColumns == 2) $sql .= '*';
		else {
                    for ($i=2; $i<$numColumns; $i++) $sql .= func_get_arg($i).', ';
                    $sql = substr($sql, 0, strlen($sql)-2);
		}
		$sql .= " FROM $tableName";
                if (isset($orderBy)) $sql .= " ORDER BY $orderBy ";
               
		return $this->launchQuery($sql);
	}
		
	// This function is used to launch a set of queries(SELECT, SHOW; not modifying queries). The response is a set of responses which are matrixes themselves.
	function launchQueryList($queryList, $functionName=''){
		$result=array();
		try{
			if (!$this->con) $this->connect();
		}
		catch (Exception $e){
			print($e->getMessage());
		}
		foreach ($queryList AS $query){
			$idQuery = @mysql_query($query); 
			$resAux=array();
			if (!$idQuery) throw new Exception("Error launching query: ".$sql."\n from function: ".$functionName.":\n".mysql_error($this->con));
			while ($row = mysql_fetch_assoc($consulta)) $resAux[]=$row;
			$result[]=$resAux;
		}
		return($result);
	}
}
?>