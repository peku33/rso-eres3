import {Directive, AfterViewChecked} from '@angular/core';
declare let componentHandler: any;

@Directive({
    selector: '[mdl]'
})
export class MDLUpgradeElement implements AfterViewChecked {
    ngAfterViewChecked() {
        componentHandler.upgradeAllRegistered();
    }
}
