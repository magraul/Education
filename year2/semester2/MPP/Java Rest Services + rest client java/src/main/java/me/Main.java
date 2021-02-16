package me;

import me.teledon.repository.CazuriDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("hi");
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            serverProps.list(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    //    CazuriDBRepository cazuriDBRepository = new CazuriDBRepository(serverProps);

    }
}
