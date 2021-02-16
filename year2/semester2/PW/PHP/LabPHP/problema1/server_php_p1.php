<?php

function reafiseazaHTML(){
    echo ("
    <div>Campurile nu pot ramane goale!</div>
    <form method=\"post\" action=\"http://localhost/temeweb/labphp/problema1/server.php\">
        Sursa: <input type=\"text\" name=\"orasPlecare\"><br>
        Destinatie: <input type=\"text\" name=\"orasSosire\"/><br>
        Ruta Directa: <input type=\"checkbox\" name=\"direct\"/><br>
        <input type=\"Submit\" value=\"Afiseaza\">
        </form>");
}

$orasPlecare =  $_POST["orasPlecare"];
$orasSosire = $_POST["orasSosire"];

if($orasPlecare=="" || $orasSosire==""){
    reafiseazaHTML();
    return;
}

$connection =  mysqli_connect('localhost','root','','temaphp');
//$rezultat = $connection->query("SELECT * FROM rute where destinatie=\"$oras\" or sursa=\"$oras\" " );
 if(isset($_POST["direct"]))
   {
       $rezultat = $connection->query("SELECT * FROM problema1 where sursa=\"$orasPlecare\" and destinatie=\"$orasSosire\"" );
       echo "Rezultatul cautarii este: <br>";
       while($linie = $rezultat->fetch_assoc()){
            echo $linie["nrTren"]." ".$linie["tipTren"]." ".$linie["sursa"]." ".$linie["destinatie"]." ".$linie["oraPlecare"]." ".$linie["oraSosire"]."<br>";
        }
   }
   else
   {
        $rezultat1 = $connection->query("SELECT * FROM problema1 where sursa=\"$orasPlecare\"");
        echo "Rezultatul cautarii este: <br>";
        while($linie = $rezultat1->fetch_assoc()){
            //echo $linie["nrTren"]." ".$linie["tipTren"]." ".$linie["sursa"]." ".$linie["destinatie"]." ".$linie["oraPlecare"]." ".$linie["oraSosire"]."<br>";
            $variabila = $linie["destinatie"];
            $rezultat2 = $connection->query("SELECT * FROM problema1 where sursa=\"$variabila\"");
            while($linie2 = $rezultat2->fetch_assoc()){
                if($linie2["destinatie"]==$orasSosire)
                    echo $orasPlecare." ".$linie["destinatie"]." ".$orasSosire."<br>";
            }
     }
   }
   
?>