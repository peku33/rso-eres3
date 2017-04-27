import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";

import {RootComponent} from "./root.component";
import {LoginComponent} from "./login.component";
import {AuthenticationService} from "./authentication/authentication.service";
import {HttpModule} from "@angular/http";

@NgModule(
    {
        declarations: [
            RootComponent, LoginComponent
        ],
        imports: [
            BrowserModule,
            FormsModule,
            HttpModule
        ],
        exports: [],
        providers: [
            AuthenticationService
        ],
        bootstrap: [
            RootComponent
        ],
    })
export class RootModule {
}