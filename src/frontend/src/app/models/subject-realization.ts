import { Semester } from './semester';
import { SubjectVersion } from './subject-version'

export class SubjectRealization {
  id: number;
  semester: Semester;
  subjectVersion: SubjectVersion;
}
