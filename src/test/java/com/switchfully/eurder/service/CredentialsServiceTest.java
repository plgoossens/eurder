package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Feature;
import com.switchfully.eurder.domain.repositories.CredentialsRepository;
import com.switchfully.eurder.exceptions.exceptions.UnauthorizedAccessException;
import com.switchfully.eurder.exceptions.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

class CredentialsServiceTest {

    private CredentialsRepository credentialsRepository;
    private CredentialsService credentialsService;

    @BeforeEach
    void setup(){
        credentialsRepository = Mockito.mock(CredentialsRepository.class);
        credentialsService = new CredentialsService(credentialsRepository);
    }

    @Test
    void validateUsernameDoesntExist_whenUsernameAlreadyExists_thenThrowsUserAlreadyExistsException() {
        // Given
        final String username = "username";
        Mockito.when(credentialsRepository.getByUsername(username)).thenReturn(Optional.of(getDummyCredentials()));

        //When
        assertThatThrownBy(() -> credentialsService.validateUsernameDoesntExist(username))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User username already exists.");
    }

    @Test
    void validateUsernameDoesntExist_whenUsernameDoesntExists() {
        // Given
        final String username = "username";
        Mockito.when(credentialsRepository.getByUsername(username)).thenReturn(Optional.empty());

        //When
        assertThatCode(() -> credentialsService.validateUsernameDoesntExist(username))
                .doesNotThrowAnyException();
    }

    @Test
    void validateAuthorization_whenPasswordDoesntMatch() {
        // Given
        final String authorization = "Basic Y3VzdG9tZXI6cGFzcw=="; // customer:pass
        Mockito.when(credentialsRepository.getByUsername("customer")).thenReturn(Optional.of(getDummyCredentials()));

        // When
        assertThatThrownBy(() -> credentialsService.validateAuthorization(authorization, Feature.ORDER_ITEMS))
                .isInstanceOf(UnauthorizedAccessException.class)
                .hasMessage("Access not authorized. Password does not match.");

    }

    @Test
    void validateAuthorization_whenUserDoesntHavePermission() {
        // Given
        final String authorization = "Basic Y3VzdG9tZXI6cGFzc3dvcmQ="; // customer:password
        Mockito.when(credentialsRepository.getByUsername("customer")).thenReturn(Optional.of(getDummyCredentials()));

        // When
        assertThatThrownBy(() -> credentialsService.validateAuthorization(authorization, Feature.VIEW_ALL_CUSTOMERS))
                .isInstanceOf(UnauthorizedAccessException.class)
                .hasMessage("Access not authorized. User customer does not have permission to VIEW_ALL_CUSTOMERS");

    }
}