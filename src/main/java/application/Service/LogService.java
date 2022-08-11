package application.Service;

import application.Entity.Client;
import application.Entity.Log;
import application.Entity.Users;
import application.Exception.ClientNotFoundException;
import application.Exception.LogNotFoundException;
import application.Repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public Log findByUser(Users user){
        Optional<Log> foundedLog = Optional.ofNullable(logRepository.findByUser( user));
        return foundedLog.orElseThrow(LogNotFoundException::new);
    }

    public List<Log> findByUserAndTimestampBetween(Users user, Date from, Date to){
        Optional<List<Log>> foundedListLog =
                Optional.ofNullable(logRepository.findByUserAndTimestampBetween(user, from, to));
        return foundedListLog.orElseThrow(LogNotFoundException::new);
    }


}
