package com.example.user_api.service;

import com.example.user_api.entity.Trader;
import com.example.user_api.repository.TraderRepository;
import io.r2dbc.spi.ConnectionFactory;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class TraderService implements UserDetailsService {


    private TraderRepository traderRepository;

    private final PasswordEncoder passwordEncoder;

    private final ConnectionFactory connectionFactory;
    private DatabaseClient databaseClient;
    @Autowired
    public TraderService(PasswordEncoder passwordEncoder, ConnectionFactory connectionFactory) {
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
                    System.out.println("Création de la table OK");
                }).doOnError((Void) ->  {
                    System.out.println("Création de la table Not OK");
                });
        monoCreateTable.subscribe();
        System.out.println("table created");
    }
    public Mono add(String firstName, String lastName, String email, String username, String password) {
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
    }
    public Flux<Trader> getAll() {
        return databaseClient.sql("SELECT * from trader").fetch()
                .all()
                .map(m -> Trader.builder()
                        .id(Integer.valueOf(m.get("id").toString()))
                        .firstName(m.get("firstname").toString())
                        .lastName(m.get("lastname").toString())
                        .email(m.get("email").toString())
                        .username(m.get("username").toString())
                        .password(m.get("password").toString())
                        .build());
    }

    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return traderRepository.findByUsername(username)
                    .map(trader -> {
                        if (trader == null) {
                            throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
                        }
                        return new org.springframework.security.core.userdetails.User(
                                username,
                                "{noop}" + trader.getPassword(),
                                true,
                                true,
                                true,
                                true,
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                        );
                    }).block(); // Bloquez le flux pour obtenir directement le UserDetails
        }


    public Mono<Trader> add(Trader trader) {
        return databaseClient
                .sql("INSERT INTO trader (first_name, last_name, email, user_name, password) VALUES (:firstName, :lastName, :email, :userName, :password)")
                .bind("firstName", trader.getFirstName())
                .bind("lastName", trader.getLastName())
                .bind("email", trader.getEmail())
                .bind("userName", trader.getUsername())
                .bind("password", trader.getPassword())
                .fetch()
                .rowsUpdated()
                .thenReturn(trader)
                .doOnSuccess(savedTrader -> System.out.println("Trader ajouté avec succès: " + savedTrader))
                .doOnError(error -> System.out.println("Erreur lors de l'ajout du trader: " + error.getMessage()));
    }

    public UserDetails authenticate(String username, String password) {
        // Récupérer le trader par nom d'utilisateur
        Trader trader = traderRepository.findByUsername(username).block();

        if (trader != null && trader.getPassword().equals(password)) {
            // Les informations d'identification sont valides, créer et retourner un UserDetails
            return loadUserByUsername(username);
        } else {
            // Les informations d'identification sont incorrectes, renvoyer null
            return null;
        }
    }



}
