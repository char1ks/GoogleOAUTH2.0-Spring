package com.example.SpringRedis_pet2.Service;

import com.example.SpringRedis_pet2.Model.Visitor.Visitor;
import com.example.SpringRedis_pet2.Repository.VisitorRepository;
import com.example.SpringRedis_pet2.Security.VisitorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class VisitorService implements UserDetailsService {

    @Autowired
    private VisitorRepository operations;

    public void saveVisitor(Visitor visitor){
        operations.save(visitor);
    }

    public Visitor googleAuth() {
        System.out.println(123);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            System.out.println(321);
            OAuth2User oAuth2User = oauthToken.getPrincipal();
            System.out.println(123);
            if (oAuth2User != null) {
                Map<String, Object> attributes = oAuth2User.getAttributes();
                System.out.println("here4");

                String givenName = (String) attributes.get("given_name");
                String familyName = (String) attributes.get("family_name");
                String username = "";
                if (givenName != null) {
                    username += givenName; // Добавляем имя, если оно не null
                }
                if (familyName != null) {
                    if (!username.isEmpty()) {
                        username += " "; // Добавляем пробел, если имя уже добавлено
                    }
                    username += familyName; // Добавляем фамилию, если она не null
                }
                Optional<Visitor> findOrNo = Optional.ofNullable(operations.findByUsername(username));
                if(findOrNo.isEmpty()) {
                    Visitor user = new Visitor();
                    user.setUsername(username);
                    return user;
                }
                return findOrNo.get();
            }
        }
        System.out.println(421);
        return new Visitor();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Visitor> findByVisitor= Optional.ofNullable(operations.findByUsername(username));
        return new VisitorDetails(findByVisitor.get());
    }
}
