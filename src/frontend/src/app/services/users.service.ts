import {Injectable} from "@angular/core";
import {User} from "../model/user";
import {BE_URL} from "../settings/backendInfo";
import {Http} from "@angular/http";
@Injectable()
export class UsersService {
    constructor(private http: Http) {
    }

    public getAllUsers(): Promise<User[]> {
        return this.http.get(BE_URL + "/persons")
            .toPromise()
            .then((response) => {
                return response.json() as User[];
            });
    }
}