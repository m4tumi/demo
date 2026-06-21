package com.example.msa.model.service;

import com.example.msa.model.DTO.UserDTO;
import com.example.msa.model.DTO.WalletDTO;
import com.example.msa.model.entity.User;
import com.example.msa.model.entity.Wallet;
import com.example.msa.model.repository.UserRepository;
import com.example.msa.model.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TronGridService tronGridService;
    private final WalletService walletService;

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = new User(
                userDTO.username(),
                userDTO.email(),
                userDTO.kycStatus(),
                userDTO.county(),
                userDTO.tierLevel()
        );
        User saved = userRepository.save(user);
        log.info("Created new user node in Neo4j: id={}, username={}", saved.getId(), saved.getUsername());
        return saved;
    }

    @Transactional
    public User attachWalletToUser(String username, String walletAddress) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found!"));
        WalletDTO walletDTO = tronGridService.getWallet(walletAddress);
        if (walletDTO == null) {
            log.warn("Could not fetch wallet from TronGrid");
            return user;
        }

        Wallet wallet = walletService.saveOrUpdate(walletDTO);

        user.getWallets().add(wallet);
        User updated = userRepository.save(user);
        log.info("Attached wallet {} to user {}", walletAddress, username);

        walletService.syncTransactions(walletAddress);

        return updated;
    }
    @Transactional
    public User attachMultipleWalletsToUser(String username, List<String> walletAddresses) {
        User user = null;
        for (String address : walletAddresses) {
            user = attachWalletToUser(username, address);
        }
        return user;
    }


    @Transactional
    public List<User> bulkImport(List<UserWithWallets> usersWithWallets) {
        List<User> savedUsers = new ArrayList<>();

        for (UserWithWallets entry : usersWithWallets) {
            try {
                User user = createUser(entry.userDTO());

                for (String addr : entry.walletAddresses()) {
                    try {
                        WalletDTO walletDTO = tronGridService.getWallet(addr);
                        if (walletDTO != null) {
                            Wallet wallet = walletService.saveOrUpdate(walletDTO);
                            user.getWallets().add(wallet);
                        }
                    } catch (Exception e) {
                        log.warn("Skipping wallet a for this user!!");
                    }
                }

                savedUsers.add(userRepository.save(user));
                log.info("Bulk imported user: {}", user.getUsername());

            } catch (Exception e) {
                log.error("Failed to import user {}: {}", entry.userDTO().username(), e.getMessage());
            }
        }

        return savedUsers;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public record UserWithWallets(UserDTO userDTO, List<String> walletAddresses) {}
}







