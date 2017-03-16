import 'core-js';
import 'reflect-metadata';
import 'zone.js';
import 'tether';
import 'bootstrap';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.min.css';
import 'jquery';

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {AppModule} from './app/app.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

platformBrowserDynamic().bootstrapModule(AppModule);