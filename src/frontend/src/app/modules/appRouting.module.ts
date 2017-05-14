import {NgModule}             from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PersonsComponent} from "../components/persons/persons.component";
import {UnitsComponent} from "../components/units/units.component";
import {DashboardComponent} from "../components/dashboard/dashboard.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";
import {LoginPageComponent} from "../components/login-page/login-page.component";
import {AddEditPersonComponent} from "../components/persons/add/addEditPerson.component";
import {AddEditUnitComponent} from "../components/units/add/addEditUnit.component";

const routes: Routes = [
    {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
    {path: 'login', component: LoginPageComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'persons', component: PersonsComponent},
    {path: 'persons/add', component: AddEditPersonComponent},
    {path: 'units', component: UnitsComponent},
    {path: 'units/add', component: AddEditUnitComponent},
    {path: '**', component: PageNotFoundComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}