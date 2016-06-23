<?php
    /**
     * Haal alle verschillende categorieën die in gebruik zijn uit de tabel kroegen
     */
    include("connect.php");

    $sql = "SELECT DISTINCT categorie FROM kroegen";
    $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));

    $json = array();

    while($row = mysqli_fetch_assoc($result)){
        array_push($json, $row);
    }

    header('Content-type: application/json');
    echo(json_encode($json));
    mysqli_close($connection);