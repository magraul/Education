<?php
header('Content-Type: text/xml');
echo '<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>';
echo '<response>';

$oras = $_GET['oras1'];
if($oras !="getAll"){
        $connection =  mysqli_connect('localhost','root','','ajax_db_web');
        $rezultat = $connection->query("SELECT * FROM rute where oras1=\"$oras\" " );
        while($linie = $rezultat->fetch_assoc()){
                if($linie["oras1"]==$oras)
                        echo $linie["oras2"].",";
        }
        $rezultat->close();
} else if ($oras == "getAll") {
        $connection =  mysqli_connect('localhost','root','','ajax_db_web');
        $rezultat = $connection->query("SELECT * FROM rute");
        while($linie = $rezultat->fetch_assoc()){
                echo $linie["oras1"].",";
        }
        $rezultat->close();

}
else{
        echo 'Nu';
}
echo '</response>';
?>
