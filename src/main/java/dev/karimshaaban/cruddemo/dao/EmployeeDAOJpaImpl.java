package dev.karimshaaban.cruddemo.dao;

import dev.karimshaaban.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {
    //define field for entity manager
    private EntityManager entityManager;

    //setup constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        //create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee", Employee.class);
        //execute query and get result set
        List<Employee> employees = theQuery.getResultList();
        //return the results
        return employees;
    }

    @Override
    public Employee findById(int id) {
        //get employee
        Employee theEmployee = entityManager.find(Employee.class, id);
        //return employee
        return theEmployee;
    }

    @Override
    //no @Transaction we'll at that at service layer
    public Employee save(Employee employee) {
        //save employee
        //merge id = 0 ? save : update
        Employee dbEmployee = entityManager.merge(employee);
        //return the updated employee
        return dbEmployee;
    }

    @Override
    //no @Transaction we'll at that at service layer
    public void deleteById(int id) {
        //find by id
        Employee employee = entityManager.find(Employee.class, id);
        //remove the employee
        entityManager.remove(employee);
    }
}
