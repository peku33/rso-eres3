import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AppComponent} from '../components/app.component';
import {HelloWorldComponent} from '../components/hello-world/hello-world.component';
import {LoginPageComponent} from "../components/login-page/login-page.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";
import {MainPageComponent} from "../components/main-page/main-page.component";



export { AppComponent };


const appRoutes: Routes = [
    { path: 'login', component: LoginPageComponent },
    { path: '**', component: PageNotFoundComponent }
];

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
        HelloWorldComponent,
        LoginPageComponent,
        PageNotFoundComponent,
        MainPageComponent
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(appRoutes)
    ],
    providers: []
})

export class MainModule {}
