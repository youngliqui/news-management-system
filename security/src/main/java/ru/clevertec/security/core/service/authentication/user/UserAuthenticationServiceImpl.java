package ru.clevertec.security.core.service.authentication.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.security.common.dto.user.ChangePasswordDTO;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.common.exception.not_found.UserNotFoundException;
import ru.clevertec.security.common.exception.password.IncorrectPasswordException;
import ru.clevertec.security.common.exception.password.PasswordMismatchException;
import ru.clevertec.security.core.entity.User;
import ru.clevertec.security.core.mapper.UserMapper;
import ru.clevertec.security.core.repository.UserRepository;
import ru.clevertec.security.core.service.information.UserInformationService;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserInformationService userInformationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetailsService userDetailsService() {
        return userInformationService::getByUsername;
    }

    @Override
    public UserInfoDTO changePassword(Long userId, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id = " + userId + " was not found"));

        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Current password is incorrect");
        }

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("New password and confirmation do not match");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        User savedUser = userRepository.save(user);

        return userMapper.userToUserInfoDTO(savedUser);
    }
}
