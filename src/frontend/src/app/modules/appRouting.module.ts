import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {PersonsComponent} from "../components/persons/persons.component";
import {UnitsComponent} from "../components/units/units.component";
import {DashboardComponent} from "../components/dashboard/dashboard.component";
import {PageNotFoundComponent} from "../components/page-not-found/page-not-found.component";
import {LoginPageComponent} from "../components/login-page/login-page.component";
import {EditUnitComponent} from "../components/units/add/editUnit.component";
import {AddUnitComponent} from "../components/units/add/addUnit.component";
import {AddPersonComponent} from "../components/persons/add/addPerson.component";
import {EditPersonComponent} from "../components/persons/add/editPerson.component";

const routes: Routes = [
    {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
    {path: 'login', component: LoginPageComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'persons', component: PersonsComponent},
    {path: 'persons/add', component: AddPersonComponent},
    {path: 'persons/edit/:id', component: EditPersonComponent},
    {path: 'units', component: UnitsComponent},
    {path: 'units/add', component: AddUnitComponent},
    {path: 'units/edit/:id', component: EditUnitComponent},
    {path: '**', component: PageNotFoundComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}