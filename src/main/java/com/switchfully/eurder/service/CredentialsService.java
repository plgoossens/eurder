package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Credentials;
import com.switchfully.eurder.domain.models.Feature;
import com.switchfully.eurder.domain.models.Role;
import com.switchfully.eurder.domain.repositories.CredentialsRepository;
import com.switchfully.eurder.exceptions.exceptions.UnauthorizedAccessException;
import com.switchfully.eurder.exceptions.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class CredentialsService {

    private final CredentialsRepository credentialsRepository;

    public CredentialsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    public void addCustomer(String username, String password, UUID customerId){
        credentialsRepository.add(new Credentials(username, password, Role.CUSTOMER, customerId));
    }

    public UUID validateAuthorization(String authorization, Feature feature){
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);

        Credentials credentials = credentialsRepository.getByUsername(username)
                .orElseThrow(() -> new UnauthorizedAccessException("Access not authorized. User with username " + username + " doesn't exist"));

        if(!credentials.doesPasswordMatch(password)){
            throw new UnauthorizedAccessException("Access not authorized. Password does not match.");
        }

        if(!credentials.getRole().hasPermission(feature)){
            throw new UnauthorizedAccessException("Access not authorized. User " + username + " does not have permission to " + feature.name());
        }
        return credentials.getCustomerId();
    }

    public void validateUsernameDoesntExist(String username) {
        if(credentialsRepository.getByUsername(username).isPresent()){
            throw new UserAlreadyExistsException("User " + username + " already exists.");
        }
    }
}
