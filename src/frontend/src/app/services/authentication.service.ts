import { Injectable } from '@angular/core';
import { Http, Headers, Response, } from '@angular/http';
import {BE_URL} from "../settings/backendInfo";
import { URLSearchParams } from "@angular/http"


@Injectable()
export class AuthenticationService {

    constructor(private http: Http) { }



    login(username: string, password: string) {
        let loginData = new URLSearchParams();
        loginData.append('username', username);
        loginData.append('password', password);

        return this.http.post(BE_URL+'/login', loginData)
            .toPromise()
            .then(() => {

            console.log("ok")
            })
            .catch(this.handleError);
            // .map(data => {
            //         console.log("ok");
            //     }, error => {
            //         console.log("error");
            //     });



            // .map((response: Response) => {
            //     // login successful if there's a jwt token in the response
            //     let user = response.json();
            //     if (user && user.token) {
            //         // store user details and jwt token in local storage to keep user logged in between page refreshes
            //         localStorage.setItem('currentUser', JSON.stringify(user));
            //     }
            // });
    }

    logout() {
        // remove user from local storage to log user out
        // return this.router.navigateByUrl("/logout")
        //     .toPromise()
        //     .then(() => {
        //
        //         console.log("ok")
        //     })
        //     .catch(this.handleError);

    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}