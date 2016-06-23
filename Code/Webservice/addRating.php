<?php
    include "connect.php";

    $rating = $_GET['rating'];
    $gebruikersid = $_GET['gebruikersid'];
    $kroegid = $_GET['kroegid'];

    //Voeg een rating toe als er nog geen rating met de primary key(kroeg_id, gebruikers_id) bestaat, als er al een rating op die primary key bestaat,
    //update dan de waarde van die rating
    $sql = "INSERT INTO ratings(kroeg_id, gebruikers_id, kroeg_rating) VALUES($kroegid, $gebruikersid, $rating) ON DUPLICATE KEY UPDATE kroeg_id=$kroegid, gebruikers_id=$gebruikersid, kroeg_rating=$rating";
    $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));

    if ($result == true) {
        echo("success: " . $kroegid . " - " . $gebruikersid . " - " . $rating);
    } else {
        echo("missing variables");
    }
    mysqli_close($connection);