package com.example.staffrating.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "staff_ratings")
public class StaffRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Role type is required")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @NotNull(message = "Clarity score is required")
    @Min(value = 1, message = "Clarity score must be at least 1")
    @Max(value = 10, message = "Clarity score must be at most 10")
    private Integer clarity;

    @NotNull(message = "Niceness score is required")
    @Min(value = 1, message = "Niceness score must be at least 1")
    @Max(value = 10, message = "Niceness score must be at most 10")
    private Integer niceness;

    @NotNull(message = "Knowledgeable score is required")
    @Min(value = 1, message = "Knowledgeable score must be at least 1")
    @Max(value = 10, message = "Knowledgeable score must be at most 10")
    private Integer knowledgeableScore;

    @Size(max = 500, message = "Comment can be at most 500 characters")
    @Column(length = 500)
    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public Integer getOverallScore() {
        if (clarity != null && niceness != null && knowledgeableScore != null) {
            return (int) Math.round((clarity + niceness + knowledgeableScore) / 3.0);
        }
        return 0;
    }

    public String getDisplayTitle() {
        StaffMemberProfile profile = ProfileFactory.getProfile(roleType);
        return profile.displayTitle(name);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public RoleType getRoleType() { return roleType; }
    public void setRoleType(RoleType roleType) { this.roleType = roleType; }

    public Integer getClarity() { return clarity; }
    public void setClarity(Integer clarity) { this.clarity = clarity; }

    public Integer getNiceness() { return niceness; }
    public void setNiceness(Integer niceness) { this.niceness = niceness; }

    public Integer getKnowledgeableScore() { return knowledgeableScore; }
    public void setKnowledgeableScore(Integer knowledgeableScore) { this.knowledgeableScore = knowledgeableScore; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
