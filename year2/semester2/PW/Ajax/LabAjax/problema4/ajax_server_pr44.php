<?php
header('Content-Type: text/xml');
echo '<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>';
echo '<response>';

$index_patrat = $_GET['index_patrat'];
$valoarea = $_GET['valoarea'];

if($index_patrat == "initial") {
        $connection =  mysqli_connect('localhost','root','','ajax_db_web');

        $connection->query("UPDATE mutari SET folosit=0, valoarea=-1");

} else if($index_patrat != "" and $valoarea != "") {
        $connection =  mysqli_connect('localhost','root','','ajax_db_web');
        $connection->query("UPDATE mutari SET folosit=1, valoarea=\"$valoarea\" WHERE index_patrat=\"$index_patrat\"");

        //verificam daca este un castigator

        $m1 = $connection->query("select valoarea from mutari where index_patrat=1")->fetch_assoc()["valoarea"];
        $m2 = $connection->query("select valoarea from mutari where index_patrat=2")->fetch_assoc()["valoarea"];
        $m3 = $connection->query("select valoarea from mutari where index_patrat=3")->fetch_assoc()["valoarea"];

        $m4 = $connection->query("select valoarea from mutari where index_patrat=4")->fetch_assoc()["valoarea"];
        $m5 = $connection->query("select valoarea from mutari where index_patrat=5")->fetch_assoc()["valoarea"];
        $m6 = $connection->query("select valoarea from mutari where index_patrat=6")->fetch_assoc()["valoarea"];

        $m7 = $connection->query("select valoarea from mutari where index_patrat=7")->fetch_assoc()["valoarea"];
        $m8 = $connection->query("select valoarea from mutari where index_patrat=8")->fetch_assoc()["valoarea"];
        $m9 = $connection->query("select valoarea from mutari where index_patrat=9")->fetch_assoc()["valoarea"];

        $win = 0;

        if(($m1 == $m4 and $m4 == $m7 and $m4 != -1) or ($m2 == $m5 and $m5 == $m8 and $m5 != -1) or ($m3 == $m6 and $m6 == $m9 and $m6 != -1))
                $win = 1;
        if(($m1 == $m2 and $m2 == $m3 and $m2 != -1) or ($m4 == $m5 and $m5 == 6 and $m5 != -1) or ($m7 == $m8 and $m8 == $m9 and $m8 != -1))
                $win = 1;
        if(($m1 == $m5 and $m5 == $m9 and $m5 != -1) or ($m3 == $m5 and $m5 == $m7 and $m5 != -1))
                $win = 1;


if($win == 1) {
                echo 'win';
        } else {

                $val_pt_pc = 0;
                if($valoarea == "0")
                        $val_pt_pc = 1;
                else $val_pt_pc = 0;

                //$connection->query("UPDATE mutari SET folosit=1, valoarea=\"$valoarea\" WHERE index_patrat=\"$index_patrat\"");

                //alegem unde pune pc ul miscarea
                $rezultat = $connection->query("SELECT index_patrat FROM mutari WHERE folosit=0 LIMIT 1");
                $v = -1;
                if($linie = $rezultat->fetch_assoc()) {
                        $v = $linie["index_patrat"];
                        $connection->query("UPDATE mutari SET folosit=1, valoarea=\"$val_pt_pc\" WHERE index_patrat=\"$v\"");
                }

                //verificam daca este un castigator

                $m1 = $connection->query("select valoarea from mutari where index_patrat=1")->fetch_assoc()["valoarea"];
                $m2 = $connection->query("select valoarea from mutari where index_patrat=2")->fetch_assoc()["valoarea"];
                $m3 = $connection->query("select valoarea from mutari where index_patrat=3")->fetch_assoc()["valoarea"];

                $m4 = $connection->query("select valoarea from mutari where index_patrat=4")->fetch_assoc()["valoarea"];
                $m5 = $connection->query("select valoarea from mutari where index_patrat=5")->fetch_assoc()["valoarea"];
                $m6 = $connection->query("select valoarea from mutari where index_patrat=6")->fetch_assoc()["valoarea"];

                $m7 = $connection->query("select valoarea from mutari where index_patrat=7")->fetch_assoc()["valoarea"];
                $m8 = $connection->query("select valoarea from mutari where index_patrat=8")->fetch_assoc()["valoarea"];
                $m9 = $connection->query("select valoarea from mutari where index_patrat=9")->fetch_assoc()["valoarea"];

                $win = 0;

                if(($m1 == $m4 and $m4 == $m7 and $m4 != -1) or ($m2 == $m5 and $m5 == $m8 and $m5 != -1) or ($m3 == $m6 and $m6 == $m9 and $m6 != -1))
                        $win = 1;
                if(($m1 == $m2 and $m2 == $m3 and $m2 != -1) or ($m4 == $m5 and $m5 == 6 and $m5 != -1) or ($m7 == $m8 and $m8 == $m9 and $m8 != -1))
                        $win = 1;
                if(($m1 == $m5 and $m5 == $m9 and $m5 != -1) or ($m3 == $m5 and $m5 == $m7 and $m5 != -1))
                        $win = 1;

                if($win == 1) {
                        echo 'winC;' . $v;
                } else echo $v;
        }
}
echo '</response>';
?>
