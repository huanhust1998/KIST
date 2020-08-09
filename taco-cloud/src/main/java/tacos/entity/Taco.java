package tacos.entity;

import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class Taco {
    private Long id;
    @NotNull
    @Size(min=5,message = "Name must ba at least 5 characters long")
    private String name;
    @Size(min=1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;
}
