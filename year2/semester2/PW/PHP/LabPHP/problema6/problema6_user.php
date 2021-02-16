<!DOCTYPE html>
<html>

<head>

</head>

<body>

    <?php
    $servername = "localhost";
    $username = "username";
    $password = "password";
    $dbname = "myDB";
    
    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    
    $sql = "SELECT id, firstname, lastname FROM MyGuests";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
        // output data of each row
        while($row = $result->fetch_assoc()) {
            echo "<br> id: ". $row["id"]. " - Name: ". $row["firstname"]. " " . $row["lastname"] . "<br>";
        }
    } else {
        echo "0 results";
    }
    
    $conn->close();
    ?>


    <!--<form method="post" action="http://localhost/php/Lab_PHP/problema6/login.php">
        Username: <input type="text" name="username"><br> Parola: <input type="password" name="parola" /><br>
        <input type="submit" name="submit" value="Log In">
    </form>-->

</body>

</html>