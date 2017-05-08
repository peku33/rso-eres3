import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Semester } from './semester';
export declare class SemesterService {
    private http;
    private headers;
    private semesterUrl;
    constructor(http: Http);
    getSemesters(): Promise<Semester[]>;
    getSemester(id: number): Promise<Semester>;
    private handleError(error);
}
