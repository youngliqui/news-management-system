package ru.clevertec.security.core.service.information;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.common.exception.not_found.UserNotFoundException;
import ru.clevertec.security.core.entity.User;
import ru.clevertec.security.core.mapper.UserMapper;
import ru.clevertec.security.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserInformationServiceImpl implements UserInformationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public Page<UserInfoDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserInfoDTO);
    }

    @Override
    public UserInfoDTO getUserInfoById(Long userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id = " + userId + " was not found"));

        return userMapper.userToUserInfoDTO(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User with nickname = " + username + " was not found"));
    }
}
