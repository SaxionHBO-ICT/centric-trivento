<?php
    include("connect.php");

    if (!empty($_GET['category'])) {
        $category = " WHERE categorie LIKE '" . $_GET['category'] . "'";
    }
    
    //Haal alle kroegen op uit de tabel. Als er categorieën zijn ingesteld, haal alleen de kroegen op die bij die categorie horen
    $sql = "SELECT kroeg_id, naam, adres, openingstijden, beschrijving, url, categorie FROM kroegen" . $category;
    $result = mysqli_query($connection, $sql);

    $json = array();
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($json, $row);
    }
    header('Content-type: application/json');
    echo(json_encode($json));
    mysqli_close($connection);