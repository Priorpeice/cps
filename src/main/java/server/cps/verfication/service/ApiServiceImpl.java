package server.cps.verfication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl<T> implements ApiService<T>{
    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<T> get(String url, HttpHeaders httpHeaders) {
        return callApiEndpoint(url, HttpMethod.GET, httpHeaders, null, (Class<T>)Object.class);
    }

    @Override
    public ResponseEntity<T>  get(String url, HttpHeaders httpHeaders, Class<T> clazz) {
        return callApiEndpoint(url, HttpMethod.GET, httpHeaders, null, clazz);
    }

    @Override
    public ResponseEntity<T>  post(String url, HttpHeaders httpHeaders, Object body) {
        return callApiEndpoint(url, HttpMethod.POST, httpHeaders, body,(Class<T>)Object.class);
    }

    @Override
    public ResponseEntity<T>  post(String url, HttpHeaders httpHeaders, Object body, Class<T>  clazz) {
        return callApiEndpoint(url, HttpMethod.POST, httpHeaders, body, clazz);
    }

    private ResponseEntity<T> callApiEndpoint (String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Object body, Class<T> clazz)
    {
        return restTemplate.exchange(url,httpMethod,new HttpEntity<>(body,httpHeaders),clazz);
    }
}
