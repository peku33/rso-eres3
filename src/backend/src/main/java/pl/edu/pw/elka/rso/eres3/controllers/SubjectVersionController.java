package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectVersion;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectVersionRepository;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.stream.StreamSupport;

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
    @PreAuthorize("hasPermission(#id, 'Subject', 'SubjectVersionRead')")
	public Iterable<SubjectVersion> getAllSubjectVersionsBySubjectId(@PathVariable final int id) {
		return versionRepository.findBySubjectId(id);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'SubjectVersion', 'SubjectVersionRead')")
	public ResponseEntity<SubjectVersion> getSubjectVersion(@PathVariable final int id){
		return getEntity(id);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#version, 'SubjectVersionCreate')")
	public ResponseEntity<SubjectVersion> addSubjectVersion(@RequestBody final SubjectVersion version){
		if(version == null || version.getSubject() == null || version.getSubject().getId() == null)
		{
			return badRequest;
		}
		final Integer subjectId = version.getSubject().getId();
		final Iterable<SubjectVersion> existingVersions = versionRepository.findBySubjectId(subjectId);
		version.setVersionCode(generateVersionCode(existingVersions));
		return addEntity(version);
	}

	private Character generateVersionCode(final Iterable<SubjectVersion> existingVersions){
		return StreamSupport.stream(existingVersions.spliterator(), false)
				.map(SubjectVersion::getVersionCode)
				.max(Comparator.naturalOrder())
				.map(character -> (char)(character + 1))
				.orElse('A');
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'SubjectVersion', 'SubjectVersionDelete')")
	public ResponseEntity<SubjectVersion> deleteSubjectVersion(@PathVariable final int id) {
		return deleteEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}
}