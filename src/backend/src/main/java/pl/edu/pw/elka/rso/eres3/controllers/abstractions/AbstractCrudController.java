package pl.edu.pw.elka.rso.eres3.controllers.abstractions;

import com.google.common.collect.Lists;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

/**
 * Abstract base class for typical crud controller implementations.
 * The derived class should be marked with {@link javax.transaction.Transactional} annotation.
 *
 * Created by Jakub Lorenc on 23.04.17.
 */
public abstract class AbstractCrudController<T extends SimpleIdEntity<ID>, ID extends Serializable> {
    public final ResponseEntity<T> notFound = ResponseEntity.notFound().build();
    public final ResponseEntity<T> badRequest = ResponseEntity.badRequest().build();
    public final ResponseEntity<T> noContent = ResponseEntity.noContent().build();

    protected final CrudRepository<T, ID> repository;

    private final boolean artificialId;

    protected AbstractCrudController(final CrudRepository<T, ID> repository, final boolean artificialId){
        this.repository = repository;
        this.artificialId = artificialId;
    }

    protected List<T> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    protected ResponseEntity<T> getEntity(final ID id){
        if(id == null) {
            return badRequest;
        }
        final T entity = repository.findOne(id);
        if(entity == null){
            return notFound;
        }
        return ResponseEntity.ok(entity);
    }

    protected ResponseEntity<T> addEntity(final T entity) {
        if(artificialId && entity.getId() != null) {
            return badRequest;
        }
        final T createdEntity = repository.save(entity);
        return getAddSuccessResponse(createdEntity);
    }

    private ResponseEntity<T> getAddSuccessResponse(final T entity) {
        final URI uri = URI.create(
                getControllerMapping() +
                '/' +
                entity.getId()
        );
        return ResponseEntity.created(uri).body(entity);
    }

    protected ResponseEntity<T> updateEntity(final T entity) {
        if(entity.getId() == null) {
            return badRequest;
        }
        if(!repository.exists(entity.getId())){
            return notFound;
        }
        return ResponseEntity.ok(repository.save(entity));
    }

    protected ResponseEntity deleteEntity(final ID id) {
        if(id == null) {
            return badRequest;
        }
        if(!repository.exists(id)){
            return notFound;
        }
        repository.delete(id);
        return noContent;
    }

    protected abstract String getControllerMapping();
}
