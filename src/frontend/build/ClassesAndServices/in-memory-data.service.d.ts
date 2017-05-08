import { InMemoryDbService } from 'angular-in-memory-web-api';
export declare class InMemoryDataService implements InMemoryDbService {
    createDb(): {
        persons: {
            id: number;
            firstName: string;
            otherNames: string;
            lastName: string;
            login: string;
            password: string;
            pesel: string;
        }[];
        permissions: {
            id: number;
            name: string;
            description: string;
        }[];
        semesters: {
            id: string;
            year: string;
            semesterType: string;
        }[];
        subjects: {
            id: number;
            fullName: string;
            shortName: string;
            didacticalUnits: number;
            ects: number;
            type: string;
        }[];
    };
}
