package server.cps.verfication.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ApiService <T>{

    ResponseEntity<T> get (String url , HttpHeaders httpHeaders);
    ResponseEntity<T> get(String url, HttpHeaders httpHeaders, Class<T> clazz);

    ResponseEntity<T> post (String url , HttpHeaders httpHeaders, Object body);
    ResponseEntity<T> post (String url , HttpHeaders httpHeaders, Object body,Class<T> clazz);





}
