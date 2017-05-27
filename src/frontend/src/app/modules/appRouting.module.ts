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
import {SpecializationsComponent} from "../components/specializations/specializations.component";
import {PermissionsComponent} from "../components/permissions/permissions.component";
import {AddPermissionComponent} from "../components/permissions/add/addPermission.component";
import {EditPermissionComponent} from "../components/permissions/edit/editPermission.component";

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
    {path: 'units/specializations/:id', component: SpecializationsComponent},
    {path: 'permissions', component: PermissionsComponent},
    {path: 'permissions/add', component: AddPermissionComponent},
    {path: 'permissions/edit/:name', component: EditPermissionComponent},
    {path: '**', component: PageNotFoundComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}