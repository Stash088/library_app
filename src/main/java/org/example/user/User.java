package org.example.user;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @JsonProperty("id")
    int id;

    @JsonProperty("first_name")
    String first_name;

    @JsonProperty("last_name")
    String last_name;

    @JsonProperty("email")
    String email;

    @JsonProperty("code_reader")
    String code_reader;

    public String getCode_reader() {
        return code_reader;
    }
    public String getEmail() {
        return email;
    }
    public String getFirst_name() {
        return first_name;
    }
    public int getId() {
        return id;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setCode_reader(String code_reader) {
        this.code_reader = code_reader;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}