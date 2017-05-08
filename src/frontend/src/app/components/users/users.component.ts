import {Component, OnInit} from "@angular/core";
import {UsersService} from "../../services/users.service";
import {User} from "../../model/user";
@Component({
    selector: "users-table",
    templateUrl: "users.component.html"
})

export class UsersComponent implements OnInit{
    private users: User[];

    constructor(private usersService: UsersService){
    }

    ngOnInit(): void {
        this.usersService.getAllUsers().then((response: User[]) => {
            this.users = response;
        }).catch((error) => console.error(error));
    }
}