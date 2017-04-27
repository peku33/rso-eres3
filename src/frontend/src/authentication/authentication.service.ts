import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import {AppSettings} from "../settings/appSettings";

@Injectable()
export class AuthenticationService {
    constructor(private http: Http) {
    }

    login(username: string, password: string) {
        return this.http.post(AppSettings.BACKEND_URL, JSON.stringify({
            username: username,
            password: password
        }))
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                let user = response.json();
                if (user && user.token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }
            }).subscribe();
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}