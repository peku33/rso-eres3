import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Permission } from './permission';
export declare class PermissionService {
    private http;
    private headers;
    private permisionUrl;
    constructor(http: Http);
    getPermissions(): Promise<Permission[]>;
    getPermission(id: number): Promise<Permission>;
    delete(id: number): Promise<void>;
    update(permission: Permission): Promise<Permission>;
    private handleError(error);
}
