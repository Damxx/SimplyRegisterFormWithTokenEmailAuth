package twofa.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import twofa.demo.model.AppUser;
import twofa.demo.model.Token;
import twofa.demo.repository.AppUserRepo;
import twofa.demo.repository.TokenRepo;
import twofa.demo.service.UserService;

@Controller
public class MainController {

    private UserService userService;
    private TokenRepo tokenRepo;
    private AppUserRepo appUserRepo;

    @Autowired
    public MainController(UserService userService, TokenRepo tokenRepo, AppUserRepo appUserRepo) {
        this.userService = userService;
        this.tokenRepo = tokenRepo;
        this.appUserRepo = appUserRepo;
    }

    @GetMapping("/admin")
    public String foradmin(){
        return "helloAdmin";
    }

    @GetMapping("/user")
    public String forUser(){
        return "helloUser";
    }

    @GetMapping("/singup")
    public String singUp(Model theModel){
        theModel.addAttribute("user",new AppUser());
        return "sing-up";
    }
    @PostMapping("/register")
    public String register(AppUser appUser){
        userService.addUser(appUser);
        return "sing-up";
    }

    @GetMapping("/token")
    public String tokenAuth(@RequestParam String value){
        Token byValue = tokenRepo.findByValue(value);
        AppUser appUser = byValue.getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
        return "sing-up";
    }

}
