package com.javarush.jira.profile.internal.web;


import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.error.ErrorMessageHandler;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Contact;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/profile";

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private ProfileMapper profileMapper;
    @MockBean
    private ErrorMessageHandler errorMessageHandler;

    private final long TEST_USER_ID = 1L;
    private final String TEST_USER_EMAIL = "user@example.com";

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
    }

    private void setupAuthentication() {
        User user = new User(
                TEST_USER_ID,
                TEST_USER_EMAIL,
                "password",
                "User",
                "User",
                "User User",
                Role.DEV
        );

        AuthUser authUser = new AuthUser(user);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities())
        );
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getPositivePath() throws Exception {
        setupAuthentication();
        Profile mockProfile = new Profile(TEST_USER_ID);
        mockProfile.setMailNotifications(3L);
        Contact phoneContact = new Contact(TEST_USER_ID, "phone", "+1234567890");
        Contact emailContact = new Contact(TEST_USER_ID, "email", "test@mail.com");
        mockProfile.setContacts(Set.of(phoneContact, emailContact));

        ProfileTo mockProfileTo = new ProfileTo(
                TEST_USER_ID,
                Set.of("NEWS", "UPDATES"),
                Set.of(
                        new ContactTo("phone", "+1234567890"),
                        new ContactTo("email", "test@mail.com")
                )
        );

        when(profileRepository.getOrCreate(TEST_USER_ID)).thenReturn(mockProfile);
        when(profileMapper.toTo(mockProfile)).thenReturn(mockProfileTo);

        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_USER_ID))
                .andExpect(jsonPath("$.mailNotifications", containsInAnyOrder("NEWS", "UPDATES")))
                .andExpect(jsonPath("$.contacts[*].code", hasItems("phone","email")))
                .andExpect(jsonPath("$.contacts[?(@.code=='phone')].value").value(hasItems("+1234567890")))
                .andExpect(jsonPath("$.contacts[?(@.code=='email')].value").value(hasItems("test@mail.com")));

        verify(profileRepository).getOrCreate(TEST_USER_ID);
        verify(profileMapper).toTo(mockProfile);
    }

    @Test
    void updateWithInvalidContact() throws Exception {
        setupAuthentication();
        String invalidJson = """
                {
                    "id": 1,
                    "mailNotifications": ["NEWS"],
                    "contacts": [
                        {"code": "invalid_code", "value": "test"}
                    ]
                }
                """;

        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateWithWrongId() throws Exception {
        setupAuthentication();

        String invalidJson = """
                {
                    "id": 2,
                    "mailNotifications": ["NEWS"],
                    "contacts": []
                }
                """;

        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getWithoutAuthentication() throws Exception {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());

        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateWithInvalidInfo() throws Exception {
        setupAuthentication();

        String invalidJson = """
                {
                    "id": 1,
                    "mailNotifications": null,
                    "contacts": [
                        {"code": "", "value": "+1234567890"}
                    ]
                }
                """;

        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isUnprocessableEntity());
    }

}
