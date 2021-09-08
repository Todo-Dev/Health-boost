package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
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

/** This is an auto generated class representing the Coach type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Coaches")
public final class Coach implements Model {
  public static final QueryField ID = field("Coach", "id");
  public static final QueryField FIRST_NAME = field("Coach", "firstName");
  public static final QueryField LAST_NAME = field("Coach", "lastName");
  public static final QueryField USERNAME = field("Coach", "username");
  public static final QueryField EMAIL = field("Coach", "email");
  public static final QueryField PHONE_NUMBER = field("Coach", "phoneNumber");
  public static final QueryField ROLE = field("Coach", "role");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String firstName;
  private final @ModelField(targetType="String", isRequired = true) String lastName;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="Float", isRequired = true) Double phoneNumber;
  private final @ModelField(targetType="String", isRequired = true) String role;
  private final @ModelField(targetType="Student") @HasMany(associatedWith = "coach", type = Student.class) List<Student> students = null;
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
  
  public String getRole() {
      return role;
  }
  
  public List<Student> getStudents() {
      return students;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Coach(String id, String firstName, String lastName, String username, String email, Double phoneNumber, String role) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.role = role;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Coach coach = (Coach) obj;
      return ObjectsCompat.equals(getId(), coach.getId()) &&
              ObjectsCompat.equals(getFirstName(), coach.getFirstName()) &&
              ObjectsCompat.equals(getLastName(), coach.getLastName()) &&
              ObjectsCompat.equals(getUsername(), coach.getUsername()) &&
              ObjectsCompat.equals(getEmail(), coach.getEmail()) &&
              ObjectsCompat.equals(getPhoneNumber(), coach.getPhoneNumber()) &&
              ObjectsCompat.equals(getRole(), coach.getRole()) &&
              ObjectsCompat.equals(getCreatedAt(), coach.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), coach.getUpdatedAt());
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
      .append(getRole())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Coach {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstName=" + String.valueOf(getFirstName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("role=" + String.valueOf(getRole()) + ", ")
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
  public static Coach justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Coach(
      id,
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
      role);
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
    Coach build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements FirstNameStep, LastNameStep, UsernameStep, EmailStep, PhoneNumberStep, RoleStep, BuildStep {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Double phoneNumber;
    private String role;
    @Override
     public Coach build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Coach(
          id,
          firstName,
          lastName,
          username,
          email,
          phoneNumber,
          role);
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
    private CopyOfBuilder(String id, String firstName, String lastName, String username, String email, Double phoneNumber, String role) {
      super.id(id);
      super.firstName(firstName)
        .lastName(lastName)
        .username(username)
        .email(email)
        .phoneNumber(phoneNumber)
        .role(role);
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
  }
  
}
