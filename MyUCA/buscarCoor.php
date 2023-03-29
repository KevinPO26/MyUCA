<?php

if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once 'conexion.php';
    $id=$_GET['resource_id'];
    $query="SELECT * FROM coordinador WHERE idC='".$id."'";
    $resultado=$con->query($query);
    if($con->affected_rows > 0){
        while($row=$resultado->fetch_assoc()){
            $array=$row;
        
        }
        echo json_encode($array);
    }else{
        echo "No hay registros";

    }
    $resultado->close();
    $con->close();
}

?>