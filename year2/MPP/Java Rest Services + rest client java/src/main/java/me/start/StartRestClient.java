package me.start;

import me.teledon.model.CazCaritabil;
import me.teledon.repository.ServiceExeption;
import me.teledon.rest.client.ClientCazuri;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class StartRestClient {
    private final static ClientCazuri clientCazuri=new ClientCazuri();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        CazCaritabil cazT=new CazCaritabil("Caz Test ");
        try{
            System.out.println("create: ");
            show(()-> System.out.println(clientCazuri.create(cazT)));

            show(()-> System.out.println("get by id: " + clientCazuri.getById("2").getDescription()));
            System.out.println("get all: ");
            show(()->{
                CazCaritabil[] res=clientCazuri.getAll();
                for(CazCaritabil c:res){
                    System.out.println(c.getId()+": "+c.getDescription());
                }
            });
            System.out.println("delete:");
            show(() -> clientCazuri.delete("25"));
            show(()->{
                CazCaritabil[] res=clientCazuri.getAll();
                for(CazCaritabil c:res){
                    System.out.println(c.getId()+": "+c.getDescription());
                }
            });
            System.out.println("update");
            clientCazuri.update(new CazCaritabil("18", "update Lab22"));
            show(() -> System.out.println(clientCazuri.getById("5").getId() + " " + clientCazuri.getById("5").getDescription()));
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceExeption e) {
            System.out.println("Service exception"+ e);
        }
    }
}
