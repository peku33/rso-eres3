import {Component} from "@angular/core";
import {AuthenticationService} from "./authentication/authentication.service";
@Component ({
    selector: 'login-page',
    templateUrl: "./login.component.html"
})

export class LoginComponent {
    login: string;
    password: string;

    constructor(private authenticationService: AuthenticationService){
    }

    logIn() {
        this.authenticationService.login(this.login, this.password);
    }
}