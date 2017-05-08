import { InMemoryDbService } from 'angular-in-memory-web-api';
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    let persons = [
      {id: 1, firstName: 'firstName1', otherNames: 'otherNames1', lastName: 'lastName1', login: 'login1', password: 'password1', pesel: 'pesel1'},
      {id: 2, firstName: 'firstName2', otherNames: 'otherNames2', lastName: 'lastName2', login: 'login2', password: 'password2', pesel: 'pesel2'},
      {id: 3, firstName: 'firstName3', otherNames: 'otherNames3', lastName: 'lastName3', login: 'login3', password: 'password3', pesel: 'pesel3'},
      {id: 4, firstName: 'firstName4', otherNames: 'otherNames4', lastName: 'lastName4', login: 'login4', password: 'password4', pesel: 'pesel4'},
      {id: 5, firstName: 'firstName5', otherNames: 'otherNames5', lastName: 'lastName5', login: 'login5', password: 'password5', pesel: 'pesel5'},
      {id: 6, firstName: 'firstName6', otherNames: 'otherNames6', lastName: 'lastName6', login: 'login6', password: 'password6', pesel: 'pesel6'},
      {id: 7, firstName: 'firstName7', otherNames: 'otherNames7', lastName: 'lastName7', login: 'login7', password: 'password7', pesel: 'pesel7'},
      {id: 8, firstName: 'firstName8', otherNames: 'otherNames8', lastName: 'lastName8', login: 'login8', password: 'password8', pesel: 'pesel8'},
      {id: 9, firstName: 'firstName9', otherNames: 'otherNames9', lastName: 'lastName9', login: 'login9', password: 'password9', pesel: 'pesel9'},
      {id: 10, firstName: 'firstName10', otherNames: 'otherNames10', lastName: 'lastName10', login: 'login10', password: 'password10', pesel: 'pesel10'},
    ];

    let permissions = [
      {name: 'name1', description: 'description1'},
      {name: 'name2', description: 'description2'},
      {name: 'name3', description: 'description3'},
      {name: 'name4', description: 'description4'},
      {name: 'name5', description: 'description5'},
    ]


    return {persons, permissions};
  }
}
