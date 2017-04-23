package pl.edu.pw.elka.rso.eres3.controllers.abstractions;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

import java.net.URI;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for all standard rest controllers
 *
 * Created by Jakub Lorenc on 23.04.17.
 */
public class AbstractCrudControllerTest {
    private static class DummyEntity implements SimpleIdEntity<Integer> {
        private Integer id;

        DummyEntity() {}

        DummyEntity(final Integer id) {
            this.id = id;
        }

        @Override
        public Integer getId(){
            return id;
        }
    }

    private static class DummyController extends AbstractCrudController<DummyEntity, Integer> {
        DummyController(final CrudRepository<DummyEntity, Integer> repository, final boolean artificialId) {
            super(repository, artificialId);
        }

        @Override
        protected String getControllerMapping() {
            return "/dummy";
        }
    }

    private interface DummyRepository extends CrudRepository<DummyEntity, Integer>{}

    private DummyRepository repository;
    private DummyController controller;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(DummyRepository.class);
        controller = new DummyController(repository, true);
    }

    @Test
    public void getAll() throws Exception {
        final Set<DummyEntity> entitySet = ImmutableSet.of(
                new DummyEntity(1),
                new DummyEntity(2)
        );
        Mockito.when(repository.findAll()).thenReturn(entitySet);
        assertEquals(entitySet, ImmutableSet.copyOf(controller.getAll()));
    }

    @Test
    public void getEntity() throws Exception {
        final DummyEntity entity = new DummyEntity(1);
        Mockito.when(repository.findOne(1)).thenReturn(entity);
        Mockito.when(repository.findOne(2)).thenReturn(null);

        assertEquals(entity, controller.getEntity(1).getBody());
        assertEquals(controller.notFound, controller.getEntity(2));
    }

    @Test
    public void addEntity() throws Exception {
        final DummyEntity entityWithId = new DummyEntity(1);
        final DummyEntity newEntity = new DummyEntity();
        Mockito.when(repository.save(newEntity)).thenReturn(new DummyEntity(2));

        assertEquals(controller.badRequest, controller.addEntity(entityWithId));

        final ResponseEntity<DummyEntity> goodRequest = controller.addEntity(newEntity);
        assertEquals(Integer.valueOf(2), goodRequest.getBody().getId());
        assertEquals(URI.create("/dummy/2"), goodRequest.getHeaders().getLocation());
    }

    @Test
    public void addEntityWithArtificialId() throws Exception {
        final DummyController specialController = new DummyController(repository, false);
        final DummyEntity entity = new DummyEntity(1);
        Mockito.when(repository.save(entity)).thenReturn(entity);

        final ResponseEntity<DummyEntity> request = specialController.addEntity(entity);
        assertEquals(Integer.valueOf(1), request.getBody().getId());
        assertEquals(URI.create("/dummy/1"), request.getHeaders().getLocation());
    }

    @Test
    public void updateEntity() throws Exception {
        final DummyEntity entityWithoutId = new DummyEntity();
        final DummyEntity entity = new DummyEntity(1);
        final DummyEntity nonExistingEntity = new DummyEntity(2);
        Mockito.when(repository.exists(entity.getId())).thenReturn(true);
        Mockito.when(repository.exists(nonExistingEntity.getId())).thenReturn(false);
        Mockito.when(repository.save(entity)).thenReturn(new DummyEntity(1));

        assertEquals(controller.badRequest, controller.updateEntity(entityWithoutId));
        assertEquals(controller.notFound, controller.updateEntity(nonExistingEntity));

        final ResponseEntity<DummyEntity> goodRequest = controller.updateEntity(entity);
        assertEquals(entity.getId(), goodRequest.getBody().getId());
        assertTrue(goodRequest.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deleteEntity() throws Exception {
        Mockito.when(repository.exists(1)).thenReturn(true);
        Mockito.when(repository.exists(2)).thenReturn(false);

        assertEquals(controller.notFound, controller.deleteEntity(2));
        assertTrue(controller.deleteEntity(1).getStatusCode().is2xxSuccessful());
    }
}