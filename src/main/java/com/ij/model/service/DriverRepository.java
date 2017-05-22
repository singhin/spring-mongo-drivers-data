package com.ij.model.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.ij.model.Driver;

/**
 * 
 * @author Indy
 *
 */
@Repository
public interface DriverRepository extends MongoRepository<Driver, String> {

    Driver findById(String id);
    Driver findByUserId(String userId);
    public Driver findByUsername(String username);

}
