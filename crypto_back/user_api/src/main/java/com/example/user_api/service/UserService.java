package com.example.user_api.service;

import com.example.user_api.entity.User;
import com.example.user_api.repository.UserRepository;
import io.r2dbc.spi.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j

public class UserService implements UserDetailsService {


    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ConnectionFactory connectionFactory;
    private DatabaseClient databaseClient;
    @Autowired
    public UserService(PasswordEncoder passwordEncoder, ConnectionFactory connectionFactory) {
        this.passwordEncoder = passwordEncoder;
        this.connectionFactory = connectionFactory;
        createTable();
    }


    private void createTable() {
        databaseClient = DatabaseClient.create(connectionFactory);
        Mono<Void> monoCreateTable = databaseClient.sql("CREATE TABLE IF NOT EXISTS " +
                        "trader(" +
                        "id INT primary key AUTO_INCREMENT," +
                        "first_name varchar(50)," +
                        "last_name varchar(50)," +
                        "email varchar(150)," +
                        "user_name varchar(50)," +
                        "password varchar(12)" + // Suppression de la virgule ici
                        ")")

                .then().doOnSuccess((Void) ->  {
                    System.out.println("Table User created is OK");
                }).doOnError((Void) ->  {
                    System.out.println("Table User created is KO");
                });
        monoCreateTable.subscribe();
        System.out.println("table created");
    }
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }
    
    /*public Mono add(String firstName, String lastName, String email, String username, String password) {
        Map<String, Object> values = new HashMap<>();
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("email", email);
        values.put("username", username);
        values.put("password", password);
        Mono result = databaseClient.sql("INSERT INTO trader (firstName,lastName,email, username, password) values (:firstName, :lastName, :email, :username, :password)")
                //.bind("firstname", firstname).bind("lastname", lastname)
                .bindValues(values)
                .then();
        return result;
    }*/
    public Flux<User> getAllUsers() {
        return databaseClient.sql("SELECT * from trader").fetch()
                .all()
                .map(m -> User.builder()
                        .id(Integer.valueOf(m.get("id").toString()))
                        .firstName(m.get("firstname").toString())
                        .lastName(m.get("lastname").toString())
                        .email(m.get("email").toString())
                        .username(m.get("username").toString())
                        .password(m.get("password").toString())
                        .build());
    }
    public Mono<User> getUserByUsername(String username){
        return  userRepository.findByUsername(username);
    }

    public Mono<User> getUserByEmail(String email){
        return  userRepository.findByEmail(email);
    }
    public Mono<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    // Here the db lookup uses parallel processing
    /*public Flux<User> fetchUsers(List<Integer> userIds) {
        return Flux.fromIterable(userIds)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(i -> findById(i))
                .ordered((u1, u2) -> u2.getId() - u1.getId());
    }*/




    public UserDetails authenticate(String username, String password) {
        // Récupérer le trader par nom d'utilisateur
        User user = userRepository.findByUsername(username).block();

        if (user != null && user.getPassword().equals(password)) {
            // Les informations d'identification sont valides, créer et retourner un UserDetails
            return loadUserByUsername(username);
        } else {
            // Les informations d'identification sont incorrectes, renvoyer null
            return null;
        }
    }
    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> {
                    if (user == null) {
                        throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
                    }
                    return new org.springframework.security.core.userdetails.User(
                            username,
                            "{noop}" + user.getPassword(),
                            true,
                            true,
                            true,
                            true,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                }).block(); // Bloquez le flux pour obtenir directement le UserDetails
    }



}
