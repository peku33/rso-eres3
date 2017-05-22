import {Component, OnInit} from "@angular/core";
import {GreetingService} from "../../services/greeting.service";
import {Greeting} from "../../model/greeting";

@Component({
    selector: "dashboard",
    templateUrl: "dashboard.component.html"
})

export class DashboardComponent implements OnInit {
    greeting: Greeting;

    constructor(private greetingService: GreetingService) {
        this.greeting = {id: 2, content: "default"};
    }

    ngOnInit(): void {
        this.greetingService.getGreeting().then((greeting: Greeting) => {
            this.greeting = greeting;
        }).catch((error) => console.error(error));
    }

}