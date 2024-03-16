package com.example.user_api.service;

import com.example.user_api.dto.UserDTO;
import com.example.user_api.dto.WalletDTO;
import com.example.user_api.entity.User;
import com.example.user_api.repository.UserRepository;
import com.example.user_api.utils.PasswordHashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;


@Service
//@Slf4j
//public class UserService implements UserDetailsService {
public class UserService {

//    private final PasswordEncoder passwordEncoder;

//    @Autowired
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConnectionFactory connectionFactory) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }


    private final UserRepository userRepository;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public UserService(UserRepository userRepository, WebClient.Builder webClientBuilder) {
        this.userRepository = userRepository;
        this.webClientBuilder = webClientBuilder;
    }


    //CRD

    public Mono<User> createUser(UserDTO newUserDTO) {
        return userRepository.findByEmail(newUserDTO.getEmail())
                .flatMap(existingUser -> Mono.<User>error(new Exception("Email already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = User.builder()
                            .username(newUserDTO.getUsername())
                            .email(newUserDTO.getEmail())
                            .firstName(newUserDTO.getFirstName())
                            .lastName(newUserDTO.getLastName())
                            .build();

                    byte[] salt = PasswordHashUtils.getSalt();
                    byte[] hashedPassword = PasswordHashUtils.hashPassword(newUserDTO.getClearPassword().toCharArray(), salt, 10_000, 256);
                    newUser.setSalt(salt);
                    newUser.setHashedPassword(hashedPassword);

                    return userRepository.save(newUser)
                            .flatMap(user -> {
                                WalletDTO walletDTO = WalletDTO.builder()
                                        .userId(user.getId())
                                        .build();

                                return webClientBuilder.build().post()
                                        .uri("http://localhost:8084/wallet/add")
                                        .bodyValue(walletDTO)
                                        .retrieve()
                                        .bodyToMono(Void.class)
                                        .thenReturn(user);
                            });
                }));
    }


    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String userId) {
        return userRepository.findById(userId);
    }

//    public Mono<User> getUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }

//    public Mono<User> getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    public Mono<Void> deleteAnUser(String userId) {
        return userRepository.deleteById(userId);
    }

    // SPECIFIC METHOD


    public Mono<User> getUserWithEmailAndPassword(UserDTO userDTO) {
        return userRepository.findByEmail(userDTO.getEmail())
                .flatMap(user -> {
                    byte[] correctHashedPassword = PasswordHashUtils.hashPassword(userDTO.getClearPassword().toCharArray(), user.getSalt(), 10_000, 256);
                    if (Arrays.equals(correctHashedPassword, user.getHashedPassword())) {
                        return Mono.just(user);
                    } else {
                        return Mono.empty();
                    }
                });
    }


    public Mono<User> verifyIfAnEmailExist(String email) {
        return userRepository.findUserByEmail(email);
    }


    // Here the db lookup uses parallel processing
//    public Flux<User> fetchUsers(List<String> userIds) {
//        return Flux.fromIterable(userIds)
//                .parallel()
//                .runOn(Schedulers.boundedElastic())
//                .flatMap(i -> findById(Long.valueOf(i)))
//                .ordered((u1, u2) -> Math.toIntExact(u2.getId() - u1.getId()));
//    }


//    public UserDetails authenticate(String username, String password) {
//        // Récupérer le trader par nom d'utilisateur
//        User user = userRepository.findByUsername(username).block();
//
//        if (user != null && user.getPassword().equals(password)) {
//            // Les informations d'identification sont valides, créer et retourner un UserDetails
//            return loadUserByUsername(username);
//        } else {
//            // Les informations d'identification sont incorrectes, renvoyer null
//            return null;
//        }
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .map(user -> {
//                    if (user == null) {
//                        throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
//                    }
//                    return new org.springframework.security.core.userdetails.User(
//                            username,
//                            "{noop}" + user.getPassword(),
//                            true,
//                            true,
//                            true,
//                            true,
//                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//                    );
//                }).block(); // Bloquez le flux pour obtenir directement le UserDetails
//    }


}
