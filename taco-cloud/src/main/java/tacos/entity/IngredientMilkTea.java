package tacos.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class IngredientMilkTea {

    private final String id;
    private final String name;
    private final Type type;
    public static enum Type{
        SUGAR,ICE,TOPPING
    }
}
