<?php
    if($_SERVER['REQUEST_METHOD'] == "DELETE"){
        
    require_once 'conexion.php';
    
    $id=$_GET['resource_id'];
    $q = "DELETE FROM coordinador WHERE idC = '".$id."'";
    $query = mysqli_query($con, $q);

    if($query == true){
        echo 'coordinador eliminada correctamente';
    }else{
        echo 'Error al eliminar';
    }
             
    }
?>