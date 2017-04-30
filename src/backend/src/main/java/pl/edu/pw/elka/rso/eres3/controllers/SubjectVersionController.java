package pl.edu.pw.elka.rso.eres3.controllers;

import java.util.ArrayList;
import java.util.Comparator;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectVersion;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectVersionRepository;

/**
 * Rest controller for subject's versions.
 */
@RestController
@Transactional
public class SubjectVersionController extends AbstractCrudController<SubjectVersion, Integer>{

	private static final String mapping = "/subjects/versions";
	private final SubjectVersionRepository versionRepository;

	public SubjectVersionController(final SubjectVersionRepository versionRepository) {
		super(versionRepository, true);
		this.versionRepository = versionRepository;
	}

	@RequestMapping(value =  "/subjects/{id}/versions", method = RequestMethod.GET)
	public Iterable<SubjectVersion> getAllSubjectVersions(@PathVariable final int id) {
		return versionRepository.findBySubjectId(id);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SubjectVersion> getSubjectVerion(@PathVariable final int id){
		return getEntity(id);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
	public ResponseEntity<SubjectVersion> addSubjectVerion(@RequestBody final SubjectVersion version){
		final Integer subjectId = version.getSubject().getId();
		final ArrayList<SubjectVersion> existingVersions = Lists.newArrayList(versionRepository.findBySubjectId(subjectId));
		final Character newVersionCode = existingVersions.isEmpty() ? 'A' :
			(char) (existingVersions.stream()
					.map(SubjectVersion::getVersionCode)
					.max(Comparator.naturalOrder()).get() + 1);
		version.setVersionCode(newVersionCode);
		return addEntity(version);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<SubjectVersion> deleteSubjectVerion(@PathVariable final int id) {
		return deleteEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}

}
