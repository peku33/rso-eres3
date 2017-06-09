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
import {EditSpecializationComponent} from "../components/specializations/addEdit/editSpecialization.component";
import {AddSpecializationComponent} from "../components/specializations/addEdit/addSpecialization.component";
import {PermissionsComponent} from "../components/permissions/permissions.component";
import {AddPermissionComponent} from "../components/permissions/add/addPermission.component";
import {EditPermissionComponent} from "../components/permissions/edit/editPermission.component";
import {UserPermissions} from "../components/user-permissions/userPermissions.component";
import {StudentToursComponent} from "../components/student-tours/student-tours.component";
import {AddStudentTourComponent} from "../components/student-tours/addEdit/addStudentTour.component";
import {EditStudentTourComponent} from "../components/student-tours/addEdit/editStudentTour.component";
import {StudentToursSemesterComponent} from "../components/studentTours-semester/studentTours-semester.component";
import {AddStudentTourSemesterComponent} from "../components/studentTours-semester/addEdit/addStudentTourSemester.component";
import {EditStudentTourSemesterComponent} from "../components/studentTours-semester/addEdit/editStudentTourSemester.component";
import {CreditComponent} from "../components/credit/credit.component";
import {SubjectsComponent} from "../components/subjects/subjects.component";
import {AddSubjectComponent} from "../components/subjects/add/addSubject.component";


const routes: Routes = [
    {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
    {path: 'login', component: LoginPageComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'persons', component: PersonsComponent},
    {path: 'persons/add', component: AddPersonComponent},
    {path: 'persons/:id', component: EditPersonComponent},
    {path: 'persons/:id/permissions', component: UserPermissions},
    {path: 'units', component: UnitsComponent},
    {path: 'units/add', component: AddUnitComponent},
    {path: 'units/:id', component: EditUnitComponent},
    {path: 'units/:unitId/specializations', component: SpecializationsComponent},
    {path: 'units/:unitId/specializations/:id', component: EditSpecializationComponent},
    {path: 'units/:unitId/specializations/:id/add', component: AddSpecializationComponent},
    {path: 'units/:unitId/subjects', component: SubjectsComponent},
    {path: 'units/:unitId/subjects/add', component: AddSubjectComponent},
    {path: 'permissions', component: PermissionsComponent},
    {path: 'permissions/add', component: AddPermissionComponent},
    {path: 'permissions/edit/:name', component: EditPermissionComponent},
    {path: 'persons/:personId/studenttours', component: StudentToursComponent},
    {path: 'persons/:personId/studenttours/add', component: AddStudentTourComponent},
    {path: 'persons/:personId/studenttours/:id', component: EditStudentTourComponent},
    {path: 'persons/:personId/studenttours/:tourId/studenttoursemesters', component: StudentToursSemesterComponent},
    {path: 'persons/:personId/studenttours/:tourId/studenttoursemesters/add', component: AddStudentTourSemesterComponent},
    {path: 'persons/:personId/studenttours/:tourId/studenttoursemesters/:id', component: EditStudentTourSemesterComponent},
    {path: '**', component: PageNotFoundComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}