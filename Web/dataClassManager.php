<?php
require_once('define.php');
require_once('dbManager.php');

class dataClassManager {
    private $db;

    public function __construct(){
        $this->db = new dbManager();
    }

    public function getParameters(){
        // Esto para cuando estén los idiomas
        $sql = "SELECT dc.*, t._value FROM dataclass dc, _translation t, _language l
                WHERE dc.id=t.dataclass_id AND t.language_id=l.id AND l.name=";
        if (isset($_SESSION['lang'])) $sql.="'{$_SESSION['lang']}'";
        else $sql.="'en'";
        return $this->db->launchQuery('SELECT * FROM dataclass ORDER BY name');
    }
    // Given a dataclass, the function returns a list of the values that each district has on this dataclass
    public function getDistrictValues($dataclass_id){
        return $this->db->launchQuery("SELECT dis.*, dv._value, dv.age FROM district dis LEFT JOIN district_value dv ON dv.district_id=dis.id AND dv.dataclass_id={$dataclass_id}");
    }

    public function getNeighbourhoodValues($dataclass_id){
        return $this->db->launchQuery("SELECT ngh.*, nv._value, nv.age FROM neighbourhood ngh LEFT JOIN neighbourhood_value nv ON nv.neighbourhood_id=ngh.id AND nv.dataclass_id={$dataclass_id}");
    }

    public function getIndicator($dataClassWeights){
        foreach ($dataClassWeights as $dataclass_id=>$dataclass_weight) {
            $values = $this->getNeighbourhoodValues($dataclass_id);
            if (!is_null($values)) $normalisedValues=$this->normaliseValues($values);
        }
    }

    private function normaliseValues($values){
        if (sort($values, SORT_NUMERIC)){
            $min = $values[0]; $max = $values[count($values)-1];
            $difference = $max-$min;
            foreach($values as $value) $normalisedValues[]=(($values-$min)/$difference)*100;
            return $normalisedValues;
        }
        else return null;
    }

}
?>
