package com.rijai.webjpa.repository;
import com.rijai.webjpa.model.UserRecord;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRecord,Integer>
{
}
