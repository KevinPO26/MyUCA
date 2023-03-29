<?php

if($_SERVER['REQUEST_METHOD'] == "PUT"){
        
    require_once 'conexion.php';
    $id=$_GET['resource_id'];
    $nombres = $_GET["nombres"];
    $apellidos = $_GET["apellidos"];
    $fechaNac = $_GET["fechaNac"];
    $titulo = $_GET["titulo"];
    $email = $_GET["email"];
    $facultad = $_GET["facultad"];
    $query = "UPDATE coordinador SET nombres= '$nombres', apellidos= '$apellidos', fechaNac= '$fechaNac', titulo= '$titulo', email= '$email', facultad= '$facultad' WHERE idC='$id'";
    $resultado=$con->query($query);
    if($con->affected_rows >0 && $resultado==true){
        echo "El coordinador se actualizo correctamente";
    }else{
        echo "Error al editar";
    }
    $con->close();
         
}
?>