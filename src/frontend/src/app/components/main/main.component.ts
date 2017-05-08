import {Component} from "@angular/core";
@Component({
    selector: "eres3",
    template: `
        <nav class="mdl-navigation">
            <a class="mdl-navigation__link" routerLink="/dashboard">Strona główna</a>
            <a class="mdl-navigation__link" routerLink="/users">Użytkownicy</a>
            <a class="mdl-navigation__link" routerLink="/units">Jednostki organizacyjne</a>
        </nav>
        <router-outlet></router-outlet>
    `,
    styleUrls: ['main.component.css']
})
export class MainComponent {

}