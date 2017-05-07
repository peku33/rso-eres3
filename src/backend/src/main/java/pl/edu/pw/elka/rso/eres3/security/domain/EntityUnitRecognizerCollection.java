package pl.edu.pw.elka.rso.eres3.security.domain;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Collection of all {@link EntityUnitRecognizer} objects used by application.
 */
@Component
public class EntityUnitRecognizerCollection {

	private final Map<String, EntityUnitRecognizer> unitRecognizersByClassName = Maps.newHashMap();
	private final Map<Class<?>, EntityUnitRecognizer> unitRecognizersByClass = Maps.newHashMap();

	@Autowired
	public EntityUnitRecognizerCollection(
			final PlainUnitRecognizer plainUnitRecognizer,
			final SubjectUnitRecognizer subjectUnitRecognizer,
			final SubjectVersionUnitRecognizer subjectVersionUnitRecognizer,
			final SubjectRealizationUnitRecognizer subjectRealizationUnitRecognizer) {
		final ArrayList<EntityUnitRecognizer> unitRecognizers = Lists.newArrayList(
				plainUnitRecognizer,
				subjectUnitRecognizer,
				subjectVersionUnitRecognizer,
				subjectRealizationUnitRecognizer);
		for(final EntityUnitRecognizer unitRecognizer : unitRecognizers)
		{
			unitRecognizersByClass.put(unitRecognizer.getRecognizableClass(), unitRecognizer);
			unitRecognizersByClassName.put(unitRecognizer.getRecognizableClass().getSimpleName(), unitRecognizer);
		}
	}

	public EntityUnitRecognizer getByClass(final Class<?> entityToRecognizeClass){
		return unitRecognizersByClass.get(entityToRecognizeClass);
	}

	public EntityUnitRecognizer getByClassName(final String entityToRecognizeClassName){
		return unitRecognizersByClassName.get(entityToRecognizeClassName);
	}
}