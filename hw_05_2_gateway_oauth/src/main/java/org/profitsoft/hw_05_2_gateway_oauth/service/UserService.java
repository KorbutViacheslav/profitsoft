package org.profitsoft.hw_05_2_gateway_oauth.service;

import org.profitsoft.hw_05_2_gateway_oauth.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);
}
