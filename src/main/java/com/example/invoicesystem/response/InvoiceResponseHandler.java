package com.example.invoicesystem.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class InvoiceResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("Invoice", responseObj);
        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateResponseNot(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Message", message);
        map.put("Status", status.value());

        return new ResponseEntity<Object>(map, status);
    }
}
