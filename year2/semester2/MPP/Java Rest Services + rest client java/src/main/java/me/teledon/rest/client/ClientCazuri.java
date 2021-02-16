package me.teledon.rest.client;

import me.teledon.model.CazCaritabil;
import me.teledon.repository.RepositoryExeption;
import me.teledon.repository.ServiceExeption;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Callable;

public class ClientCazuri {
    public static final String URL = "http://localhost:8080/teledon/cazuri";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new ServiceExeption(e);
        }
    }

    public CazCaritabil[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, CazCaritabil[].class));
    }

    public CazCaritabil getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), CazCaritabil.class));
    }

    public CazCaritabil create(CazCaritabil caz) {
        return execute(() -> restTemplate.postForObject(URL, caz, CazCaritabil.class));
    }

    public void update(CazCaritabil caz) {
        execute(() -> {
            restTemplate.put(URL, caz);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}

