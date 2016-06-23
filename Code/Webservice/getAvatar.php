<?php
    include("connect.php");

    if(!empty($_GET['id'])){
        $id = $_GET['id'];
    } else {
        die("missing id");
    }
    
    //Haal de avatar (LONGBLOB) van een kroeg uit de tabel. Echo die na de base64_encode erop aan te roepen
    $sql = "SELECT avatar FROM kroegen WHERE kroeg_id=$id";

    $result = mysqli_query($connection, $sql);

    $image = mysqli_fetch_assoc($result);

    echo (base64_encode($image['avatar']));