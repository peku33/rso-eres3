import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from '../components/app.component';
import { HelloWorldComponent } from '../components/hello-world/hello-world.component';

export { AppComponent };

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
        HelloWorldComponent
    ],
    imports: [BrowserModule],
    providers: []
})
export class MainModule {}
