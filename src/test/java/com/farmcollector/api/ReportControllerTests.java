package com.farmcollector.api;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.farmcollector.api.controllers.ReportController;
import com.farmcollector.api.dto.CropReport;
import com.farmcollector.api.dto.FarmReport;
import com.farmcollector.api.repositories.CropRepository;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerTests {
    
	@Autowired private MockMvc mockMvc;

    @MockBean private CropRepository repo;

    @Test
    void verifyFarmReportEndpoint() {
        try {
            var season = "winter";
            var response = new ArrayList<FarmReport>();
            when(repo.getFarmSummaryReport(season)).thenReturn(response);
            mockMvc
                .perform(get("/api/reports/"+season+"/farm"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            fail("Exception occured!");
        }
    }

    @Test
    void verifyCropReportEndpoint() {
        try {
            var season = "winter";
            var response = new ArrayList<CropReport>();
            when(repo.getCropSummaryReport(season)).thenReturn(response);
            mockMvc
                .perform(get("/api/reports/"+season+"/crop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            fail("Exception occured!");
        }
    }
}
