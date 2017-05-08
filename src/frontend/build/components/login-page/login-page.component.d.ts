import { OnInit } from '@angular/core';
import { SubjectService } from '../../ClassesAndServices/Subject/subject.service';
export declare class LoginPageComponent implements OnInit {
    private subjectService;
    constructor(subjectService: SubjectService);
    title: string;
    ngOnInit(): void;
}
