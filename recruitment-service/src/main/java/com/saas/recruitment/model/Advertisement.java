package com.saas.recruitment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.saas.recruitment.enums.AnnouncementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true,
        exclude = {"recruitment", "mediaTypes"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement extends Base {

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDateTime endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnouncementType announcementType;

    @NotNull
    @Column(nullable = false)
    private Integer occurrence;

    @OneToOne
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "advertisement_media_type",
            joinColumns = @JoinColumn(name = "advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "media_type_id", nullable = false)
    )
    @JsonManagedReference
    private Set<MediaType> mediaTypes = new HashSet<>();

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
