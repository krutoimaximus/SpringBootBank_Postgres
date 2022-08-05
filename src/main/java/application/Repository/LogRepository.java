package application.Repository;

import application.Entity.Log;
import application.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log,Long> {
    Log findByUser(Users user);

    List<Log> findByUserAndTimestampBetween(Users user, Date from, Date to);
}
