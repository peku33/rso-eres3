import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {AppComponent} from "./app.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {HttpModule} from "@angular/http";
import {LoginComponent} from "./login.component";
import {AppRoutingModule} from "./app-routing.module";

@NgModule({
    imports: [NgbModule.forRoot(), BrowserModule, HttpModule, AppRoutingModule],
    declarations: [AppComponent, LoginComponent],
    bootstrap: [AppComponent]
})
export class AppModule {
}