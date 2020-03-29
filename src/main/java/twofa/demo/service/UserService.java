package twofa.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import twofa.demo.model.AppUser;
import twofa.demo.model.Token;
import twofa.demo.repository.AppUserRepo;
import twofa.demo.repository.TokenRepo;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserService {

    private MailService mailService;
    private TokenRepo tokenRepo;
    private AppUserRepo appUserRepo;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(AppUserRepo appUserRepo, BCryptPasswordEncoder passwordEncoder,TokenRepo tokenRepo,MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole("ROLE_USER");
        appUserRepo.save(appUser);
        sendToken(appUser);
    }
    public void sendToken(AppUser appUser){
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setAppUser(appUser);
        tokenRepo.save(token);
        String url = "http://localhost:8080/token?value="+tokenValue;

        try {
            mailService.sendMail(appUser.getUsername(),"Potwierdz konto",url,false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
