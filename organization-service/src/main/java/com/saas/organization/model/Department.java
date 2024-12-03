package com.saas.organization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id", nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Department name cannot be blank")
    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @NotNull(message = "Established date cannot be null")
    @Column(name = "established_date", nullable = false)
    private LocalDate establishedDate;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "department_type_id")
    private DepartmentType departmentType;

    @ManyToOne
    @JoinColumn(name = "parent_department_id")
    private Department parentDepartment;

    @OneToMany(mappedBy = "parentDepartment")
    private Set<Department> subDepartments = new HashSet<>();

    @OneToMany(mappedBy = "department")
    private List<DepartmentHistory> departmentHistory;

    @OneToMany(mappedBy = "department")
    private List<JobRegistration> jobRegistrations;

    @OneToMany(mappedBy = "department")
    private List<StaffPlan> staffPlans;

    @OneToMany(mappedBy = "department")
    private List<Address> addresses;

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((id == null) ? 0 : id.hashCode());
//        return result;
//    }

    // Ensures bidirectional relationship consistency
    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
        if (parentDepartment != null) {
            parentDepartment.getSubDepartments().add(this);
        }
    }

    // Adds a sub-department and sets the parent department
    public void addSubDepartment(Department subDepartment) {
        subDepartments.add(subDepartment);
        subDepartment.setParentDepartment(this);
    }

    // Removes a sub-department and clears its parent department reference
    public void removeSubDepartment(Department subDepartment) {
        subDepartments.remove(subDepartment);
        subDepartment.setParentDepartment(null);
    }

    // Retrieves all parent departments recursively
    public List<Department> getAllParentDepartments() {
        List<Department> parentDepartments = new ArrayList<>();
        Department currentDepartment = this;
        while (currentDepartment.getParentDepartment() != null) {
            currentDepartment = currentDepartment.getParentDepartment();
            parentDepartments.add(currentDepartment);
        }
        return parentDepartments;
    }

    // Retrieves all child departments recursively
    public List<Department> getAllChildDepartments() {
        List<Department> childDepartments = new ArrayList<>(subDepartments);
        for (Department subDepartment : subDepartments) {
            childDepartments.addAll(subDepartment.getAllChildDepartments());
        }
        return childDepartments;
    }
    public void changeParentDepartment(Department childDepartment, Department newParentDepartment) {
        // Remove the child from the current parent department if it has one
        if (childDepartment.getParentDepartment() != null) {
            Department oldParent = childDepartment.getParentDepartment();
            oldParent.getSubDepartments().remove(childDepartment);
        }

        // Set the new parent for the child department
        childDepartment.setParentDepartment(newParentDepartment);

        // Add the child to the new parent department's subDepartments set
        if (newParentDepartment != null) {
            newParentDepartment.getSubDepartments().add(childDepartment);
        }
    }

}