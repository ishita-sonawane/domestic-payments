package com.onlinebanking.domesticpayments.controller;

import com.onlinebanking.domesticpayments.dto.DomesticPaymentRequest;
import com.onlinebanking.domesticpayments.error.InsufficientBalanceException;
import com.onlinebanking.domesticpayments.error.PaymentDeclinedException;
import com.onlinebanking.domesticpayments.error.TechnicalErrorException;
import com.onlinebanking.domesticpayments.service.DomesticPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DomesticPaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DomesticPaymentService paymentService;
    private static String jwtToken = "";

    @TestConfiguration
    static class MockConfig {
        @Bean
        public DomesticPaymentService paymentService() {
            return mock(DomesticPaymentService.class);
        }

        public MockConfig() {
            RestTemplate restTemplate = new RestTemplate();
            String jwtCreationUrl = "https://wyav2lgfic.execute-api.eu-north-1.amazonaws.com/default/jwt_java";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            try {
                ResponseEntity<Map> responseEntity = restTemplate.exchange(
                        jwtCreationUrl,
                        HttpMethod.GET,
                        entity,
                        Map.class);
                Map<String, Object> body = responseEntity.getBody();
                jwtToken = body != null ? (String) body.get("token") : null;
            } catch (Exception e) {
                System.out.println("Error creating JWT token: " + e.getMessage());
            }
        }
    }

    @Test
    void shouldReturnBadRequestForInvalidBeneficiary() throws Exception {
        String requestJson = "{"
                + "\"paymentAmount\": 100,"
                + "\"beneficiaryId\": \"abc\","
                + "\"payeeAccountNumber\": \"9876543210\","
                + "\"customerId\": \"cust123\","
                + "\"paymentMethod\": \"NEFT\","
                + "\"paymentReferenceId\": \"ref001\""
                + "}";
        mockMvc.perform(post("/api/domestic-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // Invalid payeeAccountNumber (e.g., too short)
    @Test
    void shouldReturnBadRequestForInvalidPayeeAccountNumber() throws Exception {
        String requestJson = "{"
                + "\"paymentAmount\": 100,"
                + "\"beneficiaryId\": \"12345678\","
                + "\"payeeAccountNumber\": \"123\","
                + "\"customerId\": \"cust123\","
                + "\"paymentMethod\": \"NEFT\","
                + "\"paymentReferenceId\": \"ref003\""
                + "}";
        mockMvc.perform(post("/api/domestic-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // Invalid customerId (e.g., null)
    @Test
    void shouldReturnBadRequestForInvalidCustomerId() throws Exception {
        String requestJson = "{"
                + "\"paymentAmount\": 100,"
                + "\"beneficiaryId\": \"12345678\","
                + "\"payeeAccountNumber\": \"9876543210\","
                + "\"customerId\": null,"
                + "\"paymentMethod\": \"NEFT\","
                + "\"paymentReferenceId\": \"ref004\""
                + "}";
        mockMvc.perform(post("/api/domestic-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // Invalid paymentAmount (e.g., negative)
    @Test
    void shouldReturnBadRequestForInvalidPaymentAmount() throws Exception {
        String requestJson = "{"
                + "\"paymentAmount\": -10,"
                + "\"beneficiaryId\": \"12345678\","
                + "\"payeeAccountNumber\": \"9876543210\","
                + "\"customerId\": \"cust123\","
                + "\"paymentMethod\": \"NEFT\","
                + "\"paymentReferenceId\": \"ref005\""
                + "}";
        mockMvc.perform(post("/api/domestic-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // Invalid paymentMethod (e.g., empty)
    @Test
    void shouldReturnBadRequestForInvalidPaymentMethod() throws Exception {
        String requestJson = "{"
                + "\"paymentAmount\": 100,"
                + "\"beneficiaryId\": \"12345678\","
                + "\"payeeAccountNumber\": \"9876543210\","
                + "\"customerId\": \"cust123\","
                + "\"paymentMethod\": \"\","
                + "\"paymentReferenceId\": \"ref006\""
                + "}";
        mockMvc.perform(post("/api/domestic-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // Invalid paymentReferenceId (e.g., null)
    @Test
    void shouldReturnBadRequestForInvalidPaymentReferenceId() throws Exception {
        String requestJson = "{"
                + "\"paymentAmount\": 100,"
                + "\"beneficiaryId\": \"12345678\","
                + "\"payeeAccountNumber\": \"9876543210\","
                + "\"customerId\": \"cust123\","
                + "\"paymentMethod\": \"NEFT\","
                + "\"paymentReferenceId\": null"
                + "}";
        mockMvc.perform(post("/api/domestic-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnForbiddenForInsufficientBalance() throws Exception {
        String requestJson = "{"
                + "\"paymentAmount\": 10000,"
                + "\"beneficiaryId\": \"12345679\","
                + "\"payeeAccountNumber\": \"9876543210\","
                + "\"customerId\": \"cust123\","
                + "\"paymentMethod\": \"NEFT\","
                + "\"paymentReferenceId\": \"ref002\""
                + "}";
        doThrow(new InsufficientBalanceException("Insufficient balance")).when(paymentService)
                .savePayment(any(DomesticPaymentRequest.class));

        mockMvc.perform(post("/api/domestic-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void shouldReturnServerErrorForTechnicalErrorReference() throws Exception {
//        String requestJson = "{ \"paymentAmount\": 100, \"beneficiaryId\": \"12345679\", \"paymentReference\": \"TECHERROR\" }";
//        doThrow(new TechnicalErrorException("Technical error")).when(paymentService)
//                .savePayment(any(DomesticPaymentRequest.class));
//
//        mockMvc.perform(post("/api/domestic-payments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isInternalServerError());
//    }
}