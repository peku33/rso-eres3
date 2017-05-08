import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HelloWorldComponent} from '../components/hello-world/hello-world.component';
import {LoginPageComponent} from "../components/login-page/login-page.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";
import {MainPageComponent} from "../components/main-page/main-page.component";



const appRoutes: Routes = [
    { path: '', component: LoginPageComponent },
    { path: 'login', component: LoginPageComponent },
    { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(appRoutes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
