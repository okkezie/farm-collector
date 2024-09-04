package com.farmcollector.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.farmcollector.api.controllers.CropController;
import com.farmcollector.api.dto.ApiResponse;
import com.farmcollector.api.dto.CropDataRequest;
import com.farmcollector.api.models.Crop;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CropControllerTests {
    
    @Autowired private CropController controller;

    @LocalServerPort private int port;

	@Autowired private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void testValidController() {
        assertThat(controller).isNotNull();
    }
    
    @Test
    void testSuccessfulCropRequestShouldReturnCropData() {
        var url = "http://localhost:" + port + "/api/crops";
        var request = new CropDataRequest();
        request.setFarmerName("Farmer One");
        request.setFarmName("Farm One");
        request.setSeason("Autumn");
        request.setCropType("Potatoes");
        request.setExpectedHarvestIntons(20);
        request.setAreaInAcres(10);
        request.setField("Field A");
        var response = this.restTemplate.postForEntity(url, request, ApiResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isInstanceOf(ApiResponse.class);
        assertThat(response.getBody().getResult()).isNotNull();
        assertThat(response.getBody().getResult()).isInstanceOf(Crop.class);
    }

    @Test
    void testFailedValidationCropRequestShouldReturnCropErrorList() {
        var url = "http://localhost:" + port + "/api/crops";
        var request = new CropDataRequest();
        request.setFarmerName("Farmer One");
        request.setFarmName("Farm One");
        var response = this.restTemplate.postForEntity(url, request, ApiResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isInstanceOf(ApiResponse.class);
        assertThat(response.getBody().getResult()).isNull();
        assertThat(response.getBody().getErrors()).isNotNull();
        assertThat(response.getBody().getErrors()).isInstanceOf(ArrayList.class);
        assertThat(response.getBody().getErrors().size()).isGreaterThan(1);
    }

    @Test
    void testSuccessfulCropUpdateShouldReturnCropData() {
        var url = "http://localhost:" + port + "/api/crops";
        var request = new CropDataRequest();
        request.setFarmerName("Farmer One");
        request.setFarmName("Farm One");
        request.setSeason("Autumn");
        request.setCropType("Potatoes");
        request.setExpectedHarvestIntons(20);
        request.setAreaInAcres(10);
        request.setField("Field A");
        var response = this.restTemplate.postForEntity(url, request, ApiResponse.class);
        var updateRequest = new CropDataRequest();
        updateRequest.setId(response.getBody().getResult().getId());
        updateRequest.setActualHarvestInTons(100);
        var update = this.restTemplate.patchForObject(url, updateRequest, ApiResponse.class);
        assertThat(update).isNotNull();
        assertThat(update.getResult()).isNotNull();
        assertThat(update.getResult()).isInstanceOf(Crop.class);
    }
    
    @Test
    void testFailedCropUpdateShouldReturnError() {
        var url = "http://localhost:" + port + "/api/crops";
        var updateRequest = new CropDataRequest();
        updateRequest.setId(100);
        updateRequest.setActualHarvestInTons(100);
        var update = this.restTemplate.patchForObject(url, updateRequest, ApiResponse.class);
        assertThat(update).isNotNull();
        assertThat(update.getResult()).isNull();
        assertThat(update.getErrors()).isNotNull();
        assertThat(update.getErrors()).isInstanceOf(ArrayList.class);
        assertThat(update.getErrors().size()).isGreaterThan(0);
    }
}
