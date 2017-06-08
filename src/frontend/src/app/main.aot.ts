import { platformBrowser } from '@angular/platform-browser';
import { AppModuleNgFactory } from './modules/app.module.ngfactory';

platformBrowser().bootstrapModuleFactory(AppModuleNgFactory);
