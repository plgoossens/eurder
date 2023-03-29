package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Credentials;
import com.switchfully.eurder.domain.models.Role;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CredentialsRepository {

    private final Map<String, Credentials> credentialsDB;

    public CredentialsRepository(){
        credentialsDB = new HashMap<>();
        initializeDB();
    }

    public void initializeDB(){
        add(new Credentials("admin", "admin", Role.ADMIN, null));
    }

    public void add(Credentials credentials){
        credentialsDB.put(credentials.getUsername(), credentials);
    }

    public Optional<Credentials> getByUsername(String username){
        return Optional.ofNullable(credentialsDB.get(username));
    }
}
