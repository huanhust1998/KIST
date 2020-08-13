package tacos.entity;

import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=5,message = "Name must ba at least 5 characters long")
    private Date createdAt;

    private String name;
    @ManyToMany(targetEntity = IngredientTaco.class)
    @Size(min=1, message = "You must choose at least 1 ingredient")
    private List<IngredientTaco> ingredients;

    @PrePersist
    void createdAt(){
        this.createdAt = new Date(12/2020);
    }
}
