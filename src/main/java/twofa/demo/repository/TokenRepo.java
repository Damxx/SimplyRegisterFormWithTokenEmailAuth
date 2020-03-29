package twofa.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import twofa.demo.model.Token;

@Repository
public interface TokenRepo extends JpaRepository<Token,Long> {

    Token findByValue(String value);

}
