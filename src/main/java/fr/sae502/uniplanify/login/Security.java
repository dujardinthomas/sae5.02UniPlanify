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
                                // .requestMatchers("/h2-console/**").permitAll() // Add this line to permit access to H2 console
                                // .csrf().disable() // Désactiver CSRF pour la console H2
                                //  // Désactiver X-Frame-Options pour H2 (pour pouvoir l'afficher dans un iframe)
                                .anyRequest().permitAll())

                                // ACTIVE LE FORMULAIRE DE CONNEXION /LOGIN + ENDPOINT DECONNEXION /LOGOUT
                                 .formLogin(Customizer.withDefaults())

                                // .formLogin( (form) -> form
                                //         .loginPage("/login")
                                // //         .permitAll()
                                // //         // .loginProcessingUrl("/login")
                                // //         // .defaultSuccessUrl("/", true)
                                // //         // .failureUrl("/login?msg=erreur")
                                // )

                                // REDIRECTION SUR PAGE ACCUEIL APRES DECONNEXION
                                .logout(configurer -> configurer.logoutSuccessUrl("/"))

                                // ACTIVE LA CASE REMEMBER ME
                                .rememberMe(configurer -> configurer.rememberMeParameter("remember")
                                                .useSecureCookie(true))
                                .build();
        }

    @Bean
    public UserDetailsService mesutilisateurs() {
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
