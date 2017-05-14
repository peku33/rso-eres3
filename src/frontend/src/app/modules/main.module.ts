import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {InMemoryWebApiModule} from "angular-in-memory-web-api";

import {MainComponent} from "../components/main/main.component";
import {PersonsComponent} from "../components/persons/persons.component";
import {UnitsComponent} from "../components/units/units.component";
import {DashboardComponent} from "../components/dashboard/dashboard.component";
import {LoginPageComponent} from "../components/login-page/login-page.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";
import {AppComponent} from "../components/app.component";

import {PersonService} from "../ClassesAndServices/Person/person.service";
import {PermissionService} from "../ClassesAndServices/Permission/permission.service";
import {SemesterService} from "../ClassesAndServices/Semester/semester.service";
import {SubjectService} from "../ClassesAndServices/Subject/subject.service";
import {GrantedPermissionsService} from "../services/granted-permissions.service";
import {GreetingService} from "../services/greeting.service";
import {OrganizationalUnitsService} from "../services/organizational-units.service";
import {PermissionsService} from "../services/permissions.service";
import {PersonsService} from "../services/persons.service";
import {SemestersService} from "../services/semesters.service";
import {SpecializationsService} from "../services/specializations.service";
import {SubjectRealizationsService} from "../services/subject-realizations.service";
import {SubjectVersionsService} from "../services/subject-versions.service";
import {SubjectsService} from "../services/subjects.service";
import {InMemoryDataService} from "../services/in-memory-data.service";

import {AppRoutingModule} from "./appRouting.module";

import "material-design-lite";

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
        LoginPageComponent,
        AppComponent
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
        SubjectsService,
        PersonService,
        PermissionService,
        SemesterService,
        SubjectService
    ],
    bootstrap: [MainComponent],
})
export class MainModule {
}
