import {Injectable} from "@angular/core";
import {Greeting} from "../models/greeting";
import {Http} from "@angular/http";

import "rxjs/add/operator/toPromise";
import {BE_URL} from "../settings/backendInfo";

@Injectable()
export class GreetingService {
    constructor(private http: Http) {
    }

    public getGreeting(): Promise<Greeting> {
        return this.http.get(BE_URL)
            .toPromise()
            .then((response) => {
                return response.json() as Greeting;
            });
    }
}
