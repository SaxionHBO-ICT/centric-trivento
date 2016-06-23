<?php
    include "connect.php";

    if(!empty($_POST['username']) && !empty($_POST['password'])){
        $mail = $_POST['username'];
        $password = $_POST['password'];
    }

    //Kijk of de username(mailadres) en het wachtwoord van de gebruiker overeenkomen met hetgeen dat in de POST staat
    $sql = "SELECT gebruiker_id FROM gebruikers WHERE mail='$mail' AND wachtwoord='$password'";
    $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));
    $rows = $result->num_rows;

    $json = new stdClass();

    if($rows == 1){
        $json->accepted = true;
        while($row = mysqli_fetch_assoc($result)){
            $json->gebruiker_id = $row['gebruiker_id'];
        }
    } else {
        $json->accepted = false;
    }

    header('Content-type: application/json');
    echo(json_encode($json));
    mysqli_close($connection);