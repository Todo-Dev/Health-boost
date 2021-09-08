package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Student type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Students")
@Index(name = "myStudent", fields = {"coachID"})
public final class Student implements Model {
  public static final QueryField ID = field("Student", "id");
  public static final QueryField FIRST_NAME = field("Student", "firstName");
  public static final QueryField LAST_NAME = field("Student", "lastName");
  public static final QueryField USERNAME = field("Student", "username");
  public static final QueryField EMAIL = field("Student", "email");
  public static final QueryField PHONE_NUMBER = field("Student", "phoneNumber");
  public static final QueryField WEIGHT = field("Student", "weight");
  public static final QueryField HEIGHT = field("Student", "height");
  public static final QueryField ROLE = field("Student", "role");
  public static final QueryField COACH = field("Student", "coachID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String firstName;
  private final @ModelField(targetType="String", isRequired = true) String lastName;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="Float", isRequired = true) Double phoneNumber;
  private final @ModelField(targetType="Float") Double weight;
  private final @ModelField(targetType="Float") Double height;
  private final @ModelField(targetType="String", isRequired = true) String role;
  private final @ModelField(targetType="Coach") @BelongsTo(targetName = "coachID", type = Coach.class) Coach coach;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFirstName() {
      return firstName;
  }
  
  public String getLastName() {
      return lastName;
  }
  
  public String getUsername() {
      return username;
  }
  
  public String getEmail() {
      return email;
  }
  
  public Double getPhoneNumber() {
      return phoneNumber;
  }
  
  public Double getWeight() {
      return weight;
  }
  
  public Double getHeight() {
      return height;
  }
  
  public String getRole() {
      return role;
  }
  
  public Coach getCoach() {
      return coach;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Student(String id, String firstName, String lastName, String username, String email, Double phoneNumber, Double weight, Double height, String role, Coach coach) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.weight = weight;
    this.height = height;
    this.role = role;
    this.coach = coach;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Student student = (Student) obj;
      return ObjectsCompat.equals(getId(), student.getId()) &&
              ObjectsCompat.equals(getFirstName(), student.getFirstName()) &&
              ObjectsCompat.equals(getLastName(), student.getLastName()) &&
              ObjectsCompat.equals(getUsername(), student.getUsername()) &&
              ObjectsCompat.equals(getEmail(), student.getEmail()) &&
              ObjectsCompat.equals(getPhoneNumber(), student.getPhoneNumber()) &&
              ObjectsCompat.equals(getWeight(), student.getWeight()) &&
              ObjectsCompat.equals(getHeight(), student.getHeight()) &&
              ObjectsCompat.equals(getRole(), student.getRole()) &&
              ObjectsCompat.equals(getCoach(), student.getCoach()) &&
              ObjectsCompat.equals(getCreatedAt(), student.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), student.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstName())
      .append(getLastName())
      .append(getUsername())
      .append(getEmail())
      .append(getPhoneNumber())
      .append(getWeight())
      .append(getHeight())
      .append(getRole())
      .append(getCoach())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Student {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstName=" + String.valueOf(getFirstName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("weight=" + String.valueOf(getWeight()) + ", ")
      .append("height=" + String.valueOf(getHeight()) + ", ")
      .append("role=" + String.valueOf(getRole()) + ", ")
      .append("coach=" + String.valueOf(getCoach()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static FirstNameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Student justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Student(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      firstName,
      lastName,
      username,
      email,
      phoneNumber,
      weight,
      height,
      role,
      coach);
  }
  public interface FirstNameStep {
    LastNameStep firstName(String firstName);
  }
  

  public interface LastNameStep {
    UsernameStep lastName(String lastName);
  }
  

  public interface UsernameStep {
    EmailStep username(String username);
  }
  

  public interface EmailStep {
    PhoneNumberStep email(String email);
  }
  

  public interface PhoneNumberStep {
    RoleStep phoneNumber(Double phoneNumber);
  }
  

  public interface RoleStep {
    BuildStep role(String role);
  }
  

  public interface BuildStep {
    Student build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep weight(Double weight);
    BuildStep height(Double height);
    BuildStep coach(Coach coach);
  }
  

  public static class Builder implements FirstNameStep, LastNameStep, UsernameStep, EmailStep, PhoneNumberStep, RoleStep, BuildStep {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Double phoneNumber;
    private String role;
    private Double weight;
    private Double height;
    private Coach coach;
    @Override
     public Student build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Student(
          id,
          firstName,
          lastName,
          username,
          email,
          phoneNumber,
          weight,
          height,
          role,
          coach);
    }
    
    @Override
     public LastNameStep firstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
        return this;
    }
    
    @Override
     public UsernameStep lastName(String lastName) {
        Objects.requireNonNull(lastName);
        this.lastName = lastName;
        return this;
    }
    
    @Override
     public EmailStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public PhoneNumberStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public RoleStep phoneNumber(Double phoneNumber) {
        Objects.requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    @Override
     public BuildStep role(String role) {
        Objects.requireNonNull(role);
        this.role = role;
        return this;
    }
    
    @Override
     public BuildStep weight(Double weight) {
        this.weight = weight;
        return this;
    }
    
    @Override
     public BuildStep height(Double height) {
        this.height = height;
        return this;
    }
    
    @Override
     public BuildStep coach(Coach coach) {
        this.coach = coach;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String firstName, String lastName, String username, String email, Double phoneNumber, Double weight, Double height, String role, Coach coach) {
      super.id(id);
      super.firstName(firstName)
        .lastName(lastName)
        .username(username)
        .email(email)
        .phoneNumber(phoneNumber)
        .role(role)
        .weight(weight)
        .height(height)
        .coach(coach);
    }
    
    @Override
     public CopyOfBuilder firstName(String firstName) {
      return (CopyOfBuilder) super.firstName(firstName);
    }
    
    @Override
     public CopyOfBuilder lastName(String lastName) {
      return (CopyOfBuilder) super.lastName(lastName);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder phoneNumber(Double phoneNumber) {
      return (CopyOfBuilder) super.phoneNumber(phoneNumber);
    }
    
    @Override
     public CopyOfBuilder role(String role) {
      return (CopyOfBuilder) super.role(role);
    }
    
    @Override
     public CopyOfBuilder weight(Double weight) {
      return (CopyOfBuilder) super.weight(weight);
    }
    
    @Override
     public CopyOfBuilder height(Double height) {
      return (CopyOfBuilder) super.height(height);
    }
    
    @Override
     public CopyOfBuilder coach(Coach coach) {
      return (CopyOfBuilder) super.coach(coach);
    }
  }
  
}
