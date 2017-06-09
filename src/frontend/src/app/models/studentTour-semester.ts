import {StudentTour} from "./student-tour";
import {Semester} from "./semester";
import {Specialization} from "./specialization";


export class StudentTourSemester {
    id: number;
    nominalSemesterNo:  number;
    tour: StudentTour;
    semester: Semester;
    specialization: Specialization;
}
