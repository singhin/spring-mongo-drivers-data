package com.ij.model.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.ij.model.Driver;
import com.ij.model.Journey;
import com.ij.model.JourneyDetail;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private DriverRepository driverRepository;
    
    @Value("${csv.path}")
    private String csvPath;

    public DatabaseSeeder(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }


    @Override
    public void run(String... strings) throws Exception {

        //Create the CSVFormat object
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
       
        Resource resource = new ClassPathResource(csvPath);
        //initialize the CSVParser object
        CSVParser parser = new CSVParser(new InputStreamReader(resource.getInputStream()), format);

        List<Driver> drivers = new ArrayList<Driver>();
        Driver driver = null;
        Journey journey = null;
        String tempUserId = null;
        String tempJourneyId = null;
        for (CSVRecord record : parser) {

            String userId = record.get("userId");
            String journeyId = record.get("journeyId");

            if (!userId.equals(tempUserId)) {
                if(journey != null)
                {
                    ArrayList<Journey> journeys = driver.getJourneys();
                    journeys.add(journey);
                    driver.setJourneys(journeys);
                    tempJourneyId = null;
                    journey = null;
                }

                if(driver != null)
                drivers.add(driver);

                driver = new Driver();
                driver.setUserId(userId);
                driver.setUsername(userId);
                driver.setPassword(userId);
                driver.setRoles(new String[]{"USER"});
            }

            if (!journeyId.equals(tempJourneyId)) {
                if(journey != null)
                {
                    ArrayList<Journey> journeys = driver.getJourneys();
                    journeys.add(journey);
                    driver.setJourneys(journeys);
                }
                journey = new Journey();
                journey.setJourneyId(record.get("journeyId"));
            }

            JourneyDetail journeyDetail = new JourneyDetail();
            journeyDetail.setTime(new Date(Long.parseLong(record.get("time"))));
            journeyDetail.setLat(record.get("lat"));
            journeyDetail.setLon(record.get("lon"));
            journeyDetail.setSpeed(record.get("speed"));
            journeyDetail.setBearing(record.get("bearing"));

            ArrayList<JourneyDetail> journeyDetails = journey.getJourneyDetails();
            journeyDetails.add(journeyDetail);
            journey.setJourneyDetails(journeyDetails);

            tempUserId = userId;
            tempJourneyId = journeyId;
        }

        ArrayList<Journey> journeys = driver.getJourneys();
        journeys.add(journey);
        driver.setJourneys(journeys);
        
        drivers.add(driver);

        //close the parser
        parser.close();
        
        
        //Adding the admin
        Driver adminDriver = new Driver();
        adminDriver.setUserId("admin");
        adminDriver.setUsername("admin");
        adminDriver.setPassword("admin");
        adminDriver.setRoles(new String[]{"ADMIN"});
        drivers.add(adminDriver);


      this.driverRepository.deleteAll();

      this.driverRepository.save(drivers);
      
    }
}
