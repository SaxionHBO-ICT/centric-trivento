<?php
    //Maak verbinding met de database met de volgende gegevens:
    $host = "eu-cdbr-azure-west-d.cloudapp.net:3306";
    $gebruikersnaam = "bff895cf4b4f0c";
    $wachtwoord = "7dfc2fe3";
    $database = "deventerkroegen";

    $connection = mysqli_connect("$host", "$gebruikersnaam", "$wachtwoord", $database);
    if(mysqli_connect_errno()){
        echo("Failed to connect to MySQL: " . mysqli_connect_error());
    }