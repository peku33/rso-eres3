import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {MainComponent} from "../components/main/main.component";
import {AppRoutingModule} from "./appRouting.module";
import {PersonsComponent} from "../components/persons/persons.component";
import {UnitsComponent} from "../components/units/units.component";
import {DashboardComponent} from "../components/dashboard/dashboard.component";
import {GreetingService} from "../services/greeting.service";
import {HttpModule} from "@angular/http";
import {PersonsService} from "../services/persons.service";
import {LoginPageComponent} from "../components/login-page/login-page.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";
import {AppComponent} from "../components/app.component";
import {UnitsService} from "../services/units.service";

@NgModule({
    bootstrap: [MainComponent],
    declarations: [
        MainComponent,
        PersonsComponent,
        UnitsComponent,
        DashboardComponent,
        PageNotFoundComponent,
        LoginPageComponent,
        AppComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpModule
    ],
    providers: [
        GreetingService,
        PersonsService,
        UnitsService
    ]
})
export class MainModule {
}
