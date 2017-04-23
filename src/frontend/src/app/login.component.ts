import {Component} from "@angular/core";

@Component({
    selector: 'login-form',
    templateUrl: require('./login.component.html')
})

export class LoginComponent {
    login: string;
    password: string;
}