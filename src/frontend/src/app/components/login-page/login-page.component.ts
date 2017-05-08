import { Component, OnInit} from '@angular/core';
import { SubjectService }       from '../../ClassesAndServices/Subject/subject.service';


@Component({
    selector: 'login-page',
    templateUrl: 'login-page.template.html',
    styleUrls: ['login-page.style.css']
})

export class LoginPageComponent implements OnInit{
  constructor( private subjectService: SubjectService) {}
    title: string = "ERES 3.0 - logowanie"

    ngOnInit(): void {
  this.subjectService.getSubject(2)
    .then(subject => console.log(subject));
  }
}
