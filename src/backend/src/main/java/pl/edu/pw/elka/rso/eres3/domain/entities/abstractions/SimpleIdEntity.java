package pl.edu.pw.elka.rso.eres3.domain.entities.abstractions;

import java.io.Serializable;

/**
 * Created by Jakub Lorenc on 23.04.17.
 */
public interface SimpleIdEntity<ID extends Serializable> {
    ID getId();
}
