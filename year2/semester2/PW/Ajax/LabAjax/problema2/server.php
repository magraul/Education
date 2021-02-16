<?php
   header('Content-Type: text/xml');
   echo '<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>';
   echo '<response>';
   $pagina = $_GET['pagina'];
   if($pagina!=""){
   $connection =  mysqli_connect('localhost','root','','pw');
   $rezultat = $connection->query("SELECT * FROM problema2" );
   $elemente = array();
   while($linie = $rezultat->fetch_assoc()){
        $elemente[] = $linie;
    }
   for($i=($pagina-1)*3;$i<$pagina*3;$i=$i+1) 
        echo $elemente[$i]['nume'].' '.$elemente[$i]['prenume'].' '.$elemente[$i]['telefon'].' '.$elemente[$i]['email'].'#';
   $rezultat->close(); 
   }
   else{
       echo 'Nu';
   }
   echo '</response>';
?>