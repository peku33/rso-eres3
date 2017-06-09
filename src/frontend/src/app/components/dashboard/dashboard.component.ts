import {Component, OnInit} from "@angular/core";
import {GreetingService} from "../../services/greeting.service";
import {Greeting} from "../../models/greeting";
import {Router} from "@angular/router";

@Component({
    selector: "dashboard",
    templateUrl: "dashboard.component.html"
})

export class DashboardComponent implements OnInit {
    greeting: Greeting;

    constructor(private greetingService: GreetingService, private router: Router) {
        this.greeting = {id: 2, content: "default"};
    }

    ngOnInit(): void {
        this.greetingService.getGreeting().then((greeting: Greeting) => {
            this.greeting = greeting;
        }).catch((error) => {
            console.error(error);
            if (error.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (error.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }

}