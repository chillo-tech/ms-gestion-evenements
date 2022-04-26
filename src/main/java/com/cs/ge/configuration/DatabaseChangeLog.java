
package com.cs.ge.configuration;
import com.cs.ge.entites.Utilisateur;
import com.cs.ge.enums.Role;
import com.cs.ge.repositories.UtilisateurRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


 @ChangeLog
public class  DatabaseChangeLog {
    @ChangeSet(order="001", id= "cs-1", author="mongock")
     public void changeSet1(UtilisateurRepository utilisateurRepository){
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        Utilisateur athena = new Utilisateur();
        athena.setRole(Role.admin);
        athena.setFirstName("mario");
        athena.setLastName("kwe");
        athena.setUsername("mai@yahoo.com");
        String encodedPassword = passwordEncoder.encode(athena.getPassword());
        athena.setPassword(encodedPassword);
        utilisateurRepository.insert(athena);

    // @ChangeSet(id= "cs-1", order = "001", author = "mongock")
     //public void changeSet1(MongoTemplate mongoTemplate) {
       //  Utilisateur admin = new Utilisateur("admin", "admin");
     //    admin.getRole().add("ADMIN");
     //    mongoTemplate.save(admin, "user");

    }

   // private Utilisateur createNewUtilisateur ( Role role,String firstName,String lastName,String username,String password){
   //     BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
   //     Utilisateur utilisateur = new Utilisateur();
   //     utilisateur.setUsername(username);
    //    utilisateur.setFirstName(firstName);
  //      utilisateur.setLastName(lastName);
   //     String encodedPassword = passwordEncoder.encode(utilisateur.getPassword());
    //    utilisateur.setPassword(encodedPassword);
//        utilisateur.setRole(role);
 //       return utilisateur;
    //    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
