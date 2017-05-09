import {BrowserModule}  from "@angular/platform-browser";
import {NgModule}       from "@angular/core";
import {HttpModule}     from "@angular/http";

import {MainComponent}         from "../components/main/main.component";
import {PersonsComponent}      from "../components/persons/persons.component";
import {UnitsComponent}        from "../components/units/units.component";
import {DashboardComponent}    from "../components/dashboard/dashboard.component";
import {LoginPageComponent}    from "../components/login-page/login-page.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";


import {GrantedPermissionsService} from "../services/granted-permissions.service";
import {GreetingService}           from "../services/greeting.service";
import {OrganizationalUnitsService}from "../services/organizational-units.service";
import {PermissionsService}        from "../services/permissions.service";
import {PersonsService}            from "../services/persons.service";
import {SemestersService}          from "../services/semesters.service";
import {SpecializationsService}    from "../services/specializations.service";
import {SubjectRealizationsService}from "../services/subject-realizations.service";
import {SubjectVersionsService}    from "../services/subject-versions.service";
import {SubjectsService}           from "../services/subjects.service";

import {AppRoutingModule} from "./appRouting.module";

// Imports for loading & configuring the in-memory web api
// For mocking a database
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService }  from '../services/in-memory-data.service';


@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpModule,
        InMemoryWebApiModule.forRoot(InMemoryDataService),
    ],
    declarations: [
        MainComponent,
        PersonsComponent,
        UnitsComponent,
        DashboardComponent,
        PageNotFoundComponent,
        LoginPageComponent
    ],
    providers: [
        GrantedPermissionsService,
        GreetingService,
        OrganizationalUnitsService,
        PermissionsService,
        PersonsService,
        SemestersService,
        SpecializationsService,
        SubjectRealizationsService,
        SubjectVersionsService,
        SubjectsService
    ],
    bootstrap: [MainComponent],
})
export class MainModule {
}
