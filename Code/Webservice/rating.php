<?php
    /**
     * Bereken de gemiddelde rating (afgerond op halven (0.5, 1, 1.5, etc)) om de rating bar in de app mee te vullen
     */
    include "connect.php";

    if (!empty($_GET['kroeg_id'])) {
        $kroeg_id = $_GET['kroeg_id'];
    }

    $sql = "SELECT kroeg_rating FROM ratings WHERE kroeg_id = '$kroeg_id'";
    $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));

    $json = new stdClass();
    $total = 0;
    $i = 0;
    //Tel het aantal ratings die er van die kroeg zijn en tel alle ratings bij elkaar op, waaruit het gemiddelde berekend kan worden
    while ($row = mysqli_fetch_assoc($result)) {
        //$json->rating = $row['rating'];
        $total += $row['kroeg_rating'];
        $i++;
    }
    $total = $total/$i;
    $total = floor($total * 2)/2;
    $json->rating = $total;
    header('Content-type: application/json');
    echo(json_encode($json));
    mysqli_close($connection);