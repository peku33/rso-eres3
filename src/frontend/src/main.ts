import 'core-js';
import 'reflect-metadata';
import 'zone.js';
import 'jquery';
import 'bootstrap';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.min.css';

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {AppModule} from './app/app.module';

platformBrowserDynamic().bootstrapModule(AppModule);