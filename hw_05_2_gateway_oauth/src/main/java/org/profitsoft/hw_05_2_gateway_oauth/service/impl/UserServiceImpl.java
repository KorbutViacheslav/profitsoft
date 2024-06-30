package org.profitsoft.hw_05_2_gateway_oauth.service.impl;

import org.profitsoft.hw_05_2_gateway_oauth.entity.UserEntity;
import org.profitsoft.hw_05_2_gateway_oauth.repository.UserEntityRepository;
import org.profitsoft.hw_05_2_gateway_oauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userEntityRepository.save(user);
    }
}
