package fr.sae502.uniplanify.login;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@Component
@EnableWebSecurity
public class Security {

        @Autowired
        private DataSource dataSource;

        @Bean
        public SecurityFilterChain mesautorisations(HttpSecurity http, HandlerMappingIntrospector introspector)
                        throws Exception {
                MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
                System.out.println("HEYY");
                return http.authorizeHttpRequests((authorize) -> authorize
                                // RESTRICION DES ACCES TOUJOURS FINIR PAR ANYREQUEST POUR LE RESTE
                                .requestMatchers(mvc.pattern("/pro/**")).hasRole("ADMIN")
                                .requestMatchers(mvc.pattern("/perso/**")).hasRole("USER")
                                .requestMatchers(mvc.pattern("/rdv/**")).authenticated()                                
                                .anyRequest().permitAll())

                                // ACTIVE LE FORMULAIRE DE CONNEXION /LOGIN + ENDPOINT DECONNEXION /LOGOUT
                                .formLogin(Customizer.withDefaults())

                                // REDIRECTION SUR PAGE ACCUEIL APRES DECONNEXION
                                .logout(configurer -> configurer.logoutSuccessUrl("/"))

                                // ACTIVE LA CASE REMEMBER ME
                                .rememberMe(configurer -> configurer.rememberMeParameter("remember")
                                                .useSecureCookie(true))
                                .build();
        }

    @Bean
    public UserDetailsService mesutilisateurs() {
        // System.out.println("ON VA CHARGER LES USERS");
        // UserDetails user1 = User.withUsername("a")
        //         .password(encoder().encode("a"))
        //         .roles("ADMIN")
        //         .build();

        // UserDetails user2 = User.withUsername("u")
        //         .password(encoder().encode("u"))
        //         .roles("USER")
        //         .build();
        // return new InMemoryUserDetailsManager(user1, user2);

        // JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        // users.deleteUser("a");
        // users.deleteUser("u");
        // users.createUser(user1);
        // users.createUser(user2);


        //UserDetails user1 = loadUserByUsername("thomas");


        String usersByUsernameQuery = "select email, password, enabled from utilisateur where email = ?";
        String authsByUserQuery = "select email, authority from utilisateur where email = ?";
        System.out.println("REQUETE : ");
        System.out.println(usersByUsernameQuery);
        System.out.println(authsByUserQuery);
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        
        users.setUsersByUsernameQuery(usersByUsernameQuery);
        users.setAuthoritiesByUsernameQuery(authsByUserQuery);

        return users;
    }

        @Bean
        public PasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
        }
}
