import {NgModule}             from '@angular/core';
import {BrowserModule}        from '@angular/platform-browser';
import {RouterModule, Routes} from "@angular/router";
import { HttpModule }         from '@angular/http';

// Imports for loading & configuring the in-memory web api
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService }  from '../ClassesAndServices/in-memory-data.service';


import {AppComponent}         from '../components/app.component';
import {HelloWorldComponent}  from '../components/hello-world/hello-world.component';
import {LoginPageComponent}   from "../components/login-page/login-page.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";
import {MainPageComponent}    from "../components/main-page/main-page.component";

import { AppRoutingModule }   from './app-routing.module';

import { PersonService }       from '../ClassesAndServices/Person/person.service';
import { PermissionService }   from '../ClassesAndServices/Permission/permission.service';
import { SemesterService }     from '../ClassesAndServices/Semester/semester.service';
import { SubjectService }      from '../ClassesAndServices/Subject/subject.service';


export { AppComponent };


@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpModule,
        InMemoryWebApiModule.forRoot(InMemoryDataService),
    ],
    declarations: [
        AppComponent,
        HelloWorldComponent,
        LoginPageComponent,
        PageNotFoundComponent,
        MainPageComponent
    ],
    providers: [
      PersonService,
      PermissionService,
      SemesterService,
      SubjectService
    ],
    bootstrap: [AppComponent],

})

export class MainModule {}
