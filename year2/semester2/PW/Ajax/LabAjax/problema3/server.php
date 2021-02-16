<?php
   header('Content-Type: text/xml');
   echo '<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>';
   echo '<response>';
   if(isset($_GET['id'])){
      echo '<metoda>';
      echo 'id';
      echo '</metoda>';
      echo '<rezultat>';
      $id = $_GET['id'];
      $connection =  mysqli_connect('localhost','root','','ajax_db_web');

      $rezultat = $connection->query("SELECT * FROM problema3 where id=".$id );
      $elemente = array();
      while($linie = $rezultat->fetch_assoc()){
         echo $linie['marca'].'#'.$linie['model'];
      }
      echo '</rezultat>';
      $rezultat->close();

    }
   if(isset($_GET['size'])){
      echo '<metoda>';
      echo 'size';
      echo '</metoda>';
      echo '<rezultat>';
      $connection =  mysqli_connect('localhost','root','','ajax_db_web');

      $rezultat = $connection->query("SELECT MAX(id) AS max FROM problema3" );
      $linie = $rezultat->fetch_assoc();
      echo $linie['max'];
      echo '</rezultat>';
   }
   if(isset($_GET['marca'])){
       echo '<metoda>';
       echo 'size';
       echo '</metoda>';
       echo '<rezultat>';
       $marca = $_GET['marca'];
       $model = $_GET['model'];
       $connection =  mysqli_connect('localhost','root','','ajax_db_web');

       $max = $connection->query("SELECT MAX(id) AS max FROM problema3");
       $linie = $max->fetch_assoc();
       $id = $linie['max']+1;
       $insert = $connection->query("INSERT INTO problema3 (id, marca, model) VALUES (".$id.",\"$marca\",\"$model\");");
       echo $id;
       echo '</rezultat>';
   }

   echo '</response>';
?>
