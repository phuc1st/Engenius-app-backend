package com.phuc.notification.repository;

import com.phuc.notification.entity.DeviceToken;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTokenRepository extends MongoRepository<DeviceToken, String> {
    Optional<DeviceToken> findByUserId(String userId);
}