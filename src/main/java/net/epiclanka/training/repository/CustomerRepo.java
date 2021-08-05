package net.epiclanka.training.repository;

import net.epiclanka.training.model.CustomerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends MongoRepository<CustomerModel, Long> {
}
