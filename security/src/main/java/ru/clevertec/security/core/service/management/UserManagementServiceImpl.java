package ru.clevertec.security.core.service.management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.common.dto.user.UserUpdateDTO;
import ru.clevertec.security.common.exception.already_exists.UsernameAlreadyExistsException;
import ru.clevertec.security.common.exception.not_found.UserNotFoundException;
import ru.clevertec.security.core.entity.User;
import ru.clevertec.security.core.mapper.UserMapper;
import ru.clevertec.security.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;

    @Override
    public UserInfoDTO save(User user) {
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserInfoDTO(savedUser);
    }

    @Override
    public UserInfoDTO create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username = " + user.getUsername() + " already exists");
        }

        return save(user);
    }

    @Override
    public UserInfoDTO updateUser(Long userId, UserUpdateDTO updateDTO) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id = " + userId + " was not found"));
        UserMapper.INSTANCE.updateUser(user, updateDTO);

        return save(user);
    }
}
