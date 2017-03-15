"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
require("core-js");
require("reflect-metadata");
require("zone.js");
require("jquery");
require("bootstrap");
require("!style-loader!css-loader!bootstrap/dist/css/bootstrap.min.css");
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
var app_module_1 = require("./app/app.module");
platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(app_module_1.AppModule);
//# sourceMappingURL=main.js.map