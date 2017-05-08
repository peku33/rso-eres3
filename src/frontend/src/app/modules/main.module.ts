import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {MainComponent} from "../components/main/main.component";
import {AppRoutingModule} from "./appRouting.module";
import {UsersComponent} from "../components/users/users.component";
import {UnitsComponent} from "../components/units/units.component";
import {DashboardComponent} from "../components/dashboard/dashboard.component";
import {GreetingService} from "../services/greeting.service";
import {HttpModule} from "@angular/http";
import {UsersService} from "../services/users.service";
import {LoginPageComponent} from "../components/login-page/login-page.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";

@NgModule({
    bootstrap: [MainComponent],
    declarations: [
        MainComponent,
        UsersComponent,
        UnitsComponent,
        DashboardComponent,
        PageNotFoundComponent,
        LoginPageComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpModule
    ],
    providers: [
        GreetingService,
        UsersService
    ]
})
export class MainModule {
}
