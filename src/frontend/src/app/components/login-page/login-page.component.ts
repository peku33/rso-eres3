import { Component, OnInit} from '@angular/core';
import { PermissionService }       from '../../ClassesAndServices/Permission/permission.service';


@Component({
    selector: 'login-page',
    templateUrl: 'login-page.template.html',
    styleUrls: ['login-page.style.css']
})

export class LoginPageComponent implements OnInit{
  constructor( private permissionService: PermissionService) {}
    title: string = "ERES 3.0 - logowanie"

    ngOnInit(): void {
  this.permissionService.getPermissions()
    .then(persons => console.log(persons));
  }
}
