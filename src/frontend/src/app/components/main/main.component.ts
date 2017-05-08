import {Component} from "@angular/core";
@Component({
    selector: "eres3",
    template: `
        <ul>
            <li><a routerLink="/dashboard">Strona główna</a></li>
            <li><a routerLink="/users">Użytkownicy</a></li>
            <li><a routerLink="/units">Jednostki organizacyjne</a></li>
        </ul>
        <router-outlet></router-outlet>
    `
})
export class MainComponent {

}