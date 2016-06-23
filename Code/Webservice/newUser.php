<?php
    include "connect.php";

    //Maak een nieuwe gebruiker aan met de juiste variabelen die uit de POST worden gehaald
    if (!empty($_POST['voornaam']) && !empty($_POST['achternaam']) && !empty($_POST['adres']) && !empty($_POST['postcode']) && !empty($_POST['woonplaats']) && !empty(['land']) && !empty($_POST['email']) && !empty($_POST['wachtwoord'])) {

        $voornaam = $_POST['voornaam'];
        $achternaam = $_POST['achternaam'];
        $adres = $_POST['adres'];
        $postcode = $_POST['postcode'];
        $woonplaats = $_POST['woonplaats'];
        $land = $_POST['land'];
        $email = $_POST['email'];
        $wachtwoord = $_POST['wachtwoord'];

        $sql = "INSERT INTO `gebruikers` (`voornaam`, `achternaam`, `adres`, `postcode`, `woonplaats`, `land`, `mail`, `wachtwoord`) VALUES ('$voornaam', '$achternaam', '$adres', '$postcode', '$woonplaats', '$land', '$email', '$wachtwoord')";
        $result = mysqli_query($connection, $sql);

        if ($result == true) {
            echo("success");
        }
    } else {
        echo("missing variables");
    }
    mysqli_close($connection);