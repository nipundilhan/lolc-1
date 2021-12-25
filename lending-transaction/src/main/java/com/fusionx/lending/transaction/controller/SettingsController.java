package com.fusionx.lending.transaction.controller;

import com.fusionx.lending.transaction.core.FXDefaultException;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rk
 */
@RestController
@RequestMapping(value = "/settings")
@CrossOrigin(origins = "*")
@Timed
public class SettingsController {

    private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "/display")
    public ResponseEntity<String> displaySettings() throws Exception {
        System.out.println("inside SETTINGS controller...");
        try {
            return new ResponseEntity<String>("Inside the Settings", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("{}", e);
            // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            FXDefaultException defaultException = new FXDefaultException("1001",
                    "Error in getAllCountries:" + e.getMessage(), new java.util.Date());
            defaultException.setSeverity(false);
            throw defaultException;
        } finally {
        }
    }

}
