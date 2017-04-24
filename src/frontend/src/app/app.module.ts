import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {InMemoryWebApiModule} from "angular-in-memory-web-api";

import {AppComponent} from "./app.component";
import {AppRoutingModule} from "./app-routing.module";
import {DashboardComponent} from "./dashboard.component";
import {HeroDetailComponent} from "./hero-detail.component";
import {HeroesComponent} from "./heroes.component";
import {HeroSearchComponent} from "./hero-search.component";
import {LoginComponent} from "./login.component";

import {HeroService} from "./hero.service";
import {InMemoryDataService} from "./in-memory-data.service";
import {HeroSearchService} from "./hero-search.service";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService)
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    HeroDetailComponent,
    HeroesComponent,
    HeroSearchComponent,
    LoginComponent
  ],
  bootstrap: [AppComponent],
  providers: [HeroService, HeroSearchService]
})

export class AppModule {
}
