package dev.karimshaaban.cruddemo.dao;

import dev.karimshaaban.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//easier than all that boilerplate code we used to have
//Integer => PK Type
//@RepositoryRestResource(path="members") //you can customize it however you want (/members)
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    //that's it no need to write any code
}
