import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Subject } from './subject';
export declare class SubjectService {
    private http;
    private headers;
    private subjectsUrl;
    constructor(http: Http);
    getSubject(id: number): Promise<Subject>;
    delete(id: number): Promise<void>;
    update(subject: Subject): Promise<Subject>;
    private handleError(error);
}
