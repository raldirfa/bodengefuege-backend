package de.hsosnabrueck.bodengefuegeapp.repository;

import de.hsosnabrueck.bodengefuegeapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public default User getUserFromToken(String jwtToken){
        for(User u: this.findAll()){
            if(u.getJwtToken().equals(jwtToken)){
                return u;
            }
        }
        return null;
    }
}
