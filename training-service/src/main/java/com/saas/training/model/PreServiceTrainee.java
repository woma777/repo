package com.saas.training.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.saas.training.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true,
        exclude = {"preServiceCourses", "checkedDocuments"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTrainee extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 10)
    private String traineeId;

    @NotNull
    @Column(nullable = false)
    private UUID budgetYearId;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 15)
    private String batchCode;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String middleName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String lastName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String amharicFirstName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String amharicMiddleName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String amharicLastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull
    @Column(nullable = false)
    private UUID locationId;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String telephoneNumber;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(min = 10, max = 20)
    private String mobileNumber;

    @NotNull
    @Column(nullable = false)
    private UUID educationLevelId;

    @NotNull
    @Column(nullable = false)
    private UUID fieldOfStudyId;

    @Size(max = 100)
    private String remark;

    private String imageName;
    private String imageType;
    @Lob
    @Column(length = 50000000)
    private byte[] image;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trainee_checked_document",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    @JsonManagedReference
    private Set<CheckedDocument> checkedDocuments;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pre_service_trainee_course",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonManagedReference
    private Set<PreServiceCourse> preServiceCourses;

    @OneToMany(mappedBy = "preServiceTrainee", fetch = FetchType.LAZY)
    private List<PreServiceTraineeResult> preServiceTraineeResults;
}
