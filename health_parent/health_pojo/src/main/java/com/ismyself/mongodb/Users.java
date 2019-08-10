package com.ismyself.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * package com.ismyself.pojo;
 *
 * @auther txw
 * @create 2019-08-08  15:50
 * @description：
 */
@Document(collection = "users")
public class Users  implements Serializable {
    @Id
    private Double _id;

    private String name;

    private Double age;

    @Override
    public String toString() {
        return "Users{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Double get_id() {
        return _id;
    }

    public void set_id(Double _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }
}

